package com.tiro.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** Unit tests of Security pattern - Users <--> Groups <--> Roles. */
@RunWith(JUnit4.class)
public class UsersGroupsRolesSecurityTest {
  private static final String ALL_MODEL_PACKAGES = User.class.getPackage().getName() + ".*";

  private EntityManager em;

  @Before
  public void setUp() throws Exception {
    // register all entities
    com.objectdb.Enhancer.enhance(ALL_MODEL_PACKAGES);

    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("objectdb:build/tmp/testing.tmp;drop");
    em = factory.createEntityManager();

    // create minimalistic set of entities in tables for good testing

  }

  @After
  public void tearDown() throws Exception {
    if (null != em) {
      em.close();
    }
  }

  /** Create database schema from entities. */
  @Test
  public void testCreateSchema() throws Exception {


  }
}
