//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.22 at 02:35:47 ���� CST 
//


package java.z.cube.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "viewentry"
})
@XmlRootElement(name = "viewentries")
public class ViewEntries {

    @XmlElement(required = true)
    protected ViewEntry viewentry;
    @XmlAttribute(required = true)
    protected byte toplevelentries;
    @XmlAttribute(required = true)
    protected String timestamp;

     
    public ViewEntry getViewentry() {
        return viewentry;
    }

     
    public void setViewentry(ViewEntry value) {
        this.viewentry = value;
    }

     
    public byte getToplevelentries() {
        return toplevelentries;
    }

     
    public void setToplevelentries(byte value) {
        this.toplevelentries = value;
    }

     
    public String getTimestamp() {
        return timestamp;
    }

     
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

}