package net.orange_box.storebox.adapters;

public class IntegerTypeAdapter extends StoreBoxTypeAdapter<Integer, Integer> {

    private static int DEFAULT;
    
    public IntegerTypeAdapter() {
        super(Integer.class);
    }
    
    @Override
    public Integer getForPreferences(Integer value) {
        return value;
    }

    @Override
    public Integer getFromPreferences(Integer value) {
        return value;
    }

    @Override
    public Integer getDefaultValueForPreferences() {
        return DEFAULT;
    }
}
