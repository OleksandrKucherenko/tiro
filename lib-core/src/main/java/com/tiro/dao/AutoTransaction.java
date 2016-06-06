package com.tiro.dao;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/** Create automatically closable transaction. */
class AutoTransaction implements AutoCloseable {

  private final EntityTransaction mTransaction;
  private final boolean mInNestedTransaction;
  private boolean mIsOk;

  /* package */ AutoTransaction(@Nonnull final EntityTransaction transaction) {
    this.mTransaction = transaction;
    this.mInNestedTransaction = transaction.isActive();

    if (!mInNestedTransaction) {
      transaction.begin();
    }
  }

  public static AutoTransaction from(@Nonnull final EntityManager em) {
    return new AutoTransaction(em.getTransaction());
  }

  public static AutoTransaction from(@Nonnull final EntityTransaction et) {
    return new AutoTransaction(et);
  }

  public void ok() {
    mIsOk = true;
  }

  @Override
  public void close() throws Exception {
    if (!mInNestedTransaction) {
      if (mIsOk) {
        mTransaction.commit();
      } else {
        mTransaction.rollback();
      }
    }
  }
}
