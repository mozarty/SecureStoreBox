package net.orange_box.storebox.harness.types;

import net.orange_box.storebox.adapters.StoreBoxTypeAdapter;

public class CustomStringType {
    
    public final String one;
    public final String two;
    
    public CustomStringType(String one, String two) {
        this.one = one;
        this.two = two;
    }
    
    public static class CustomStringTypeTypeAdapter extends
            StoreBoxTypeAdapter<CustomStringType, String> {

        public CustomStringTypeTypeAdapter() {
            super(String.class);
        }

        @Override
        public String getForPreferences(CustomStringType value) {
            return value.one + "-" + value.two;
        }

        @Override
        public CustomStringType getFromPreferences(String value) {
            final String[] split = value.split("-");
            return new CustomStringType(split[0], split[1]);
        }

        @Override
        public String getDefaultValueForPreferences() {
            return "-";
        }
    }
}
