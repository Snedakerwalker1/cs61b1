/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
/* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
    	if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		/* lld1.printDeque(); */

		printTestStatus(passed);

	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");

		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);

	}
	/**
	 Add's to both sides and removes from both sides to check that the system actually works!
	 **/
	public static void addRemovaddtest() {
		System.out.println("Running add remove add test");
		LinkedListDeque<String> strlist1 = new LinkedListDeque<>();
		// adds to the front and back
		strlist1.addFirst("hi");
		strlist1.addLast("Computer");
		// removes both items
		strlist1.removeLast();
		strlist1.removeLast();
		boolean passed = checkEmpty(true, strlist1.isEmpty());
		// adds to the list again
		strlist1.addLast("bi");
		strlist1.addLast("comp");
		boolean pass = strlist1.removeFirst().equals("bi");
		passed = pass && passed;
		printTestStatus(passed);
	}
	public static void indextest() {
		System.out.println("running index test");
		LinkedListDeque<Integer> intls = new LinkedListDeque<>();
		intls.addFirst(2);
		intls.addFirst(3);
		boolean passed = (null == intls.get(3));
		passed = (2 == intls.get(1)) && passed;
		passed = (3 == intls.get(0)) && passed;
		intls.addLast(5);
		passed = passed && (5 == intls.getRecursive(2));
		passed = (3 == intls.getRecursive(0)) && passed;
		printTestStatus(passed);
	}
	public static void removeempty() {
		System.out.println("Running remove empty test");
		LinkedListDeque<Integer> intls = new LinkedListDeque<>();
		boolean passed = (null == intls.removeLast());
		passed = (null == intls.removeFirst()) && passed;
		intls.addFirst(2);
		intls.removeFirst();
		passed = (null == intls.removeFirst()) && passed;
		printTestStatus(passed);

	}
	public static void arraytest() {
		System.out.println("Running array test");
		ArrayDeque<Integer> intarr = new ArrayDeque<>();
		boolean passed = (intarr.isEmpty());
		intarr.addFirst(3);
		intarr.addFirst(2);
		intarr.addLast(4);
		passed = passed && (intarr.get(2) == 4);
		intarr.addLast(5);
		intarr.addLast(6);
		intarr.addLast(7);
		intarr.addLast(0);
		passed = passed && (intarr.get(6) == 0);
		passed = passed && (intarr.size() == 7);
		intarr.addFirst(1);
		intarr.addFirst(0);
		intarr.addLast(15);
		intarr.addFirst(16);
		passed = passed && (intarr.get(10) == 15);
		intarr.removeFirst();
		passed = passed && (intarr.get(10) == null);
		intarr.removeLast();
		passed = passed && (intarr.get(9) == null);
		printTestStatus(passed);
	}

	public static void difftypetest() {
		ArrayDeque<String> strarr = new ArrayDeque<>();
		ArrayDeque<Double> darr = new ArrayDeque<>();
		strarr.addFirst("hi");
		darr.addLast(2.1);
		strarr.printDeque();
		darr.printDeque();
	}
	public static void firsttest() {
		ArrayDeque<Integer> intarr = new ArrayDeque<>();
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
		intarr.addLast(0);
	}
	public static void addfornt() {
		ArrayDeque<Double> darr = new ArrayDeque<>();
		int count = 0;
		while (count < 20) {
			darr.addFirst(3.14);
			count += 1;
		}
		while (count > 3) {
			darr.removeFirst();
			count -= 1;
		}
		while (count < 9) {
			darr.addFirst(3.14);
			count += 1;
		}
		darr.printDeque();

	}

	public static void addremove() {
		ArrayDeque<String> strarr = new ArrayDeque<>();
		strarr.addLast("hi");
		System.out.println(strarr.removeFirst());
	}
	public static void gettest() {
		ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
		arrayDeque.addFirst(0);
		arrayDeque.addFirst(1);
		arrayDeque.addFirst(2);
		arrayDeque.addLast(3);
		arrayDeque.addLast(4);
		arrayDeque.removeFirst();
		arrayDeque.get(3);
		arrayDeque.addFirst(7);
		arrayDeque.addLast(8);
		arrayDeque.addLast(9);
		arrayDeque.addFirst(10);
		arrayDeque.addLast(11);
		arrayDeque.removeFirst();
		arrayDeque.removeFirst();
		arrayDeque.addFirst(14);
		arrayDeque.get(2);
		arrayDeque.addLast(16);
		arrayDeque.addFirst(17);
		arrayDeque.addFirst(18);
		arrayDeque.addFirst(19);
		arrayDeque.removeLast();
		arrayDeque.removeLast();
		printTestStatus(arrayDeque.removeLast() == 9);
	}
	public static void randomtest() {
		ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
		arrayDeque.addFirst(0);
		arrayDeque.removeLast();
		arrayDeque.addFirst(2);
		arrayDeque.addLast(3);
		arrayDeque.removeFirst();
		arrayDeque.addFirst(5);
		arrayDeque.addLast(6);
		arrayDeque.addLast(7);
		arrayDeque.addLast(8);
		arrayDeque.addFirst(9);
		arrayDeque.removeFirst();
		arrayDeque.get(3);
		arrayDeque.addFirst(12);
		arrayDeque.addLast(13);
		arrayDeque.removeLast();
		arrayDeque.addFirst(15);
		arrayDeque.get(3);
		arrayDeque.addLast(17);
		arrayDeque.removeLast();
		arrayDeque.removeLast();
	}
	public static void gettes() {
		ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
		arrayDeque.addFirst(0);
		arrayDeque.addLast(1);
		arrayDeque.addFirst(2);
		arrayDeque.removeFirst();
		arrayDeque.addFirst(4);
		arrayDeque.addFirst(5);
		arrayDeque.get(2);
		arrayDeque.addFirst(7);
		arrayDeque.addLast(8);
		arrayDeque.removeLast();
		arrayDeque.addLast(10);
		arrayDeque.removeFirst();
		arrayDeque.addFirst(12);
		arrayDeque.addLast(13);
		arrayDeque.addFirst(14);
		arrayDeque.removeFirst();
		arrayDeque.addFirst(16);
		arrayDeque.removeFirst();
		arrayDeque.removeLast();
	}
	public static void addlast() {
		int count = 0;
		ArrayDeque<Integer> intlst = new ArrayDeque<>();
		boolean passed = true;
		while (count < 100) {
			intlst.addLast(count);
			count += 1;
		}
		while (count > 1) {
			count -= 1;
			passed = passed && (intlst.removeLast() == count);
		}
		while (count < 100) {
			intlst.addLast(count);
			count += 1;
		}
		while (count > 1) {
			count -= 1;
			passed = passed && (intlst.removeLast() == count);
		}
		printTestStatus(passed);
	}
	public static void lasttest() {
		ArrayDeque<String> stra = new ArrayDeque<>();
		stra.addLast("mine");
		stra.addLast("mine");
		stra.addLast("mine");
		stra.addFirst("mine");
		stra.addFirst("mine");
		stra.addFirst("mine");
		stra.addFirst("mine");
		stra.addFirst("mine");
		stra.addFirst("mine");
		stra.removeLast();
	}
	public static void finaltest() {
		ArrayDeque<Integer> intarr = new ArrayDeque<>();
		int count = 0;
		int rev = 0;
		boolean passed = true;
		while (count < 11) {
			intarr.addFirst(count);
			count += 1;
		}
		while (count > 0) {
			count -= 1;
			passed = passed && (intarr.removeLast() == rev);
			rev += 1;
		}
		printTestStatus(passed);
	}
	public static void weitest() {
		ArrayDeque<Integer> intarr = new ArrayDeque<>();
		boolean passed = true;
		intarr.addFirst(0);
		passed = passed && (intarr.removeLast() == 0);
		intarr.addLast(2);
		intarr.addFirst(3);
		intarr.addLast(4);
		passed = passed && (intarr.removeLast() == 4);
		intarr.addFirst(6);
		intarr.addFirst(7);
		intarr.addFirst(8);
		intarr.addFirst(9);
		intarr.addFirst(10);
		intarr.addLast(11);
		passed = passed && (intarr.removeFirst() == 10);
		passed = passed && (intarr.removeFirst() == 9);
		passed = passed && (intarr.get(3) == 3);
		intarr.addFirst(15);
		passed = passed && (intarr.removeLast() == 11);
		passed = passed && (intarr.removeFirst() == 15);
		passed = passed && (intarr.removeFirst() == 8);
		passed = passed && (intarr.removeFirst() == 7);
		passed = passed && (intarr.removeFirst() == 6);
		passed = passed && (intarr.removeLast() == 2);
		printTestStatus(passed);
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		gettes();
		addIsEmptySizeTest();
		indextest();
		addRemoveTest();
		addRemovaddtest();
		removeempty();
		arraytest();
		difftypetest();
		firsttest();
		addremove();
		addfornt();
		gettest();
		randomtest();
		addlast();
		lasttest();
		finaltest();
		weitest();

	}

}
