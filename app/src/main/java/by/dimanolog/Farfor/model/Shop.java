package by.dimanolog.farfor.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;



@Root(name = "Shop")
public class Shop implements Serializable {
    @ElementList(name="categories", entry = "category")
    List<Category> mCategories;
    @ElementList(name="offers",entry = "offer")
    List<Offer>mOffers;

    public List<Offer> getOffers() {
        return mOffers;
    }

    public void setOffers(List<Offer> offers) {
        mOffers = offers;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

}
