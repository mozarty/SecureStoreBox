package net.orange_box.storebox.adapters;

public abstract class StoreBoxTypeAdapter<F, T> {
    
    private final Class<T> type;
    
    public StoreBoxTypeAdapter(Class<T> type) {
        this.type = type;
    }
    
    public final Class<T> getType() {
        return type;
    }

    public abstract T getForPreferences(F value);

    public abstract F getFromPreferences(T value);

    public abstract T getDefaultValueForPreferences();
}
