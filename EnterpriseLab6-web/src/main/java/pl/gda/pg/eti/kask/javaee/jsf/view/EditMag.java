package pl.gda.pg.eti.kask.javaee.jsf.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.WiezaService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Zywiol;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

/**
 * @author psysiu
 * @author maciek
 */
@ViewScoped
@ManagedBean
@Log
public class EditMag implements Serializable {

    @EJB
    private WiezaService wiezaService;

    @Getter
    @Setter
    private int magId;

    @Getter
    @Setter
    private Mag mag;

    private List<SelectItem> wiezeAsSelectItems;

    private List<SelectItem> zywiolyAsSelectItems;

    public void setWiezaService(WiezaService wiezaService) {
        this.wiezaService = wiezaService;
    }

    public List<SelectItem> getWiezeAsSelectItems() {
        if (wiezeAsSelectItems == null) {
            wiezeAsSelectItems = new ArrayList<>();
            for (Wieza wieza : wiezaService.findAllWieze()) {
                wiezeAsSelectItems.add(new SelectItem(wieza, wieza.toString()));
            }
        }
        return wiezeAsSelectItems;
    }

    public List<SelectItem> getZywiolyAsSelectItems() {
        if (zywiolyAsSelectItems == null) {
            zywiolyAsSelectItems = new ArrayList<>();
//      zywiolyAsSelectItems.add(new SelectItem(null, "---"));
            for (Zywiol zywiol : Zywiol.values()) {
                zywiolyAsSelectItems.add(new SelectItem(zywiol, zywiol.name().toLowerCase()));
            }
        }
        return zywiolyAsSelectItems;
    }

    public void init() {
        if (mag == null && magId != 0) {
            mag = wiezaService.findMag(magId);

            if (!wiezaService.canChangeMag(mag)) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../error/403.xhtml");
                } catch (IOException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
            }
        } else if (mag == null && magId == 0) {
            mag = new Mag();
        }

        if (mag == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveMag() {
        try {
            wiezaService.saveMag(mag);
            return "list_wieze?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("form", new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "403", "403"));
            return null;
        }
    }
}
