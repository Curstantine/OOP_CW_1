package moe.curstantine.shared;

public abstract class AbstractRunnable<T> implements Runnable {
	protected T self;
	protected boolean isRunning;

	public AbstractRunnable(T self) {
		this.self = self;
		this.isRunning = true;
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
