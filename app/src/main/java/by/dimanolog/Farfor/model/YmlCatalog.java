package by.dimanolog.farfor.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "yml_catalog")
public class YmlCatalog {
    @Element(name = "shop")
    private Shop mShop;
    @Attribute(name = "date")
    private String mDate;

    public YmlCatalog() {
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Shop getShop() {
        return mShop;
    }

    public void setShop(Shop shop) {
        mShop = shop;
    }
}
