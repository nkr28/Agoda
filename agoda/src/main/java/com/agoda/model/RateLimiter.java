package com.agoda.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class RateLimiter {

	Queue<Calendar> queue;
	private int maxPermits;
	private long suspensionInterval;
	private int windowDuration;
	private Calendar suspendedTime;

	public synchronized boolean rateCheck() {
		boolean res = true;
		Calendar currentTime = Calendar.getInstance();
		if (suspendedTime != null
				&& (currentTime.getTimeInMillis() - suspendedTime.getTimeInMillis() < (suspensionInterval * 1000))) {
			return false;
		}
		while (!queue.isEmpty()
				&& (currentTime.getTimeInMillis() - queue.peek().getTimeInMillis() >= (windowDuration * 1000))) {
			queue.poll();
		}
		if (queue.size() < maxPermits) {
			queue.add(Calendar.getInstance());
		} else {
			res = false;
			suspendedTime = currentTime;
		}
		return res;
	}

	public RateLimiter(int maxPermits, long suspensionInterval, int windowDuration) {
		super();
		this.queue = new LinkedList<Calendar>();
		this.maxPermits = maxPermits;
		this.suspensionInterval = suspensionInterval;
		this.windowDuration = windowDuration;
	}

}