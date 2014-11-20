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
import pl.gda.pg.eti.kask.javaee.jsf.entities.Swiat;
import pl.gda.pg.eti.kask.javaee.rest.authorization.AuthorizeRole;
import pl.gda.pg.eti.kask.javaee.rest.error.HandleError;

/**
 *
 * @author psysiu
 */
//@Stateless
//@LocalBean
//@DeclareRoles({"Admin", "User"})
@Path("/books")
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
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML})
//    @AuthorizeRole(roles = {"Admin", "User"})
    @Path("/")
//    @RolesAllowed({"Admin", "User"})
    public Response findWieze(@DefaultValue("0") @QueryParam("offset") int offset, @DefaultValue("0") @QueryParam("limit") int limit) {
        if (limit > 0) {
            return Response.ok(new Swiat(wiezaService.findAllWieze())).build();
        } else {
            return Response.ok(new Swiat(wiezaService.findAllWieze())).build();
        }
        
    }

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("/{id:[0-9]+}")
//    public Response findBookJson(@PathParam("id") Integer id) {
//        if (sc.isUserInRole("Admin") || sc.isUserInRole("User")) {
//            Book book = null;
//            try {
//                book = wiezaService.findBook(id);
//            } catch (EJBException ex) {
//                if (ex.getCause() instanceof AuthException) {
//                    return Response.status(Response.Status.UNAUTHORIZED).build();
//                } else {
//                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//                }
//            }
//            if (book != null) {
//                return Response.ok(book).build();
//            } else {
//                return Response.status(Response.Status.NOT_FOUND).build();
//            }
//        } else {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//    }
//
//    @GET
//    @Produces({MediaType.APPLICATION_XML})
//    @Path("/{id:[0-9]+}")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response findBookXML(@PathParam("id") Integer id) {
//        Book book = wiezaService.findBook(id);
//        if (book != null) {
//            return Response.ok(new JAXBElement<Book>(new QName("http://www.eti.pg.gda.pl/kask/javaee/books", "Book"), Book.class, book)).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//
//    }
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response saveNewBook(Book book) {
//        book = wiezaService.saveBook(book);
//        return Response.status(Response.Status.CREATED).header("Location", "books/" + book.getId()).build();
//    }
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/{id:[0-9]+}")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response saveBook(Book book) {
//        Book oldBook = wiezaService.findBook(book.getId());
//        if (oldBook == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        } else {
//            wiezaService.saveBook(book);
//            return Response.status(Response.Status.OK).build();
//        }
//    }
//
//    @DELETE
//    @Path("/{id:[0-9]+}")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response deleteBook(@PathParam("id") Integer id) {
//        Book book = wiezaService.findBook(id);
//        if (book == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        } else {
//            wiezaService.removeBook(book);
//            return Response.status(Response.Status.OK).build();
//        }
//    }
//
//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    @Path("/{id:[0-9]+}/authors")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response findBookAuthors(@PathParam("id") Integer id) {
//        Book book = wiezaService.findBook(id);
//        if (book != null) {
//            return Response.ok(new Authors(book.getAuthors())).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Path("/{id:[0-9]+}/title")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response findBookTitle(@PathParam("id") int id) {
//        Book book = wiezaService.findBook(id);
//        if (book != null) {
//            return Response.ok(book.getTitle()).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/authors")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response findAuthors() {
//        return Response.ok(new Authors(wiezaService.findAllAuthors())).build();
//    }
//
//    @GET
//    @Path("/authors/{id:[0-9]+}/books")
//    @AuthorizeRole(roles = {"Admin", "User"})
//    @HandleError
//    public Response findAuthorBooks(@PathParam("id") Integer id, @Context Request r) {
//        Author author = wiezaService.findAuthor(id);
//        List<Variant> vs = Variant
//                .mediaTypes(MediaType.APPLICATION_XML_TYPE, MediaType.APPLICATION_JSON_TYPE)
//                .languages(Locale.ENGLISH, Locale.forLanguageTag("pl"))
//                .add().build();
//        if (author != null) {
//            Variant v = r.selectVariant(vs);
//            if (v == null) {
//                return Response.notAcceptable(vs).build();
//            } else {
//                for (Book book : author.getBooks()) {
//                    book.setTitle(book.getTitle() + "-" + v.getLanguageString());
//                }
//                return Response.ok(new Books(author.getBooks())).build();
//            }
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }
//
//    @POST
//    @Path("/authors")
//    public Response saveNewAuthor(
//            @FormParam("name") String name,
//            @FormParam("surname") String surname) {
//        Author author = new Author();
//        author.setName(name);
//        author.setSurname(surname);
//        author = wiezaService.saveAuthor(author);
//        return Response.status(Response.Status.CREATED).header("Location", "books/authors/" + author.getId()).build();
//    }
}
