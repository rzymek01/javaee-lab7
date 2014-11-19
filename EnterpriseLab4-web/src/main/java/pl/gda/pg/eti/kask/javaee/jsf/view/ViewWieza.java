package pl.gda.pg.eti.kask.javaee.jsf.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.WiezaService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.ejb.EJB;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
@Log
public class ViewWieza implements Serializable {

    @EJB
    private WiezaService wiezaService;

    @Getter
    @Setter
    private int wiezaId;

    @Getter
    @Setter
    private Wieza wieza;

    public void setWiezaService(WiezaService wiezaService) {
        this.wiezaService = wiezaService;
    }
    
    public void init() {
        if (wieza == null) {
            wieza = wiezaService.findWieza(wiezaId);
        }
        if (wieza == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

}
