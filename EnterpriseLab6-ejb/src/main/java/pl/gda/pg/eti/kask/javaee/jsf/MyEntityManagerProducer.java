/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author maciek
 */
public class MyEntityManagerProducer {
    @PersistenceContext
    EntityManager em;

    @Produces
    @MyEntityManager
    public EntityManager getManager() {
        return em;
    }
}
