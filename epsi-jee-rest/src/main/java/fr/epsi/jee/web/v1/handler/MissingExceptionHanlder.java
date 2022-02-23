package fr.epsi.jee.web.v1.handler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Provider
public class MissingExceptionHanlder implements ExceptionMapper<ConstraintViolationException> {

    class Error {
        String propertyName;
        String message;

        public Error(String propertyName, String message) {
            this.propertyName = propertyName;
            this.message = message;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public String getMessage() {
            return message;
        }
    }
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepare(exception))
                .build();
    }

    private List<Error> prepare(ConstraintViolationException exception) {
        final List errors = new ArrayList<Error>();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            Path.Node name= StreamSupport.stream(cv.getPropertyPath().spliterator(), false)
                    .reduce((first, second) -> second).orElse(null);
            errors.add(new Error(String.valueOf(name), cv.getMessage()));
        }
        return errors;
    }

}
