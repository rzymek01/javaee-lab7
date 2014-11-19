package pl.gda.pg.eti.kask.javaee.jsf;

import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.ObjectFactory;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Swiat;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import pl.gda.pg.eti.kask.javaee.jsf.entities.User;

/**
 * @author psysiu
 * @author maciek
 */
@Stateless
@LocalBean
@Log
//@DeclareRoles(value = {"Admin", "User"})
public class WiezaService implements Serializable {

    @Inject
    @MyEntityManager
    EntityManager em;

    @Resource
    SessionContext sctx;

    @EJB
    UserService userService;

    @Inject
    Event<Mag> nowyMag;

    public WiezaService() {

    }

    private List<Mag> asList(Mag... magowie) {
        return findAllMagowie();
    }

    @RolesAllowed({"Admin", "User"})
    public List<Wieza> findAllWieze() {
        return em.createNamedQuery("Wieza.findAll").getResultList();
    }

    @RolesAllowed({"Admin", "User"})
    public Wieza findWieza(int id) {
        return em.find(Wieza.class, id);
    }
    
    public List<Mag> findMegaMagowie(int amount) {
        return em.createNamedQuery("Mag.findMega").setMaxResults(amount).getResultList();
    }

    @RolesAllowed({"Admin", "User"})
    public void removeWieza(Wieza wieza) {
        // sprawdzenie uprawnień
        User user = wieza.getOwner();
        checkOwnerOrAdmin(user);

        wieza = em.merge(wieza);
        em.remove(wieza);
    }

    @RolesAllowed({"Admin", "User"})
    public void saveWieza(Wieza wieza) {
        if (wieza.getId() > 0) {
            // sprawdzenie uprawnień
            User user = wieza.getOwner();
            checkOwnerOrAdmin(user);

            em.merge(wieza);
        } else {
            User principal = getPrincipal();
            wieza.setOwner(principal);

            em.persist(wieza);
        }
    }

    @RolesAllowed({"Admin", "User"})
    public void trenujMagow(int incr) {
        em.createNamedQuery("Mag.increaseManaForAll").setParameter("incr", incr).
                executeUpdate();
    }

    @RolesAllowed({"Admin", "User"})
    public List<Mag> findAllMagowie() {
        return em.createNamedQuery("Mag.findAll").getResultList();
    }

    @RolesAllowed({"Admin", "User"})
    public Mag findMag(int id) {
        return em.find(Mag.class, id);
    }

    @RolesAllowed({"Admin", "User"})
    @Asynchronous
    public Future<Void> removeMag(Mag mag) {
        // sprawdzenie uprawnień
        User user = mag.getWieza().getOwner();
        checkOwnerOrAdmin(user);

        mag = em.merge(mag);
        em.remove(mag);
        nowyMag.fire(mag);
        
        return new AsyncResult<>(null);
    }

    @RolesAllowed({"Admin", "User"})
    public boolean canChangeMag(Mag mag) {
        try {
            User user = mag.getWieza().getOwner();
            checkOwnerOrAdmin(user);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RolesAllowed({"Admin", "User"})
    @SyndromPSLu
    public void saveMag(Mag mag) {
        if (mag.getId() > 0) {
            // sprawdzenie uprawnień
            User user = mag.getWieza().getOwner();
            checkOwnerOrAdmin(user);

            em.merge(mag);
        } else {
            em.persist(mag);
            nowyMag.fire(mag);
        }
    }

    @RolesAllowed({"Admin", "User"})
    public User getPrincipal() {
        User principal = userService.findUserByLogin(sctx.getCallerPrincipal().getName());
        return principal;
    }

    @RolesAllowed({"Admin", "User"})
    public boolean isAdmin() {
        return sctx.isCallerInRole("Admin");
    }

    @RolesAllowed({"Admin", "User"})
    public void saveUser(User user) {
        // sprawdzenie uprawnień
        checkOwnerOrAdmin(user);

        try {
            // zahashowanie hasła SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            String text = user.getPassword();
            md.update(text.getBytes("UTF-8"));
            byte[] digest = md.digest();
            String hashedPasswd = WiezaService.getHex(digest);

            user.setPassword(hashedPasswd);

            // zapisanie usera
            if (user.getId() > 0) {
                em.merge(user);
            } else {
                em.persist(user);
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(WiezaService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RolesAllowed({"Admin", "User"})
    public void marshalSwiat(OutputStream out) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
            Marshaller m = jaxbContext.createMarshaller();
            Swiat swiat = new Swiat();
            swiat.getWieza().addAll(findAllWieze());
            m.marshal(swiat, out);
        } catch (JAXBException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

    protected void checkOwnerOrAdmin(User user) throws EJBException {
        User principal = getPrincipal();
        if (!principal.getId().equals(user.getId())) {
            // zezwalaj tylko adminowi
            if (!isAdmin()) {
                throw new EJBException("403");
            }
        }
    }

    static final String HEXES = "0123456789ABCDEF";

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}
