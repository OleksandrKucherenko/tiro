package com.tiro;

import com.tiro.rest.SecurityRestServiceV1;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/** Warmup helper, increase the speed of the unit tests. */
public final class WarmupHelper {
  /** Unit test logger. */
  private static final org.slf4j.Logger _log = LoggerFactory.getLogger(Consts.TAG);
  /** Is initialization already passed of not. */
  private static final AtomicBoolean SYNC_WARM_UP = new AtomicBoolean();
  /** Time of grizzly warmup thread start. */
  public static final long TRICK_WARM_UP = warmUpGrizzly();

  /** Force Grizzly to accomplish heavy initialization before the real test started. */
  private static long warmUpGrizzly() {
    final long start = System.nanoTime();

    final JerseyTest warmup = new JerseyTest() {
      {
        set(TestProperties.RECORD_LOG_LEVEL, Level.OFF.intValue());
      }

      @Override
      protected Application configure() {
        return new ResourceConfig(SecurityRestServiceV1.class);
      }
    };

    final Thread thread = new Thread(() -> {
      try {
        warmup.setUp();
        warmup.tearDown();
      } catch (Exception ignored) {
      } finally {
        synchronized (SYNC_WARM_UP) {
          SYNC_WARM_UP.set(true);
          SYNC_WARM_UP.notifyAll();
        }
      }
    }, "grizzly-warmup");
    thread.setPriority(Thread.MAX_PRIORITY);
    thread.setDaemon(true);
    thread.start();

    return start;
  }

  /** Wait untill the warmup thread finish own job. */
  public static void warmUpGrizzlyWaiting() {
    if (!SYNC_WARM_UP.get()) {
      synchronized (SYNC_WARM_UP) {
        if (!SYNC_WARM_UP.get()) {
          try {
            SYNC_WARM_UP.wait();
          } catch (InterruptedException e) {
          }
        }
      }
    }

    _log.info("warmup takes: {}ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - TRICK_WARM_UP));
  }
}
