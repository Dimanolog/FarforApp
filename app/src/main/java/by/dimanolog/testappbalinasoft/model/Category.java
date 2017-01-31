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
@Root(name="category")
public class Category implements Serializable{
    @DatabaseField(id=true)
    @Attribute(name="id")
    private Long mId;
    @DatabaseField
    @Text
    private String mCategory;


    public Category() {
    }

    public Category(Long id, String category) {
        mId = id;
        mCategory = category;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    @Override
    public boolean equals(Object o) {
       if(o instanceof Category){
           Category category =(Category)o;
           return this.mId.equals(category.getId());
       }
        return false;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }
}
