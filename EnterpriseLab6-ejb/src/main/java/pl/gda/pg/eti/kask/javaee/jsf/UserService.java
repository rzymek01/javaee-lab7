/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.entities.User;

/**
 *
 * @author psysiu
 */
@Stateless
@LocalBean
@Log
public class UserService {

    @Inject
    @MyEntityManager
    EntityManager em;

    public User findUser(int id) {
        return em.find(User.class, id);
    }
    
    public User findUserByLogin(String login) {
        return (User)em.createNamedQuery("User.findByLogin").setParameter("login", login).getSingleResult();
    }

    public List<User> findAllUsers() {
        return em.createNamedQuery("User.findAll").getResultList();
    }
}
