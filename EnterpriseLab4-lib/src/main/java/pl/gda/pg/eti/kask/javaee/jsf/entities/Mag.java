
package pl.gda.pg.eti.kask.javaee.jsf.entities;

import lombok.*;
import pl.gda.pg.eti.kask.javaee.jsf.entities.validators.GoodName;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * <p>Java class for mag complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="mag">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="imie" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mana" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="zywiol" use="required" type="{http://www.eti.pg.gda.pl/kask/javaee/wieze}zywiol" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@ToString
@EqualsAndHashCode(exclude="wieza")
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mag", namespace = "http://www.eti.pg.gda.pl/kask/javaee/wieze")
@Getter
@Setter
@Entity
@Table(name = "magowie")
@NamedQueries({
    @NamedQuery(name = "Mag.findAll", query = "SELECT m FROM Mag m"),
    @NamedQuery(name = "Mag.increaseManaForAll", query = "UPDATE Mag m SET m.mana = m.mana + :incr")
})
public class Mag {

  @XmlAttribute(name = "imie", required = true)
  @Column
  @GoodName(minLength = 5, maxLength = 10, regex = "^[A-Z].*$")
  protected String imie;

  @XmlAttribute(name = "mana", required = true)
  @Column
  protected int mana;

  @XmlAttribute(name = "zywiol", required = true)
  @Column
  @Enumerated(EnumType.STRING)
  protected Zywiol zywiol = Zywiol.WODA;

  @XmlAttribute(name = "id", required = true)
  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int id;

  @XmlTransient
  @ManyToOne()
  @JoinColumn(name = "wieza_id")
  protected Wieza wieza;

}
