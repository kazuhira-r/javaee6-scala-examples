package org.littlewings.javaee6.jaxrs;

import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.littlewings.javaee6.service.CalcService;
import org.littlewings.javaee6.service.DateService;

@Path("jndi")
public class JndiLookupResource {
    @GET
    @Path("calc")
    @Produces(MediaType.TEXT_PLAIN)
    public String calc(@QueryParam("p1") int p1, @QueryParam("p2") int p2) throws NamingException {
        InitialContext ic = new InitialContext();
        BeanManager bm = (BeanManager) ic.lookup("java:comp/BeanManager");

        Set<Bean<?>> beans = bm.getBeans(CalcService.class);
        Bean<?> bean = bm.resolve(beans);
        CalcService calcService =
            (CalcService) bm.getReference(bean, CalcService.class, bm.createCreationalContext(bean));

        return Integer.toString(calcService.add(p1, p2)) + System.lineSeparator();
    }

    @GET
    @Path("date")
    @Produces(MediaType.TEXT_PLAIN)
    public String date() throws NamingException {
        InitialContext ic = new InitialContext();
        BeanManager bm = (BeanManager) ic.lookup("java:comp/BeanManager");

        Set<Bean<?>> beans = bm.getBeans(DateService.class);
        Bean<?> bean = bm.resolve(beans);
        DateService dateService =
            (DateService) bm.getReference(bean, DateService.class, bm.createCreationalContext(bean));

        return dateService.time().toString() + System.lineSeparator();
    }
}
