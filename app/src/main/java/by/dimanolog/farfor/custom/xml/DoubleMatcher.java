package by.dimanolog.farfor.custom.xml;

import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

/**
 * Created by Dimanolog on 18.04.2017.
 */

public class DoubleMatcher implements Matcher {
    @Override
    public Transform match(Class type) throws Exception {
        if(Double.class==type){
            return new CustomDoubleTransformer();
        }

        return null;
    }
}
