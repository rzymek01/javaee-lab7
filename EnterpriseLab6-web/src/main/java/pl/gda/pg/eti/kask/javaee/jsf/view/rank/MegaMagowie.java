package pl.gda.pg.eti.kask.javaee.jsf.view.rank;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import lombok.extern.java.Log;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import pl.gda.pg.eti.kask.javaee.jsf.WiezaService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.events.BasicEvent;
import pl.gda.pg.eti.kask.javaee.jsf.events.ChangeMagowieEvent;

/**
 *
 * @author maciek
 */
@ApplicationScoped
@Log
public class MegaMagowie implements Serializable {

  @EJB
  private WiezaService wiezaService;

  private Future<Void> future;

  private List<Mag> magowie;
  
  private int liczbaMegaMagow = 7;

//  public void setWiezaService(WiezaService wiezaService) {
//    this.wiezaService = wiezaService;
//  }

  public List<Mag> getMagowie() {
    if (magowie == null) {
      magowie = wiezaService.findMegaMagowie(liczbaMegaMagow);
    }
    return magowie;
  }

  public void update(@Observes @ChangeMagowieEvent BasicEvent event) {
    magowie = wiezaService.findMegaMagowie(liczbaMegaMagow);
    EventBus eventBus = EventBusFactory.getDefault().eventBus();
    eventBus.publish("/notifications", new FacesMessage("", ""));
  }
}
