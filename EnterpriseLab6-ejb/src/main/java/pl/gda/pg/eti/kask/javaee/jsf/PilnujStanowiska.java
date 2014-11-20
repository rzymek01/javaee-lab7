/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.kask.javaee.jsf;

import java.io.Serializable;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;

/**
 *
 * @author maciek
 */
@Interceptor
@SyndromPSLu
public class PilnujStanowiska implements Serializable {

  @Inject
  @MyEntityManager
  private EntityManager em;

  @AroundInvoke
  public Object invoke(InvocationContext context) throws Exception {
    Object param = context.getParameters()[0];
    if (param instanceof Mag) {
      Mag mag = (Mag) param;

      if (mag.getId() <= 0) {
        int manaDyrektora = (int) em.createQuery("select max(m.mana) from Mag m").getSingleResult();
        int manaPelikana = mag.getMana();

        if (manaPelikana >= manaDyrektora) {
          mag.setMana(1);
        }
      }
    }

    Object result = context.proceed();
    return result;
  }
}
