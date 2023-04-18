import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
	This is the class you will be implementing to guard the critical section
	from the merciless horde of n threads you'll be unleashing. 
*/
public class TreeLock implements Lock {

	/*
	 * See ThreadID.java for some background as to why this is here.
	 * 
	 * Whenever you call THREAD_ID.get() the value returned will be
	 * unique to each thread. Use this as that thread's ID value
	 * instead of calling Thread.currentThread().getId();
	 */
	final private ThreadID THREAD_ID = new ThreadID();

	private int max_depth;
	private PetersonNode[][] TreeLevel;

	TreeLock(int numThreads) {
		// TODO: Initialize your tree
		//depth of the tree
		this.max_depth = (int)Math.ceil(((Math.log(numThreads))/Math.log(2)));//logeN/loge2=log2N, get upper int
		this.TreeLevel = new PetersonNode[(this.max_depth)+1][numThreads+1];
		for(int i=0; i<=this.max_depth; i++){
			//numThreads = numThreads/2;
			for(int j=0; j<=numThreads; j++){
				TreeLevel[i][j] = new PetersonNode(j);//???if there's any prob
			}
		}

	}

	public void lock() {
		int thread_id = THREAD_ID.get();
		// TODO: Implement lock()
		int start = this.max_depth -1;
		int thread_self;

		while(start>=0){
			thread_self = thread_id;
			thread_id = (int)Math.floor(thread_id/2);
			this.TreeLevel[start][thread_id].lock(thread_self);
			start--;
		}
	}

	public void unlock() {
		int thread_id = THREAD_ID.get();
		// TODO: Implement unlock()
		ArrayList<Integer> path = new ArrayList(this.max_depth + 1);
		int start = 0;
		path.add(thread_id);

		//Need to figure out what locks we have to unlock
		for(int i=0 ; i < this.max_depth ; i++){
			thread_id = (int) Math.floor(thread_id/2);
			path.add(thread_id);
		}

		//Using path through tree, I go back and unlock
		for(int j=(path.size() - 1); j >= 1; j--){
			this.TreeLevel[start][path.get(j)].unlock(path.get(j-1));
			start++;
		}
			
	}

	// The compiler wants these declarations since TreeLock implements Lock
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