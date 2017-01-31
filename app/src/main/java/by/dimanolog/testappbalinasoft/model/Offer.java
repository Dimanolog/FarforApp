package by.dimanolog.testappbalinasoft.model;


import android.support.annotation.Nullable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dimanolog on 13.01.2017.
 */
@DatabaseTable
@Root(name = "offer")
public class Offer implements Serializable {
    @DatabaseField(id = true)
    @Attribute(name = "id")
    private Long mId;
    @DatabaseField(foreign = true,useGetSet = true)
    private Category mCategory;
    @Element(name = "categoryId")
    private Long mCategoryId;
    @DatabaseField
    @Element(name = "url")
    private String mUrl;
    @DatabaseField
    @Element(name = "picture", required = false)
    private String mPictureUrl;
    @DatabaseField
    @Element(name = "name")
    private String mName;
    @DatabaseField
    @Element(name = "price")
    private Double mPrice;
    @DatabaseField
    @Element(name = "description", required = false)
    private String mDescription;
    @ForeignCollectionField(eager = true)
    private Collection<Param> mParamCollection;

    public Category getCategory() {

        return mCategory;
    }

    public void setCategory(Category category) {
        mCategoryId=category.getId();
        mCategory=category;
    }

    public Offer() {
    }
    @ElementList(inline = true, entry = "param", required = false)
    public Collection<Param> getParamCollection() {
        return mParamCollection;
    }

    @ElementList(inline = true, entry = "param", required = false)
    public void setParamCollection(Collection<Param> paramCollection) {
        if(paramCollection!=null){
            for(Param param:paramCollection){
                param.setOffer(this);
            }
        }
        mParamCollection = paramCollection;
    }

    public Long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(Long categoryId) {
        mCategory=new Category();
        mCategory.setId(categoryId);
        mCategoryId = categoryId;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Nullable
    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public List<Param> getParamList() {
        List<Param> paramList = new ArrayList<>();
        paramList.addAll(mParamCollection);
        return paramList;
    }

    @Nullable
    public Param getParamsByName(String paramName) {
        if(mParamCollection!=null) {
            for (Param paramItem : mParamCollection) {
                if (paramItem.getName().equals(paramName)) {
                    return paramItem;
                }
            }
        }
        return null;
    }

}
