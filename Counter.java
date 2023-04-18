/*
  This is the counter that will be incremented by all the threads
*/
class Counter implements Runnable {
	// Counters for TreeLock and the Bakery Lock, respectively.
	private int tCount = 0;
	private int bCount = 0;

	private TreeLock tLock;
	private BakeryAlg bLock;

	private int numIncrementsPerThr;
	// When NUM_INCREMENTS is not divisible by numThr, the final result
	// will not be a million. It will be (NUM_INCREMENTS / numThr) * numThr.
	private int expectedTotalIncrements;

	/*
	 * When a million is not evenly divisible my numThr, the final result
	 * will not be a million. That should be okay as long as the final counter
	 * output is equal to ((1000000 / numThr) * numThr) every time you run the
	 * program
	 */
	Counter(int numThr, int numIncrements) {
		this.numIncrementsPerThr = numIncrements / numThr;
		this.expectedTotalIncrements = numIncrementsPerThr * numThr;

		tLock = new TreeLock(numThr);
		bLock = new BakeryAlg(numThr);
	}

	public void run() {
		for (int i = 0; i < numIncrementsPerThr; i++) {
			tLock.lock();
			tCount++;
			tLock.unlock();
		}

		for (int i = 0; i < numIncrementsPerThr; i++) {
			bLock.lock();
			bCount++;
			bLock.unlock();
		}
	}

	public void printCounterStatus() {
		String tSymbol = expectedTotalIncrements == tCount ? "✅" : "❌";
		String bSymbol = expectedTotalIncrements == bCount ? "✅" : "❌";

		// This method should be called by the parent thread (after all have finished
		// running), so we don't have to worry about these being printed out of order.
		System.out.printf("⭐ Expected count: %d\n", expectedTotalIncrements);
		System.out.printf(tSymbol + " TreeLock count: %d\n", tCount);
		System.out.printf(bSymbol + " Bakery count: %d\n", bCount);
	}

	// You don't necessarily need this, but it could be useful if you're automating
	// tests.
	public boolean areCountersCorrect() {
		return expectedTotalIncrements == tCount && expectedTotalIncrements == bCount;
	}
}
