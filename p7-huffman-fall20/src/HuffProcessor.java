import java.util.*;
/**
 * Although this class has a history of several years,
 * it is starting from a blank-slate, new and clean implementation
 * as of Fall 2018.
 * <P>
 * Changes include relying solely on a tree for header information
 * and including debug and bits read/written information
 * 
 * @author Owen Astrachan
 */

public class HuffProcessor {

	public static final int BITS_PER_WORD = 8;
	public static final int BITS_PER_INT = 32;
	public static final int ALPH_SIZE = (1 << BITS_PER_WORD);
	public static final int PSEUDO_EOF = ALPH_SIZE;
	public static final int HUFF_NUMBER = 0xface8200;
	public static final int HUFF_TREE = HUFF_NUMBER | 1;

	private final int myDebugLevel;

	public static final int DEBUG_HIGH = 4;
	public static final int DEBUG_LOW = 1;

	public HuffProcessor() {
		this(0);
	}

	public HuffProcessor(int debug) {
		myDebugLevel = debug;
	}

	/**
	 * Compresses a file. Process must be reversible and loss-less.
	 *
	 * @param in  Buffered bit stream of the file to be compressed.
	 * @param out Buffered bit stream writing to the output file.
	 */
	public void compress(BitInputStream in, BitOutputStream out) {

		// remove all this code when implementing compress
		/*while (true){
			int val = in.readBits(BITS_PER_WORD);
			if (val == -1) break;
			out.writeBits(BITS_PER_WORD, val);
		}
		out.close(); */

		int[] counts = readForCounts(in);
		HuffNode root = makeTree(counts);
		String[] encode = makeCodings(root);
		out.writeBits(BITS_PER_INT, HUFF_TREE);
		header(root, out);
		in.reset();
		CompressedBits(encode, in, out);
		out.close();
	}
	private int[] readForCounts(BitInputStream in) {
		int[] count = new int[ALPH_SIZE + 1];
		count[PSEUDO_EOF] = 1;
		int bit;
		while (true) {
			bit = in.readBits(BITS_PER_WORD);
			if (bit == -1) {
				break;
			} else {
				count[bit] += 1;
			}
		}
		return count;
	}
	private String[] makeCodings(HuffNode tree) {
		String[] encode = new String[ALPH_SIZE+1];
		helper(tree, "", encode);
		return encode;
	}
	private HuffNode makeTree(int[] a) {
		PriorityQueue<HuffNode> pq = new PriorityQueue<>();
		for (int i=0; i < a.length; i++) {
			if (a[i] > 0) {
				pq.add(new HuffNode(i, a[i], null, null));
			}
		}
		while (pq.size() > 1) {
			HuffNode left = pq.remove();
			HuffNode right = pq.remove();
			HuffNode h = new HuffNode(0,(left.myWeight + right.myWeight),left,right);
			pq.add(h);
		}
		HuffNode root = pq.remove();
		return root;
	}
	private void helper(HuffNode node, String s, String[] arr) {
		if (node.myLeft==null && node.myRight==null) {
			arr[node.myValue] = s;
		} else {
			String s_left = s + '0';
			String s_right = s + '1';
			helper(node.myLeft, (s + '0'), arr);
			helper(node.myRight, (s + '1'), arr);
		}
	}
	private void header(HuffNode node, BitOutputStream out) {
		if (node.myLeft == null && node.myRight == null) {
			out.writeBits(1, 1);
			out.writeBits(BITS_PER_WORD + 1, node.myValue);
			return;
		} else {
			out.writeBits(1, 0);
		}
		header(node.myLeft, out);
		header(node.myRight, out);
	}
	private void CompressedBits(String[] arr, BitInputStream in, BitOutputStream out) {
		while (true) {
			int b = in.readBits(BITS_PER_WORD);
			if (b == -1) {
				String cd = arr[PSEUDO_EOF];
				out.writeBits(cd.length(), Integer.parseInt(cd, 2));
				break;
			}
			String qtr = arr[b];
			out.writeBits(qtr.length(), Integer.parseInt(qtr, 2));
		}
	}

	/**
	 * Decompresses a file. Output file must be identical bit-by-bit to the
	 * original.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be decompressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void decompress(BitInputStream in, BitOutputStream out) {

		int magic = in.readBits(BITS_PER_INT);
		if (magic != HUFF_TREE) {
			throw new HuffException("invalid magic number " + magic);
		}
		// remove all code below this point for P7

		/*out.writeBits(BITS_PER_INT, magic);
		while (true) {
			int val = in.readBits(BITS_PER_WORD);
			if (val == -1) break;
			out.writeBits(BITS_PER_WORD, val);
		}
		out.close();*/
		HuffNode root = readTree(in);
		HuffNode current = root;
		while (true) {
			int bits = in.readBits(1);
			if (bits==-1) {
				throw new HuffException("bad input, no PSEUDO_EOF");
			} else {
				if (bits==0) {
					current = current.myLeft;
				} else {
					current = current.myRight;
				}
				if (current.myLeft==null && current.myRight==null) {
					if (current.myValue==PSEUDO_EOF) {
						break;
					} else {
						out.writeBits(BITS_PER_WORD, current.myValue);
						current = root;
					}
				}
			}
		}
		out.close();
	}

	/**
	 * Helper method to read the tree
	 * @param in
	 * @return HuffNode
	 */
	private HuffNode readTree(BitInputStream in) {
		int bit = in.readBits(1);
		if (bit==-1) {
			throw new HuffException("bad input, no PSEUDO_EOF");
		}
		if (bit==0) {
			HuffNode left = readTree(in);
			HuffNode right = readTree(in);
			return new HuffNode(0,0,left,right);
		}
		else {
			int value = in.readBits(BITS_PER_WORD+1);
			return new HuffNode(value,0,null,null);
		}
	}
}