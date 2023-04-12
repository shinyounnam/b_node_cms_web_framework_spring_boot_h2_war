package com.veteranscout.cmsweb.thread;

import java.lang.*;

public class ThreadPoolRunnable implements Runnable {

	private int id; // Runnable ID
	private ThreadPoolQueue queue;
	private volatile boolean running = true;
	private boolean DEBUG = false;

	
	public ThreadPoolRunnable(int THREAD_ID, ThreadPoolQueue queue) {
		this.id = THREAD_ID;
		this.queue = queue;
		console("ThreadPoolRunnable["+id+"] is created.");
	}

	
	@Override
	public void run() {
		while( running ){
			try{
				Thread.sleep(10);
				console("ThreadPoolRunnable["+id+"] is working.");
				Runnable r = (Runnable) queue.dequeue();
				r.run();
				
			}catch( InterruptedException e ){
				stop(); 
			}
		}
		console("ThreadPoolRunnable["+id+"] is dead.");
	}

	
	public void stop() {
		running = false;
		console("ThreadPoolRunnable["+id+"] is stopped.");
	}

	
	public void toggleDebug(boolean flag){
		this.DEBUG = flag;
	}

	public void console(String msg){
		if( DEBUG )
			System.out.println(msg);
	}
}