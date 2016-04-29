package com.tiro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**  */
@WebFilter(filterName = "PagesFilter")
@SuppressWarnings({"unused"})
public class PagesFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

  }

  @Override
  public void destroy() {

  }
}
