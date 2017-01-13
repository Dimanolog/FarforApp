package dimanolog.by.testappbalinasoft.beans;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Dimanolog on 13.01.2017.
 */
@Root(name = "param")
public class Param implements Serializable{
    @Attribute(name="name")
    private String mName;
    @Element(name="param")
    private String mContent;

    public Param() {
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

}
