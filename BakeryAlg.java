//import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class BakeryAlg implements Lock {
	/*
	 * See ThreadID.java for some background as to why this is here.
	 * 
	 * Whenever you call THREAD_ID.get() the value returned will be
	 * unique to each thread. Use this as that thread's ID value
	 * instead of calling Thread.currentThread().getId().
	 */

	private final AtomicBoolean[] flag;
	private final AtomicInteger[] label;
	final private ThreadID THREAD_ID = new ThreadID();
	BakeryAlg(int n) {
		flag = new AtomicBoolean[n];
		label = new AtomicInteger[n];
		for (int i = 0; i < n; i++) {
			flag[i] = new AtomicBoolean(false);
			label[i] = new AtomicInteger(0);
		}
	}

	public void lock() {
		int thread_id = THREAD_ID.get();
		// TODO: Implement lock
		flag[thread_id].set(true);
		label[thread_id].set(findMax(label)+1);
		for (int k=0;k<label.length;k++){
			while((k !=thread_id) && flag[k].get()&&((label[k].get() < label[thread_id].get()) || ((label[k].get()==label[thread_id].get()) && k<thread_id)))
			{
              //wait
			}
		}
	}

	private int findMax(AtomicInteger[] label2) {
		int max = Integer.MIN_VALUE;
		for (AtomicInteger element : label2){
			if(element.get()>max){
				max = element.get();
			}
		}
		return max;
	}

	public void unlock() {
		int thread_id = THREAD_ID.get();
		// TODO: Implement unlock
		flag[thread_id].set(false);
	}

	// The compiler wants these declarations since BakeryAlg implements Lock
	public void lockInterruptibly() {
		throw new UnsupportedOperationException();
	}

	public Condition newCondition() {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) {
		throw new UnsupportedOperationException();
	}
}
