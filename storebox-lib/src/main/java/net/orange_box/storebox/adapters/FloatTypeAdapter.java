package net.orange_box.storebox.adapters;

public class FloatTypeAdapter extends StoreBoxTypeAdapter<Float, Float> {

    private static float DEFAULT;
    
    public FloatTypeAdapter() {
        super(Float.class);
    }

    @Override
    public Float getForPreferences(Float value) {
        return value;
    }

    @Override
    public Float getFromPreferences(Float value) {
        return value;
    }

    @Override
    public Float getDefaultValueForPreferences() {
        return DEFAULT;
    }
}
