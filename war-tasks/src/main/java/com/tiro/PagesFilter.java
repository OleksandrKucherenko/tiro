package com.tiro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**  */
@WebFilter(filterName = "PagesFilter")
@SuppressWarnings({"unused"})
public class PagesFilter implements Filter {
  @Override
  public void init(final FilterConfig config) throws ServletException {

  }

  @Override
  public void doFilter(final ServletRequest rq, final ServletResponse rp, final FilterChain chain)
      throws IOException, ServletException {
    // get more concrete versions of request and response
    final HttpServletRequest request = (HttpServletRequest) rq;
    final HttpServletResponse response = (HttpServletResponse) rp;

    doProcessing(request, response);

    // files inside the WEB-INF folder should be executed without additional processing
    final boolean isWebInf = (request.getRequestURI().contains("/WEB-INF"));

    chain.doFilter(rq, rp);
  }

  @Override
  public void destroy() {

  }

  private void doProcessing(final HttpServletRequest request, final HttpServletResponse response) {
    // TODO: some request, session and response processing
    if (null != request.getAttribute("X-Processed")) return;

    request.setAttribute("X-Processed", true);
    request.setAttribute("RequestURL", request.getRequestURL());
  }
}
