package by.dimanolog.farfo.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;



public class ResourcesUtil {
    public static Drawable getDrawbleByName(String name, Context context){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());

        return ResourcesCompat.getDrawable(resources, resourceId , null);
    }
}
