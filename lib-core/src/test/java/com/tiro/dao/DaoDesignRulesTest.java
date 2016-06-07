package com.tiro.dao;

import com.tiro.BaseTest;
import com.tiro.Categories;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import java.lang.reflect.Modifier;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

/** Unit tests that confirms following by developer of design rules. */
@RunWith(JUnit4.class)
@Category({Categories.DesignRules.class})
@SuppressWarnings({"unchecked"})
public class DaoDesignRulesTest extends BaseTest {
  /** Check that DAO Factory return instance for each known DAO class. */
  @Test
  public void testConfirmDaoFactory() throws Exception {
    final Set<Class<? extends Dao>> daos = getReflections().getSubTypesOf(Dao.class);
    final EntityManager manager = Mockito.mock(EntityManager.class);

    daos.stream()
        .filter(cz -> !Modifier.isAbstract(cz.getModifiers()))
        .forEach(cz -> {
          _log.info("Verifying: {}", cz.getName());
          final Dao dao = DaoFactory.get(manager, cz);

          assertThat(dao).isNotNull();
        });
  }
}
