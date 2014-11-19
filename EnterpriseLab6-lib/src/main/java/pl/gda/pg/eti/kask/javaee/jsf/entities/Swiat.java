
package pl.gda.pg.eti.kask.javaee.jsf.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wieza" type="{http://www.eti.pg.gda.pl/kask/javaee/wieze}wieza" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@ToString(exclude = "wieza")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "swiat", propOrder = {
    "wieza"
})
@XmlRootElement(name = "swiat", namespace = "http://www.eti.pg.gda.pl/kask/javaee/wieze")
public class Swiat {

    @XmlElement(namespace = "http://www.eti.pg.gda.pl/kask/javaee/wieze", required = true)
    protected List<Wieza> wieza;

    /**
     * Gets the value of the wieza property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wieza property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWieza().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Wieza }
     * 
     * 
     */
    public List<Wieza> getWieza() {
        if (wieza == null) {
            wieza = new ArrayList<Wieza>();
        }
        return this.wieza;
    }

}
