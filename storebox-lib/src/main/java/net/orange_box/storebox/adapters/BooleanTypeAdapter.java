package net.orange_box.storebox.adapters;

public class BooleanTypeAdapter extends StoreBoxTypeAdapter<Boolean, Boolean> {

    private static boolean DEFAULT;
    
    public BooleanTypeAdapter() {
        super(Boolean.class);
    }

    @Override
    public Boolean getForPreferences(Boolean value) {
        return value;
    }

    @Override
    public Boolean getFromPreferences(Boolean value) {
        return value;
    }

    @Override
    public Boolean getDefaultValueForPreferences() {
        return DEFAULT;
    }
}
