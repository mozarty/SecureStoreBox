package net.orange_box.storebox.adapters;

public class LongTypeAdapter extends StoreBoxTypeAdapter<Long, Long> {

    private static long DEFAULT;
    
    public LongTypeAdapter() {
        super(Long.class);
    }
    
    @Override
    public Long getForPreferences(Long value) {
        return value;
    }

    @Override
    public Long getFromPreferences(Long value) {
        return value;
    }

    @Override
    public Long getDefaultValueForPreferences() {
        return DEFAULT;
    }
}
