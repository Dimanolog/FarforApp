package by.dimanolog.testappbalinasoft.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

/**
 * Created by Dimanolog on 13.01.2017.
 */
@DatabaseTable
@Root(name = "param")
public class Param implements Serializable{
    @DatabaseField(generatedId = true)
    private Long mId;
    @DatabaseField(foreign = true, columnName = "offer_id")
    private Offer mOffer;
    @DatabaseField
    @Attribute(name="name")
    private String mName;
    @DatabaseField
    @Text
    private String mContent;

    public Param() {
    }

    public Offer getOffer() {
        return mOffer;
    }

    public void setOffer(Offer offer) {
        mOffer = offer;
    }

    public Param(String content, String name) {

        mContent = content;
        mName = name;
    }

    public String getContent ()
    {
        return mContent;

    }

    public void setContent (String content)
    {
        this.mContent = content;
    }

    public String getName ()
    {
        return mName;
    }

    public void setName (String name)
    {
        this.mName = name;
    }

    @Override
    public boolean equals(Object o) {
       if(o instanceof Param) {
           Param param=(Param)o;
           return mName.equals(param.mName);
       }
        return false;
    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }
    @Override
    public String toString() {

        return String.format("%s : %s",mName, mContent);
    }
}

