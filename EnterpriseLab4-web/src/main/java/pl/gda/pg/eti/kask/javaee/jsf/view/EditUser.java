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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import pl.gda.pg.eti.kask.javaee.jsf.UserService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.User;

/**
 * @author psysiu
 * @author maciek
 */
@ViewScoped
@ManagedBean
@Log
public class EditUser implements Serializable {

    @EJB
    private WiezaService wiezaService;

    @EJB
    private UserService userService;

    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private User user;

    public void setWiezaService(WiezaService wiezaService) {
        this.wiezaService = wiezaService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void init() {
        // sprawdzanie roli przy save, można by to również zrobić już tutaj
        if (user == null && userId != 0) {
        // edycja usera TYLKO przez admina
            user = userService.findUser(userId);
        } else if (user == null && userId == 0) {
        // edycja hasła przez usera 
            user = wiezaService.getPrincipal();
        }
        
        if (user == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../error/404.xhtml");
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    public String saveUser() {
        try {
            wiezaService.saveUser(user);
            if (wiezaService.isAdmin()) {
                return "/admin/list_users?faces-redirect=true";
            } else {
                return "list_wieze?faces-redirect=true";
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("form", new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "403", "403"));
            return null;
        }
    }
}
