package com.tiro.dao;

import com.tiro.BaseDatabaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.*;

/** Unit tests for {@link DaoFactory} class. */
@RunWith(JUnit4.class)
public class DaoFactoryTest extends BaseDatabaseTest {

  @Test
  public void testNewInstanceUserDao() throws Exception {
    final UserDao userDao = DaoFactory.get(mEm, UserDao.class);
    assertThat(userDao).isNotNull();
  }

  @Test
  public void testNewInstanceGroupDao() throws Exception {
    final GroupDao groupDao = DaoFactory.get(mEm, GroupDao.class);
    assertThat(groupDao).isNotNull();
  }

  @Test
  public void testNewInstanceSecurityDao() throws Exception {
    final SecurityDao securityDao = DaoFactory.get(mEm, SecurityDao.class);
    assertThat(securityDao).isNotNull();
  }

  @Test
  public void testNotImplemented() throws Exception {
    assertThatThrownBy(() -> {
      DaoFactory.get(mEm, Dao.class);
    }).isInstanceOf(Error.class);
  }
}
