<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<% response.setStatus(404); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Page Not found</title>
    </head>
    <body>
        <button onclick="history.back()">Back to Previous Page</button>
        <h1>404 Page Not Found.</h1>
        <br />
        <p><b>Error code:</b> ${pageContext.errorData.statusCode}</p>
        <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
        <br />
    </body>
</html>