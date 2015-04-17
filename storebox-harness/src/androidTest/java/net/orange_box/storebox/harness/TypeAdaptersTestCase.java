package net.orange_box.storebox.harness;

import android.preference.PreferenceManager;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import net.orange_box.storebox.StoreBox;
import net.orange_box.storebox.harness.interfaces.TypeAdapterInterface;
import net.orange_box.storebox.harness.types.CustomStringType;

public class TypeAdaptersTestCase extends InstrumentationTestCase {

    @Override
    protected void tearDown() throws Exception {
        PreferenceManager.getDefaultSharedPreferences(
                getInstrumentation().getTargetContext())
                .edit().clear().commit();
        
        super.tearDown();
    }

    @SmallTest
    public void testRegularType() {
        final TypeAdapterInterface uut = getUut(TypeAdapterInterface.class);
        
        uut.setString("test");
        assertEquals("test", uut.getString());
    }

    @SmallTest
    public void testCustomType() {
        final TypeAdapterInterface uut = getUut(TypeAdapterInterface.class);
        final CustomStringType value = new CustomStringType("a", "b");

        uut.setCustomStringType(value);
        assertEquals("a", uut.getCustomStringType().one);
        assertEquals("b", uut.getCustomStringType().two);
    }
    
    private <T> T getUut(Class<T> cls) {
        return StoreBox.create(
                getInstrumentation().getTargetContext(),
                cls);
    }
}
