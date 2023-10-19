package dk.jarry.gallery.control;

import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.json.Json;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        int code = 500;
        if (exception instanceof WebApplicationException) {
            code = ((WebApplicationException) exception).getResponse().getStatus();
        }
        return Response.status(code).entity(Json.createObjectBuilder() //
                .add("error", (exception.getMessage() != null ? exception.getMessage() : "")) //
                // .add("stackTrace", stackTrace(exception)) //
                .add("code", code).build()).build();
    }

    String stackTrace(Exception exception) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        exception.printStackTrace(printWriter);
        return writer.toString();
    }

}
