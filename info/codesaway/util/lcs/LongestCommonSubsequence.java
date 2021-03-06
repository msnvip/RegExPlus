package info.codesaway.util.lcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

/**
 * <p>A class to compute the longest common subsequence in two strings.</p>
 * 
 * <p>
 * <b>Note</b>: in some locations, <i>X</i> is used to refer to the "before"
 * value and <i>Y</i> is used to refer to the "after" value.
 * </p>
 * 
 * <p>Algorithms from Wikipedia:<br>
 * <a
 * href="http://en.wikipedia.org/wiki/Longest_common_subsequence_problem">http
 * ://en.wikipedia.org/wiki/Longest_common_subsequence_problem</a>
 * </p>
 * 
 * @author jhess
 * @param <E>
 *            the element type
 * @see <a
 *      href="http://en.wikibooks.org/wiki/Algorithm_implementation/Strings/Longest_common_subsequence#Java">Source</a>
 */
public abstract class LongestCommonSubsequence<E>
{
	/**
	 * The LCS table.
	 * 
	 * The <i>i</i>th row and <i>j</i>th column shows the length of the LCS
	 * between X<sub>1..<i>i</i></sub> and Y<sub>1..<i>j</i></sub>.
	 */
	private int[][] lcsTable;

	/**
	 * Cached list, as returned by {@link #diff()}.
	 */
	private List<DiffEntry<E>> diff;

	/**
	 * Cached list, as returned by {@link #diff0}
	 */
	private List<DiffEntry<E>> diff0;

	/**
	 * Cached list, as returned by {@link #backtrack()}
	 */
//	private List<E> backtrack;

	/**
	 * Sole constructor. (For invocation by subclass constructors, typically
	 * implicit.)
	 */
	protected LongestCommonSubsequence()
	{
	}

	/**
	 * Returns the number of elements in the "before" value
	 * 
	 * @return number of elements in the "before" value
	 */
	protected abstract int lengthOfBefore();

	/**
	 * Returns the number of elements in the "after" value
	 * 
	 * @return number of elements in the "after" value
	 */
	protected abstract int lengthOfAfter();

	/**
	 * Returns the element at the specified 0-based index in the "before" value
	 * 
	 * @param index
	 *            the 0-based index of the element to return
	 * @return the element at the specified index
	 */
	protected abstract E valueOfBefore(int index);

	/**
	 * Returns the element at the specified 0-based index in the "after" value
	 * 
	 * @param index
	 *            the 0-based index of the element to return
	 * @return the element at the specified index
	 */
	protected abstract E valueOfAfter(int index);

	/**
	 * Indicates whether the two specified objects are "equal".
	 * 
	 * @param x1
	 *            the first object to compare
	 * @param y1
	 *            the second object to compare
	 * @return <code>true</code> if the two specified elements are equal;
	 *         <code>false</code> otherwise.
	 */
	protected boolean equals(E x1, E y1)
	{
		return (x1 == null ? y1 == null : x1.equals(y1));
	}

	/**
	 * Indicates whether the <i>i</i>th element of <i>X</i> is equal to the
	 * <i>j</i>th element of <i>Y</i>.
	 * 
	 * @param i
	 *            the 1-based index of the element in <i>X</i>
	 * @param j
	 *            the 1-based index of the element in <i>Y</i>
	 * @return <code>true</code> if the two specified elements are equal;
	 *         <code>false</code> otherwise.
	 */
	private boolean isXYEqual(int i, int j)
	{
		return equals(valueOfXInternal(i), valueOfYInternal(j));
	}

	/**
	 * Returns the element at the specified 1-based index in the "before" value
	 * 
	 * @param i
	 *            the 1-based index of the element to return
	 * @return the element at the specified index
	 */
	private E valueOfXInternal(int i)
	{
		return valueOfBefore(i - 1);
	}

	/**
	 * Returns the element at the specified 1-based index in the "before" value
	 * 
	 * @param j
	 *            the 1-based index of the element to return
	 * @return the element at the specified index
	 */
	private E valueOfYInternal(int j)
	{
		return valueOfAfter(j - 1);
	}

	/**
	 * Creates the LCS table used by the other functions.
	 */
	public void calculateLcs()
	{
		if (lcsTable != null) {
			return;
		}
		lcsTable = new int[lengthOfBefore() + 1][];
		for (int i = 0; i < lcsTable.length; i++) {
			lcsTable[i] = new int[lengthOfAfter() + 1];
		}

		for (int i = 1; i < lcsTable.length; i++) {
			for (int j = 1; j < lcsTable[i].length; j++) {
				if (isXYEqual(i, j)) {
					lcsTable[i][j] = lcsTable[i - 1][j - 1] + 1;
				} else {
					lcsTable[i][j] = max(lcsTable[i][j - 1], lcsTable[i - 1][j]);
				}
			}
		}
	}

	/**
	 * Returns the length of the longest common subsequence.
	 * 
	 * @return the length of the longest common subsequence
	 */
//	public int lcsLength()
//	{
//		return getLcs(lengthOfBefore(), lengthOfAfter());
//	}

	/**
	 * Returns the length of the longest common subsequence between
	 * X<sub>1..i</sub> and Y<sub>1..j</sub>.
	 * 
	 * @param i
	 *            1-based position in the "before" value
	 * @param j
	 *            1-based position in the "after" value
	 * @return the length of the longest common subsequence between
	 *         X<sub>1..i</sub> and Y<sub>1..j</sub>
	 */
//	public int getLcs(int i, int j)
//	{
//		calculateLcs();
//		return lcsTable[i][j];
//	}

	/**
	 * Returns the <a
	 * href="http://en.wikipedia.org/wiki/Levenshtein_distance">Levenshtein
	 * distance</a> when only insertion and deletion is allowed (no
	 * substitution).
	 * 
	 * @return the minimum number of operations needed to transform one string
	 *         into the other, where an operation is an insertion or deletion of
	 *         a single character
	 */
//	public int editDistance()
//	{
//		calculateLcs();
//		return lengthOfBefore() + lengthOfAfter() - 2 * lcsLength();
//	}

	/**
	 * Backtracks the choices taken when computing the LCS table.
	 * 
	 * @return the elements in the longest common subsequence
	 */
//	public List<E> backtrack()
//	{
//		calculateLcs();
//		if (this.backtrack == null) {
//			this.backtrack = new ArrayList<E>();
//			backtrack(lengthOfBefore(), lengthOfAfter());
//			// backtrack(1, 1);
//		}
//		return this.backtrack;
//	}

	/**
	 * Backtracks the choices taken when computing the LCS table.
	 * 
	 * @param i
	 *            the 1-based position in X
	 * @param j
	 *            the 1-based position in Y
	 */
//	private void backtrack(int i, int j)
//	{
//		if (i == 0 || j == 0) {
//			return;
//		} else if (isXYEqual(i, j)) {
//			backtrack(i - 1, j - 1);
//			backtrack.add(valueOfXInternal(i));
//		} else if (lcsTable[i][j - 1] > lcsTable[i - 1][j]) {
//			backtrack(i, j - 1);
//		} else {
//			backtrack(i - 1, j);
//		}
//	}

	/**
	 * add javadoc comments
	 * 
	 * @return a list of the differences
	 */
	public List<DiffEntry<E>> diff()
	{
		calculateLcs();

		if (this.diff == null) {
			this.diff = new ArrayList<DiffEntry<E>>();
			diff(lengthOfBefore(), lengthOfAfter());
		}
		return this.diff;
	}

	/**
	 * add javadoc comments
	 * 
	 * @param i
	 *            the 1-based position in X
	 * @param j
	 *            the 1-based position in Y
	 */
	private void diff(int i, int j)
	{
		while (!(i == 0 && j == 0)) {
			if (i > 0 && j > 0 && isXYEqual(i, j)) {
				this.diff.add(new DiffEntry<E>(DiffType.NONE,
						valueOfXInternal(i)));
				i--;
				j--;

			} else if (j > 0
					&& (i == 0 || lcsTable[i][j - 1] >= lcsTable[i - 1][j])) {
				this.diff.add(new DiffEntry<E>(DiffType.ADD,
						valueOfYInternal(j)));
				j--;

			} else if (i > 0
					&& (j == 0 || lcsTable[i][j - 1] < lcsTable[i - 1][j])) {

				this.diff.add(new DiffEntry<E>(DiffType.REMOVE,
						valueOfXInternal(i)));
				i--;
			}

		}

		Collections.reverse(this.diff);
	}

	/**
	 * add javadoc comments
	 * @return a list of the differences
	 */
	public List<DiffEntry<E>> diff0()
	{
		calculateLcs();

		if (this.diff0 == null) {
			this.diff0 = new ArrayList<DiffEntry<E>>();
			diff0(1, 1);
		}
		return this.diff0;
	}

	/**
	 * add javadoc comments
	 * 
	 * @param i
	 *            the 1-based position in X
	 * @param j
	 *            the 1-based position in Y
	 */
	private void diff0(int i, int j)
	{
		while (!(i > lengthOfBefore() && j > lengthOfAfter())) {
			if (i <= lengthOfBefore() && j <= lengthOfAfter()
					&& isXYEqual(i, j)) {
				this.diff0.add(new DiffEntry<E>(DiffType.NONE,
						valueOfXInternal(i)));
				i++;
				j++;

			} else {
				if (j <= lengthOfAfter()
						&& (i > lengthOfBefore() || lcsTable[i][j - 1] >= lcsTable[i - 1][j])) {
					this.diff0.add(new DiffEntry<E>(DiffType.ADD,
							valueOfYInternal(j)));
					j++;

				} else if (i <= lengthOfBefore()
						&& (j > lengthOfAfter() || lcsTable[i][j - 1] < lcsTable[i - 1][j])) {

					this.diff0.add(new DiffEntry<E>(DiffType.REMOVE,
							valueOfXInternal(i)));
					i++;
				}
			}
		}
	}

	/*
	 * @Override
	 * public String toString()
	 * {
	 * calculateLcs();
	 * 
	 * StringBuffer buf = new StringBuffer();
	 * buf.append("  ");
	 * for (int j = 1; j <= lengthOfY(); j++) {
	 * buf.append(valueOfYInternal(j));
	 * }
	 * buf.append("\n");
	 * buf.append(" ");
	 * for (int j = 0; j < lcsTable[0].length; j++) {
	 * buf.append(Integer.toString(lcsTable[0][j]));
	 * }
	 * buf.append("\n");
	 * for (int i = 1; i < lcsTable.length; i++) {
	 * buf.append(valueOfXInternal(i));
	 * for (int j = 0; j < lcsTable[i].length; j++) {
	 * buf.append(Integer.toString(lcsTable[i][j]));
	 * }
	 * buf.append("\n");
	 * }
	 * return buf.toString();
	 * }
	 */

	/**
	 * Returns the formatted LCS table as a string
	 */
//	@Override
//	public String toString()
//	{
//		// number of digits in string1
//		int digits1 = String.valueOf(lengthOfBefore()).length();
//
//		// number of digits in string2
//		int digits2 = String.valueOf(lengthOfAfter()).length();
//
//		StringBuilder formatB = new StringBuilder();
//		int[] columnNumbers = new int[lcsTable[0].length];
//		String[] dashes = new String[lcsTable[0].length + 2];
//
//		// an array containing the elements of Y
//		Object[] array2 = new Object[lengthOfAfter()];
//
//		for (int j = 0; j < lengthOfAfter(); j++) {
//			array2[j] = valueOfAfter(j);
//		}
//
//		dashes[0] = repeat('-', digits1 + 1);
//		dashes[1] = repeat('-', 2);
//
//		// formatB.append("%1$-").append(digits1 + 1).append("s%2$-2s|");
//		formatB.append("%1$").append(digits1 + 1).append("s%2$2s|");
//
//		for (int j = 0; j < lcsTable[0].length; j++) {
//			// formatB.append('%').append(j + 3).append("$-").append(digits2 +
//			// 1)
//			// .append('s');
//			formatB.append('%').append(j + 3).append("$").append(digits2 + 1)
//					.append(
//					's');
//
//			columnNumbers[j] = j;
//			dashes[j + 2] = repeat('-', digits2 + 1);
//		}
//
//		String format = formatB.append("\n").toString();
//
//		Formatter formatter = new Formatter();
//
//		formatter.format(format, combine("", "", columnNumbers));
//		formatter.format(format, combine("", "", "", array2));
//		formatter.format(format, (Object[]) dashes);
//		formatter.format(format, combine("0", "", lcsTable[0]));
//
//		for (int i = 1; i < lcsTable.length; i++) {
//			formatter.format(format, combine(i, valueOfXInternal(i),
//					lcsTable[i]));
//		}
//
//		return formatter.toString();
//	}

	/**
	 * A difference type, consisting of a symbol to represent the type and
	 * a user-friendly name for it.
	 */
	public static enum DiffType
	{
				//
		/**
		 * <code>DiffType</code> for an addition
		 */
		ADD("+", "add"),

		/**
		 * <code>DiffType</code> for a removal
		 */
		REMOVE("-", "remove"),

		/**
		 * <code>DiffType</code> for no change
		 */
		NONE(" ", "none");

		/**
		 * The symbol to represent the difference type
		 */
		private String symbol;

		/**
		 * User-friendly name for the difference type
		 */
		private String name;

		/**
		 * Enum Constructor
		 * 
		 * @param symbol
		 *            the symbol used to represent the difference type
		 * @param name
		 *            a user-friendly name for the difference type
		 */
		DiffType(String symbol, String name)
		{
			this.symbol = symbol;
			this.name = name;
		}

		/**
		 * Returns the symbol used to represent the difference type.
		 * 
		 * @return the symbol used to represent the difference type
		 */
		@Override
		public String toString()
		{
			return symbol;
		}

		/**
		 * Returns the user-friendly name for the difference type.
		 * 
		 * @return the user-friendly name for the difference type
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * Returns the symbol used to represent the difference type.
		 * 
		 * @return the symbol used to represent the difference type
		 */
		public String getSymbol()
		{
			return symbol;
		}
	}

	/**
	 * A difference entry consisting of a {@link DiffType} and an element.
	 * 
	 * @param <E>
	 *            the element type
	 */
	public static class DiffEntry<E>
	{
		/**
		 * The difference type for this difference entry.
		 */
		private final DiffType type;

		/**
		 * The element for this difference entry.
		 */
		private final E value;

		/**
		 * Constructs a new difference entry
		 * 
		 * @param type
		 *            the difference type for the entry
		 * @param value
		 *            the element for this difference entry
		 */
		public DiffEntry(DiffType type, E value)
		{
			super();
			this.type = type;
			this.value = value;
		}

		/**
		 * Returns the difference type.
		 * 
		 * @return the difference type
		 */
		public DiffType getType()
		{
			return type;
		}

		// public void setType(DiffType type)
		// {
		// this.type = type;
		// }

		/**
		 * Returns the element associated with this difference entry.
		 * 
		 * @return the element associated with this difference entry
		 */
		public E getValue()
		{
			return value;
		}

		// public void setValue(E value)
		// {
		// this.value = value;
		// }

		/**
		 * Returns the entry's type symbol followed by the entry's value as a
		 * string.
		 * 
		 * @returns the string representation of this difference entry
		 */
		@Override
		public String toString()
		{
			return type.toString() + value.toString();
		}
	}
}