package com.tiro.dao;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/** Create automatically closable transaction. */
@SuppressWarnings("try")
class AutoTransaction implements AutoCloseable {
  /** Reference on transaction. */
  private final EntityTransaction mTransaction;
  /** Is detected nested transaction or not. */
  private final boolean mInNestedTransaction;
  /** Is success of operation confirmed or not. */
  private boolean mIsOk;

  /** Hidden constructor. */
  /* package */ AutoTransaction(@Nonnull final EntityTransaction transaction) {
    this.mTransaction = transaction;
    this.mInNestedTransaction = transaction.isActive();

    if (!mInNestedTransaction) {
      transaction.begin();
    }
  }

  /** Helper. Create instance from entity manager reference. */
  public static AutoTransaction from(@Nonnull final EntityManager em) {
    return new AutoTransaction(em.getTransaction());
  }

  /** Helper. Create instance from transaction reference. */
  public static AutoTransaction from(@Nonnull final EntityTransaction et) {
    return new AutoTransaction(et);
  }

  /** Confirm that all passed normally. */
  public void ok() {
    mIsOk = true;
  }

  /** Auto close the transaction. */
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
