package by.dimanolog.farfor.custom.xml;

import org.simpleframework.xml.transform.Transform;

/**
 * Created by Dimanolog on 06.04.2017.
 */

public class CustomDoubleTransformer implements Transform<Double> {

    @Override
    public Double read(String value) throws Exception {
        String str = value.replace(',', '.');
        return Double.valueOf(str);
    }

    @Override
    public String write(Double value) throws Exception {
        return value.toString();
    }
}
