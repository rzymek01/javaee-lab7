package pl.gda.pg.eti.kask.javaee.jsf.view.rank;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;
import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import lombok.Delegate;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author Mikolaj
 */

@ViewScoped
@ManagedBean
public class ListMegaMagowie implements Serializable {
    
    @Inject
    @Delegate
    MegaMagowie magowie;
    
}
