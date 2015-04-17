package net.orange_box.storebox.adapters;

public class StringTypeAdapter extends StoreBoxTypeAdapter<String, String> {
    
    public StringTypeAdapter() {
        super(String.class);
    }
    
    @Override
    public String getForPreferences(String value) {
        return value;
    }

    @Override
    public String getFromPreferences(String value) {
        return value;
    }

    @Override
    public String getDefaultValueForPreferences() {
        return "";
    }
}
