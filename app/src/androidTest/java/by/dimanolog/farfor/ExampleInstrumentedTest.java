package by.dimanolog.farfor;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import by.dimanolog.farfor.model.Category;
import by.dimanolog.farfor.database.DatabaseHelper;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("by.dimanolog.farfor", appContext.getPackageName());
    }

    @Test
    public void databaseCreate(){
       DatabaseHelper sDatabaseHelper = OpenHelperManager.getHelper(InstrumentationRegistry.getTargetContext(), DatabaseHelper.class);
       sDatabaseHelper.getCategoryDao().create(new Category(new Long(0),"1234"));
    }
}
