
package pl.gda.pg.eti.kask.javaee.jsf.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for wieza complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="wieza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mag" type="{http://www.eti.pg.gda.pl/kask/javaee/wieze}mag" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="wysokosc" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@ToString(of = "wysokosc")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wieza", namespace = "http://www.eti.pg.gda.pl/kask/javaee/wieze", propOrder = {
    "mag"
})
@Getter
@Setter
@Entity
@Table(name = "wieze")
@NamedQuery(name = "Wieza.findAll", query = "SELECT w FROM Wieza w")
public class Wieza {

  @XmlElement(namespace = "http://www.eti.pg.gda.pl/kask/javaee/wieze")
  @OneToMany(mappedBy = "wieza", cascade = CascadeType.ALL)
  protected List<Mag> mag;

  @XmlAttribute(name = "wysokosc", required = true)
  @Column
  protected int wysokosc;

  @XmlAttribute(name = "id", required = true)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  protected int id;
  
  @ManyToOne
  @JoinColumn(name = "owner", referencedColumnName = "id")
  private User owner;

  /**
   * Gets the value of the mag property.
   * <p/>
   * <p/>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the mag property.
   * <p/>
   * <p/>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getMag().add(newItem);
   * </pre>
   * <p/>
   * <p/>
   * <p/>
   * Objects of the following type(s) are allowed in the list
   * {@link Mag }
   */


}
