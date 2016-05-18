package com.tiro;

/** Constants used for referencing pages in Controllers. */
public interface Pages {
  /** Root path: "/". */
  String ROOT = "/";

  /** Web application folder. */
  String WEB_INF = "/WEB-INF";

  /** Path to regular pages folder. */
  String PAGES = WEB_INF + "/pages";
  /** Path to error pages folder. */
  String ERRORS = PAGES + "/errors";

  /** Page Not Found Error Page. */
  String E404 = ERRORS + "/404.jsp";
}
