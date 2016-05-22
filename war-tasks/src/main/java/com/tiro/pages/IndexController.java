package com.tiro.pages;

import com.tiro.Pages;

import javax.annotation.Nonnull;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/** Index.JSP page controller. */
@WebServlet(urlPatterns = {"/", "/index.jsp"}, name = "Home")
public class IndexController extends HttpServlet implements Pages {
  private static final long serialVersionUID = 2789062589572783637L;

  /** Redirect 'root' == '/' to index.jsp page */
  @Override
  protected void doGet(@Nonnull final HttpServletRequest request,
                       @Nonnull final HttpServletResponse response)
      throws ServletException, IOException {

    safeProcessing(request, response, PAGES + "/index.jsp");
  }

  private void safeProcessing(@Nonnull final HttpServletRequest request,
                              @Nonnull final HttpServletResponse response,
                              @Nonnull final String path)
      throws ServletException, IOException {

    final ServletContext context = request.getServletContext();
    final String realPath = context.getRealPath(path);
    final boolean isFileExists = new File(realPath).exists();
    final String finalPath = isFileExists ? path : E404;

    final RequestDispatcher dispatcher = request.getRequestDispatcher(finalPath);

    dispatcher.forward(request, response);
  }
}
