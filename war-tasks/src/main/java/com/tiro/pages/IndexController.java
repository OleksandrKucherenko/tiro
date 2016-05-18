package com.tiro.pages;

import com.tiro.Pages;

import javax.annotation.Nonnull;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Index.JSP page controller. */
@WebServlet(urlPatterns = {"/"})
public class IndexController extends HttpServlet implements Pages {
  private static final long serialVersionUID = 2789062589572783637L;

  /** Redirect 'root' == '/' to index.jsp page */
  @Override
  protected void doGet(@Nonnull final HttpServletRequest request,
                       @Nonnull final HttpServletResponse response)
      throws ServletException, IOException {

    final RequestDispatcher dispatcher = request.getRequestDispatcher(PAGES + "/index.jsp");

    dispatcher.forward(request, response);
  }
}
