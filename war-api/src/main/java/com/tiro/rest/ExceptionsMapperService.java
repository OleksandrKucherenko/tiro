package com.tiro.rest;

import com.tiro.rest.responses.Error;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exceptions handler provider.
 * <p>
 * References:
 * <a href="http://www.tutorialspoint.com/servlets/servlets-exception-handling.htm">Servlets Exception Handling</a>
 */
@Provider
public class ExceptionsMapperService implements ExceptionMapper<Throwable> {
  /** Injected instance of the context. */
  @Context private ServletContext mContext;
  /** Injected instance of the request. */
  @Context private HttpServletRequest mRequest;

  /** {@inheritDoc} */
  @Override
  public Response toResponse(final Throwable throwable) {
    final Error errorInfo = Error.from(mRequest, throwable);

    // ALTERNATIVE:
    //   return Response.serverError().build();

    // for all cases we will return JSON error
    return Response
        .status(Response.Status.INTERNAL_SERVER_ERROR)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(errorInfo)
        .build();
  }
}
