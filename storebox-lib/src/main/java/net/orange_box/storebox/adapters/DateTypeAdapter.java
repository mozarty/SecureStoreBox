package net.orange_box.storebox.adapters;

import java.util.Date;

public class DateTypeAdapter extends StoreBoxTypeAdapter<Date, Long> {

    public DateTypeAdapter() {
        super(Long.class);
    }
    
    @Override
    public Long getForPreferences(Date value) {
        return value.getTime();
    }

    @Override
    public Date getFromPreferences(Long value) {
        return new Date(value);
    }

    @Override
    public Long getDefaultValueForPreferences() {
        return 0L;
    }
}
