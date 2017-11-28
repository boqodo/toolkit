package java.z.cube.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "number",
    "text"
})
@XmlRootElement(name = "entrydata")
public class EntryData {

    protected Short number;
    protected String text;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected byte columnnumber;

    
    public Short getNumber() {
        return number;
    }

    
    public void setNumber(Short value) {
        this.number = value;
    }

    
    public String getText() {
        return text;
    }

    
    public void setText(String value) {
        this.text = value;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String value) {
        this.name = value;
    }

    
    public byte getColumnnumber() {
        return columnnumber;
    }

    
    public void setColumnnumber(byte value) {
        this.columnnumber = value;
    }

}
