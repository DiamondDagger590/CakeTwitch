package relampagorojo93.caketwitch.spigotthreads.objects;

import relampagorojo93.LibsCollection.SpigotThreads.Exceptions.AlreadyStartedException;
import relampagorojo93.LibsCollection.SpigotThreads.Exceptions.AlreadyStoppedException;

public class Thread implements AutoCloseable {
	private static int LAST_ID = -1;
	private long id = ++LAST_ID;
	private Runnable runnable;
	private CallBack callback;
	private java.lang.Thread thread;
	private java.lang.Runnable threadrunnable = () -> {
		if (callback != null) callback.onStart();
		if (runnable != null) runnable.run();
		if (callback != null) callback.onFinish();
		thread = null;
	};
	public Thread() {}
	public Thread(Runnable runnable) { this(runnable, null); }
	public Thread(Runnable runnable, CallBack callback) {
		this.runnable = runnable;
		this.callback = callback;
	}
	
	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public void setCallBack(CallBack callback) {
		this.callback = callback;
	}
	
	public long getId() {
		return id;
	}
	
	public boolean isStarted() {
		return thread != null;
	}
	public boolean isRunning() {
		return isStarted() && thread.isAlive();
	}
	public boolean isStopping() {
		return isStarted() && thread.isInterrupted();
	}
	
	public void run() {
		this.runnable.run();
	}
	
	public void start() throws AlreadyStartedException {
		if (!isStarted()) (thread = new java.lang.Thread(threadrunnable)).start();
		else throw new AlreadyStartedException();
	}
	public void stop() throws Exception {
		if (!isStarted()) throw new AlreadyStoppedException();
		if (isStopping()) return;
		if (isRunning()) {
			close();
			joinThread();
			thread = null;
		}
	}
	
	public void startSecure() {
		try { start(); } catch (Exception e) {}
	}
	public void stopSecure() {
		try { stop(); } catch (Exception e) {}
	}
	
	public void joinThread() throws InterruptedException {
		if (isRunning()) thread.join();
	}
	
	public void notifyThread() {
		if (isRunning()) thread.notify();
	}
	
	public abstract interface Runnable {
		public abstract void run();
		public abstract void output(Object output);
	}
	
	public abstract interface CallBack {
		public abstract void onFinish();
		public abstract void onInterrupt();
		public abstract void onError(Exception ex);
		public abstract void onInput(Object input);
		public abstract void onStart();
	}

	@Override
	public void close() throws Exception {
		if (callback != null) callback.onInterrupt();
		thread.interrupt();
	}
}
