class TestDriver {
	private static void runBasicTest(int numThr, int numIncrements) {
		Thread threads[] = new Thread[numThr];
		Counter ctr = new Counter(numThr, numIncrements);

		for (int i = 0; i < numThr; i++) {
			threads[i] = new Thread(ctr);
			threads[i].start();
		}

		try {
			for (int i = 0; i < numThr; i++) {
				threads[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println("Something went wrong during joining.");
		}

		ctr.printCounterStatus();
	}

	public static void main(String args[]) throws InterruptedException {
		// Make sure to reset ThreadID after each test so that you can use ThreadID.get()
		// in your implementations.
		System.out.print("num:2, increments:1000000\n");
		runBasicTest(2, 1000000);
		ThreadID.reset();

		// TODO: Write some more tests to make sure your implementation works
		// in all situations!
		System.out.print("num:4, increments:1000000\n");
		runBasicTest(4, 1000000);
		ThreadID.reset();

		System.out.print("num:8, increments:1000000\n");
		runBasicTest(8, 1000000);
		ThreadID.reset();

		System.out.print("num:16, increments:100000\n");
		runBasicTest(16, 100000);
		ThreadID.reset();

		//runBasicTest(20, 1000000);
		//ThreadID.reset();
	}
}
