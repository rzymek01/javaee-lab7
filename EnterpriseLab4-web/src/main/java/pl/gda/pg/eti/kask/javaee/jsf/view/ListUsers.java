package pl.gda.pg.eti.kask.javaee.jsf.view;

import pl.gda.pg.eti.kask.javaee.jsf.WiezaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import pl.gda.pg.eti.kask.javaee.jsf.UserService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.User;

/**
 *
 * @author psysiu
 */
@ViewScoped
@ManagedBean
public class ListUsers implements Serializable {

//    @EJB
//    WiezaService wiezaService;
    
    @EJB
    UserService userService;

//    private List<Entry<User, List<Mag>>> users;
    private List<User> users;
    
//    public List<Entry<User, List<Mag>>> getUsers() {
    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>(userService.findAllUsers());
        }
        return users;
    }
}
