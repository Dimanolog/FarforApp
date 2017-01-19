package by.dimanolog.testappbalinasoft.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by Dimanolog on 16.01.2017.
 */

public class ResourcesUtil {
    public static Drawable getDrawbleByName(String name, Context context){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());

        return ResourcesCompat.getDrawable(resources, resourceId , null);
    }
}
