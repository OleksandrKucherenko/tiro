package com.tiro;

import javax.ws.rs.ApplicationPath;

/**
 * JSP application. Required for enabling JSP support inside the module.
 * <p>
 * Important:<br/>
 * - application path should correspond build.gradle 'gretty/contextPath' value;<br/>
 */
@ApplicationPath("/tasks")
public class TiroApplication extends javax.ws.rs.core.Application {
}