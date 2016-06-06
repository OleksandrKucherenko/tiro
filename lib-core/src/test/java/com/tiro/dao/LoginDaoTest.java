package com.tiro.dao;

import com.tiro.BaseDatabaseTest;
import com.tiro.entities.User;
import com.tiro.exceptions.CoreException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.*;

/** Unit tests for {@link LoginDao} class. */
@RunWith(JUnit4.class)
public class LoginDaoTest extends BaseDatabaseTest {

  private LoginDao mDao;

  @Override
  protected void onSetup() {
    mDao = new LoginDao(mEm);
  }

  @Override
  protected void onTearDown() {
    mDao = null;
  }

  @Test
  public void testFindById() throws Exception {
    final User newUser = mDao.newUser("sample@example.com");
    mDao.include(newUser);
    mDao.forcedPersist();

    final User user = mDao.findUser(1L);

    assertThat(user).isNotNull();
  }

  @Test
  public void testFindByIdNonExistingUser() throws Exception {
    assertThatThrownBy(() -> {
      mDao.findUser(-1L);
    }).isInstanceOf(CoreException.class);
  }
}
