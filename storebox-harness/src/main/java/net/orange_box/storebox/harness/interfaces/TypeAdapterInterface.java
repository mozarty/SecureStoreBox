package net.orange_box.storebox.harness.interfaces;

import net.orange_box.storebox.annotations.adapters.TypeAdapter;
import net.orange_box.storebox.annotations.method.KeyByResource;
import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.harness.R;
import net.orange_box.storebox.harness.types.CustomStringType;

@TypeAdapter(
        type = CustomStringType.class,
        adapter = CustomStringType.CustomStringTypeTypeAdapter.class)
public interface TypeAdapterInterface {
    
    @KeyByResource(R.string.key_string)
    void setString(String value);
    
    @KeyByResource(R.string.key_string)
    String getString();
    
    @KeyByString("custom_type")
    void setCustomStringType(CustomStringType value);

    @KeyByString("custom_type")
    CustomStringType getCustomStringType();
}
