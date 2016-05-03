package com.tiro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**  */
@WebFilter(filterName = "PagesFilter")
@SuppressWarnings({"unused"})
public class PagesFilter implements Filter {
  @Override
  public void init(final FilterConfig config) throws ServletException {

  }

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {

    final HttpServletRequest hsr = (HttpServletRequest) request;

    if (hsr.getRequestURI().contains("/WEB-INF")) {
      chain.doFilter(request, response);
      return;
    }

    // TODO: some request processing

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
