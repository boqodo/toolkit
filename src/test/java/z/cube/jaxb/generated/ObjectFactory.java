//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.22 at 02:35:47 ���� CST 
//


package java.z.cube.jaxb.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


 
@XmlRegistry
public class ObjectFactory {

    private final static QName _Text_QNAME = new QName("", "text");
    private final static QName _Number_QNAME = new QName("", "number");

     
    public ObjectFactory() {
    }

     
    public EntryData createEntrydata() {
        return new EntryData();
    }

     
    public ViewEntry createViewentry() {
        return new ViewEntry();
    }

     
    public ViewEntries createViewentries() {
        return new ViewEntries();
    }

     
    @XmlElementDecl(namespace = "", name = "text")
    public JAXBElement<String> createText(String value) {
        return new JAXBElement<String>(_Text_QNAME, String.class, null, value);
    }

     
    @XmlElementDecl(namespace = "", name = "number")
    public JAXBElement<Short> createNumber(Short value) {
        return new JAXBElement<Short>(_Number_QNAME, Short.class, null, value);
    }

}
