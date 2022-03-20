package de.papenhagen;

import de.papenhagen.enities.CSVInput;
import de.papenhagen.service.ZipCodeService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/zipcode")
public class ZipCodeResource {

    @Inject
    ZipCodeService zipCodeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{zipcode}")
    public Response zipCode(@PathParam("zipcode") String zipcode) {
        try {
            final long parseLong = Long.parseLong(zipcode);
            final Optional<CSVInput> csvInput = zipCodeService.filterByZipcode(parseLong);
            if (csvInput.isEmpty()) {
                return Response.noContent().build();
            }
            return Response.ok(csvInput.get()).build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}