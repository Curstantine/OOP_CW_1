package moe.curstantine.shared;

import java.util.logging.Logger;

public abstract class AbstractRunnable<T> implements Runnable {
	protected T self;
	protected volatile boolean isRunning;

	public AbstractRunnable(T self) {
		this.self = self;
		this.isRunning = true;
	}

	public T getSelf() {
		return self;
	}
	
	protected abstract void action();

	protected abstract int getDelayDuration();

	protected abstract Logger getLogger();

	@Override
	public void run() {
		while (isRunning) {
			action();

			try {
				//noinspection BusyWait
				Thread.sleep(getDelayDuration());
			} catch (InterruptedException e) {
				getLogger().severe("Thread sleep interrupted: " + e.getMessage());
				break;
			}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Calling this method will not start a new thread after the suspension.
	 * Make sure to instantiate another thread to resume the process.
	 */
	public void start() {
		isRunning = true;
	}

	/**
	 * Calling {@link #stop} will suspend the running function, making the GC cleanup the related Thread.
	 * <p>
	 * When trying to call {@link #start} again, make sure to create a new thread.
	 */
	public void stop() {
		isRunning = false;
	}
}
