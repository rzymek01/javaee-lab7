package pl.gda.pg.eti.kask.javaee.rest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Variant;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.javaee.jsf.WiezaService;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Mag;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Swiat;
import pl.gda.pg.eti.kask.javaee.jsf.entities.Wieza;
import pl.gda.pg.eti.kask.javaee.rest.authorization.AuthorizeRole;
import pl.gda.pg.eti.kask.javaee.rest.error.HandleError;

/**
 *
 * @author psysiu
 */
//@Stateless
//@LocalBean
//@DeclareRoles({"Admin", "User"})
@Path("/wieze")
@Log
public class WiezaResource {

  @EJB
  WiezaService wiezaService;

  @Context
  SecurityContext sc;

  @Context
  ServletContext context;

  @Context
  HttpServletRequest request;

  @Context
  HttpServletResponse response;

  @javax.enterprise.inject.Produces
  @RequestScoped
  @pl.gda.pg.eti.kask.javaee.rest.authorization.SecurityContext
  public SecurityContext getSecurityContet() {
    return sc;
  }

  @GET
  @Path("/")
  @Produces({MediaType.APPLICATION_JSON})
  @AuthorizeRole(roles = {"Admin", "User"})
  @HandleError
  public Response findWieze() {
    return Response.ok(new Swiat(wiezaService.findAllWieze())).build();
  }

  @GET
  @Path("/wieza-{id : [0-9]+}")
  @Produces({MediaType.APPLICATION_JSON})
  @AuthorizeRole(roles = {"Admin", "User"})
  @HandleError
  public Response findWieza(@PathParam("id") int id, @Context Request request) {
    List<Variant> vs = Variant
      .mediaTypes(MediaType.APPLICATION_JSON_TYPE)
      .languages(Locale.ENGLISH, Locale.forLanguageTag("pl"))
      .add().build();

    Wieza wieza = wiezaService.findWieza(id);
    if (null != wieza) {
      Variant v = request.selectVariant(vs);
      if (null == v) {
        return Response.notAcceptable(vs).build();
      } else {
        List<Mag> magowie = wieza.getMag();
        for (Mag mag : magowie) {
          mag.setImie(mag.getImie() + "-" + v.getLanguageString());
        }
        
        return Response.ok(wieza).build();
      }
      
    } else {
//      String json = "{}";
//      return Response.ok(json, MediaType.APPLICATION_JSON).build();
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/wieza-{id : [0-9]+}/nowy-mag")
  @AuthorizeRole(roles = {"Admin", "User"})
  @HandleError
  public Response saveNewMag(@PathParam("id") int id, Mag mag) {
    Wieza wieza = wiezaService.findWieza(id);
    if (null == wieza) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    System.out.println("wiezaId: " + wieza.getId() + "; magId: " + mag.getId());
    mag.setWieza(wieza);
    wiezaService.saveMag(mag);
    System.out.println("wiezaId: " + wieza.getId() + "; magId: " + mag.getId());
    return Response.status(Response.Status.CREATED).header("Location", "wieze/wieza-" + wieza.getId()).build();
  }

}
