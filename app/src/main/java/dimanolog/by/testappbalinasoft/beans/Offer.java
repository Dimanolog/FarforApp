package dimanolog.by.testappbalinasoft.beans;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimanolog on 13.01.2017.
 */
@Root(name="offer")
public class Offer implements Serializable {
    @Attribute(name = "id")
    private Long mId;
    @Element(name="categoryId")
    private Long mCategoryId;
    @Element(name="url")
    private String mUrl;
    @Element(name="picture")
    private String mPictureUrl;
    @Element(name="name")
    private String mName;
    @Element(name="price")
    private Double mPrice;
    @Element(name="description")
    private String mDescription;
    @ElementList(inline=true, entry ="param" )
    private List<Param> ParamList=new ArrayList<>();


    public Offer() {
    }

    public Long getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(Long categoryId) {
        mCategoryId = categoryId;
    }

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
        return ParamList;
    }

    public void setParamList(List<Param> paramList) {
        ParamList = paramList;
    }
}
