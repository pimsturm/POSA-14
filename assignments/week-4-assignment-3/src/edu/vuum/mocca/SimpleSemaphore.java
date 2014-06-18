package edu.vuum.mocca;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject (which is accessed via a
 *        Condition). It must implement both "Fair" and "NonFair" semaphore
 *        semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
	/**
	 * Define a ReentrantLock to protect the critical section.
	 */
	// TODO - you fill in here
	final private ReentrantLock mRlock;

	/**
	 * Define a Condition that waits while the number of permits is 0.
	 */
	// TODO - you fill in here
	final private Condition mNopermits;

	/**
	 * Define a count of the number of available permits.
	 */
	// TODO - you fill in here. Make sure that this data member will
	// ensure its values aren't cached by multiple Threads..
	volatile int mPermits;

	public SimpleSemaphore(int permits, boolean fair) {
		// TODO - you fill in here to initialize the SimpleSemaphore,
		// making sure to allow both fair and non-fair Semaphore
		// semantics.
		mPermits = permits;
		mRlock = new ReentrantLock(fair);
		mNopermits = mRlock.newCondition();
	}

	/**
	 * Acquire one permit from the semaphore in a manner that can be
	 * interrupted.
	 */
	public void acquire() throws InterruptedException {
		// TODO - you fill in here.
		mRlock.lockInterruptibly();
		try {
			while (mPermits <= 0)
				mNopermits.await();
			mPermits--;
		} finally {
			mRlock.unlock();
		}
	}

	/**
	 * Acquire one permit from the semaphore in a manner that cannot be
	 * interrupted.
	 */
	public void acquireUninterruptibly() {
		// TODO - you fill in here.
		mRlock.lock();
		try {
			while (mPermits <= 0)
				mNopermits.awaitUninterruptibly();
			mPermits--;
		} finally {
			mRlock.unlock();
		}
	}

	/**
	 * Return one permit to the semaphore.
	 */
	public void release() {
		// TODO - you fill in here.
		mRlock.lock();
		try {
			mPermits++;
			if (mPermits > 0)
				mNopermits.signal();
		} finally {
			mRlock.unlock();
		}
	}

	/**
	 * Return the number of permits available.
	 */
	public int availablePermits() {
		// TODO - you fill in here by changing null to the appropriate
		// return value.
		return mPermits;
	}
}
