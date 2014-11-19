package pl.gda.pg.eti.kask.javaee.jsf.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.WiezaService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;

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
import pl.gda.pg.eti.kask.javaee.jsf.entities.User;

/**
 *
 * @author psysiu
 * @author maciek
 */
@ViewScoped
@ManagedBean
@Log
public class EditWieza implements Serializable {

    @EJB
    private WiezaService wiezaService;

    @Getter
    @Setter
    private int wiezaId;

//    @Getter
//    @Setter
//    private Author a;
    @Getter
    @Setter
    private Wieza wieza;

    private List<SelectItem> magowieAsSelectItems;

//    private List<SelectItem> coversAsSelectItems;
    public void setWiezaService(WiezaService wiezaService) {
        this.wiezaService = wiezaService;
    }

    public List<SelectItem> getMagowieAsSelectItems() {
        if (magowieAsSelectItems == null) {
            magowieAsSelectItems = new ArrayList<>();
            for (Mag mag : wiezaService.findAllMagowie()) {
                magowieAsSelectItems.add(new SelectItem(mag, mag.getImie() + " " + mag.getMana() + " " + mag.getZywiol()));
            }
        }
        return magowieAsSelectItems;
    }

//    public List<SelectItem> getCoversAsSelectItems() {
//        if (coversAsSelectItems == null) {
//            coversAsSelectItems = new ArrayList<>();
//            coversAsSelectItems.add(new SelectItem(null, "---"));
//            for (Cover cover : Cover.values()) {
//                coversAsSelectItems.add(new SelectItem(cover, cover.name().toLowerCase()));
//            }
//        }
//        return coversAsSelectItems;
//    }
    public void init() {
        if (wieza == null && wiezaId != 0) {
            wieza = wiezaService.findWieza(wiezaId);
        } else if (wieza == null && wiezaId == 0) {
            wieza = new Wieza();
        }
        if (wieza == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveWieza() {
        try {
            wiezaService.saveWieza(wieza);
            return "list_wieze?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("form", new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "403", "403"));
            return null;
        }
    }
}
