package com.veteranscout.cmsweb.thread;

import java.util.LinkedList;

public class ThreadPoolQueue {

	/* item을 저장할 큐 선언 */
	private LinkedList<Object> queue = new LinkedList<Object>();

	/* 큐 최대 사이즈 선언 */
	private int MAX_QUEUE_SIZE = 5;

	/* 디버그를 위한 콘솔 출력 변수 */
	private boolean DEBUG = false;

	public ThreadPoolQueue(int MAX_QUEUE_SIZE) {
		this.MAX_QUEUE_SIZE = MAX_QUEUE_SIZE;
	}

	/* 아이템 삽입 */
	public synchronized void enqueue(Object item) throws InterruptedException {
		try
		{
			// 현재 큐가 최대 사이즈면 멈춤
			while( queue.size() == MAX_QUEUE_SIZE ){
				console("enqueue waiting...");
				wait();
			}

			// 현재 큐가 비어있으면 일어나라!! 일해!!
			if( queue.size() == 0 ){
				console("enqueue notifyall...");
				notifyAll();
			}

			console("enqueue adding...");
			queue.add(item);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}		
	}

	/* 아이템 반환 */
	public synchronized Object dequeue() throws InterruptedException {
		Object obj = null;
		try
		{
			// 반환할 아이템이 없으면 멈춤
			while( queue.size() == 0 ){
				console("dequeue waiting...");
				wait();
			}

			// 반환할 아이템이 가득찼다? 일어나서 일해!!
			if( queue.size() == MAX_QUEUE_SIZE ){
				console("dequeue notifyall...");
				notifyAll();
			}

			console("dequeue removing...");
			obj = queue.remove(0);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		return obj;
	}

	// 디버깅 설정
	public void toggleDebug(boolean flag){
		this.DEBUG = flag;
	}

	public void console(String msg){
		if( DEBUG )
			System.out.println(msg);
	}
}