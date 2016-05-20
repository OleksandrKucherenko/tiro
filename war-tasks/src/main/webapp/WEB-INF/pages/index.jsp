<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>INDEX</title>
  </head>
  <body>
    <%! double num = Math.random(); %>
    <h1>Random <%= num %></h1>
    <a href="${RequestURL}"><h3>Try Again</h3></a>
  </body>
</html>