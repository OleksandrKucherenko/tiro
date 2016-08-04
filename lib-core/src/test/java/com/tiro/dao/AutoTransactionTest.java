package com.tiro.dao;

import com.tiro.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.mockito.Mockito.*;


/** Unit tests for {@link AutoTransaction} class. */
@RunWith(JUnit4.class)
public class AutoTransactionTest extends BaseTest {

  /** Create a new instance from entity manager reference. */
  @Test
  @SuppressWarnings("try")
  public void testNewInstance() throws Exception {
    EntityManager mockManager = mock(EntityManager.class);
    EntityTransaction mockTransaction = Mockito.mock(EntityTransaction.class);

    when(mockTransaction.isActive()).thenReturn(false);
    when(mockManager.getTransaction()).thenReturn(mockTransaction);

    try (AutoTransaction at = AutoTransaction.from(mockManager)) {
      at.ok();
    }

    verify(mockTransaction).commit();
  }

  /** Create a new instance from transaction reference. */
  @Test
  @SuppressWarnings("try")
  public void testAutoCloseFinally() throws Exception {
    EntityTransaction mockTransaction = Mockito.mock(EntityTransaction.class);
    when(mockTransaction.isActive()).thenReturn(false);

    try (AutoTransaction at = AutoTransaction.from(mockTransaction)) {
      at.ok();
    }

    verify(mockTransaction).commit();
  }

  /** Confirm auto-close call on exception raising. */
  @Test
  @SuppressWarnings("try")
  public void testAutoCloseOnException() throws Exception {
    EntityTransaction mockTransaction = Mockito.mock(EntityTransaction.class);
    when(mockTransaction.isActive()).thenReturn(false);

    try (AutoTransaction at = AutoTransaction.from(mockTransaction)) {
      throw new Error();
    } catch (Throwable ignored) {
      // do nothing
    }

    verify(mockTransaction).rollback();
  }

  /** Confirm that auto-close do nothing in case of nested transaction detection. */
  @Test
  @SuppressWarnings("try")
  public void testNestedTransaction() throws Exception {
    EntityTransaction mockTransaction = Mockito.mock(EntityTransaction.class);
    when(mockTransaction.isActive()).thenReturn(true);

    try (AutoTransaction at = AutoTransaction.from(mockTransaction)) {
      at.ok();
    }

    verify(mockTransaction, never()).commit();
    verify(mockTransaction, never()).rollback();
  }
}
