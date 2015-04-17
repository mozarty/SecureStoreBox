package net.orange_box.storebox.internal;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.TypedValue;

import net.orange_box.storebox.adapters.BooleanTypeAdapter;
import net.orange_box.storebox.adapters.DateTypeAdapter;
import net.orange_box.storebox.adapters.FloatTypeAdapter;
import net.orange_box.storebox.adapters.IntegerTypeAdapter;
import net.orange_box.storebox.adapters.LongTypeAdapter;
import net.orange_box.storebox.adapters.StoreBoxTypeAdapter;
import net.orange_box.storebox.adapters.StringTypeAdapter;
import net.orange_box.storebox.annotations.method.DefaultValue;
import net.orange_box.storebox.annotations.option.DefaultValueOption;
import net.orange_box.storebox.enums.DefaultValueMode;
import net.orange_box.storebox.utils.TypeUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TypeAdapterApplicator {
    
    private static final int DEFAULT_ADAPTER_COUNT = 6;
    private static final Map<Class, StoreBoxTypeAdapter> DEFAULT_ADAPTERS;
    static {
        final Map<Class, StoreBoxTypeAdapter> map = 
                new HashMap<>(DEFAULT_ADAPTER_COUNT);
        
        // natively supported
        map.put(Boolean.class, new BooleanTypeAdapter());
        map.put(Float.class, new FloatTypeAdapter());
        map.put(Integer.class, new IntegerTypeAdapter());
        map.put(Long.class, new LongTypeAdapter());
        map.put(String.class, new StringTypeAdapter());
        // our extra ones
        map.put(Date.class, new DateTypeAdapter());

        DEFAULT_ADAPTERS = map;
    }
    
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final Resources res;
    
    private final DefaultValueMode defaultValueMode;
    private final Map<Class, Class<? extends StoreBoxTypeAdapter>> adapterClasses;
    
    private final Map<Class, StoreBoxTypeAdapter> adapters;
    
    public TypeAdapterApplicator(
            SharedPreferences prefs,
            SharedPreferences.Editor editor,
            Resources res,
            DefaultValueMode defaultValueMode,
            Map<Class, Class<? extends StoreBoxTypeAdapter>> adapterClasses) {
        
        this.prefs = prefs;
        this.editor = editor;
        this.res = res;
        
        this.defaultValueMode = defaultValueMode;
        this.adapterClasses = adapterClasses;

        adapters = new HashMap<>(DEFAULT_ADAPTER_COUNT + adapterClasses.size());
        adapters.putAll(DEFAULT_ADAPTERS);
    }
    
    @SuppressWarnings("unchecked")
    public <F, T> void applyPut(String key, F value) {
        final StoreBoxTypeAdapter<F, T> adapter = 
                getAdapter((Class<F>) value.getClass());
        final Class<T> type = adapter.getType();
        
        if (type == Boolean.class) {
            editor.putBoolean(key, (Boolean) adapter.getForPreferences(value));
        } else if (type == Float.class) {
            editor.putFloat(key, (Float) adapter.getForPreferences(value));
        } else if (type == Integer.class) {
            editor.putInt(key, (Integer) adapter.getForPreferences(value));
        } else if (type == Long.class) {
            editor.putLong(key, (Long) adapter.getForPreferences(value));
        } else if (type == String.class) {
            editor.putString(key, (String) adapter.getForPreferences(value));
        } else {
            throw new UnsupportedOperationException(
                    "Adapters can only use Boolean, Float, Integer, Long, " +
                            "or String preference types");
        }
    }

    @SuppressWarnings("unchecked")
    public Object applyGet(
            String key,
            @Nullable Object defValue,
            Class outType,
            @Nullable DefaultValue defValueAnnotation,
            @Nullable DefaultValueOption defValueOptionAnnotation) {
        
        // method return types are not boxed for us
        outType = TypeUtil.wrapToBoxedType(outType);

        final StoreBoxTypeAdapter adapter = getAdapter(outType);
        final Class inType = adapter.getType();
        final Object defaultValue = getDefaultValue(
                defValue,
                defValueAnnotation,
                defValueOptionAnnotation,
                adapter);
        
        if (inType == Boolean.class) {
            return adapter.getFromPreferences(prefs.getBoolean(
                    key, (Boolean) defaultValue));
        } else if (inType == Float.class) {
            return adapter.getFromPreferences(prefs.getFloat(
                    key, (Float) defaultValue));
        } else if (inType == Integer.class) {
            return adapter.getFromPreferences(prefs.getInt(
                    key, (Integer) defaultValue));
        } else if (inType == Long.class) {
            return adapter.getFromPreferences(prefs.getLong(
                    key, (Long) defaultValue));
        } else if (inType == String.class) {
            return adapter.getFromPreferences(prefs.getString(
                    key, (String) defaultValue));
        } else {
            throw new UnsupportedOperationException(
                    "Adapters can only use Boolean, Float, Integer, Long, " +
                            "or String preference types");
        }
    }
    
    @SuppressWarnings("unchecked")
    private <F, T> StoreBoxTypeAdapter<F, T> getAdapter(Class<F> type) {
        StoreBoxTypeAdapter<F, T> adapter = adapters.get(type);
        if (adapter == null) {
            final Class<? extends StoreBoxTypeAdapter> adapterClass =
                    adapterClasses.get(type);
            if (adapterClass != null) {
                try {
                    adapter = adapterClass.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(
                            String.format(
                                    Locale.ENGLISH,
                                    "Failed to instantiate %1$s, " +
                                            "perhaps a no-arguments constructor is missing?",
                                    adapterClass.getSimpleName()),
                            e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(
                            String.format(
                                    Locale.ENGLISH,
                                    "Failed to instantiate %1$s, " +
                                            "perhaps the no-arguments constructor is not public?",
                                    adapterClass.getSimpleName()),
                            e);
                }

                adapters.put(type, adapter);
            } else {
                throw new UnsupportedOperationException(
                        "Failed to find adapter for type " + type.getName());
            }
        }
        
        return adapter;
    }
    
    @SuppressWarnings("unchecked")
    private Object getDefaultValue(
            @Nullable Object argument,
            @Nullable DefaultValue defValueAnnotation,
            @Nullable DefaultValueOption defValueOptionAnnotation,
            StoreBoxTypeAdapter adapter) {
        
        final Class type = adapter.getType();
        
        Object result = null;

        // parameter default > method-level default
        if (argument != null) {
            if (argument.getClass() != type) {
                throw new UnsupportedOperationException(String.format(
                        Locale.ENGLISH,
                        "Return type %1%s and default value type %2$s not the same",
                        adapter.getType().getName(),
                        type.getName()));
            }
            
            result = adapter.getForPreferences(argument);
        }
        if (result == null && defValueAnnotation != null) {
            final TypedValue value = new TypedValue();
            res.getValue(defValueAnnotation.value(), value, true);

            if (type == Boolean.class) {
                result = value.data != 0;
            } else if (type == Float.class) {
                result = value.getFloat();
            } else if (type == Integer.class) {
                result = value.data;
            } else if (type == Long.class) {
                result = (long) value.data;
            } else if (type == String.class) {
                if (value.string == null) {
                    result = new Object(); // we'll fail later
                } else {
                    result = value.string;
                }
            }
        }

        // default was not provided so let's see how we should create it
        if (result == null) {
            if (skipInstantiation(type)) {
                result = adapter.getDefaultValueForPreferences();
            } else {
                // method-level option > class-level option
                final DefaultValueMode mode;
                if (defValueOptionAnnotation != null) {
                    mode = defValueOptionAnnotation.value();
                } else {
                    mode = defaultValueMode;
                }
                
                switch (mode) {
                    case EMPTY:
                        result = adapter.getDefaultValueForPreferences();
                        break;

                    case NULL:
                    default:
                        result = null;
                }
            }
        }
        
        if (result != null && (result.getClass() != type)) {
            throw new UnsupportedOperationException(String.format(
                    Locale.ENGLISH,
                    "Return type %1%s and default value type %2$s not the same",
                    result.getClass().getName(),
                    type.getName()));
        }
        
        return result;
    }
    
    private static boolean skipInstantiation(Class type) {
        return (type == Boolean.class
                || type == Float.class
                || type == Integer.class
                || type == Long.class);
    }
}
