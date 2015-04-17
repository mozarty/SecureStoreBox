package net.orange_box.storebox.annotations.adapters;

import net.orange_box.storebox.adapters.StoreBoxTypeAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TypeAdapter {
    
    Class type();
    
    Class<? extends StoreBoxTypeAdapter> adapter();
}
