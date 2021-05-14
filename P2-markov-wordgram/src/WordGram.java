import java.util.*;
/**
 * A WordGram represents a sequence of strings
 * just as a String represents a sequence of characters
 * 
 * @author Arman Shekarriz
 *
 */
public class WordGram {
	
	private String[] myWords;   
	private String myToString;  // cached string
	private int myHash;         // cached hash value

	/**
	 * Create WordGram by creating instance variable myWords and copying
	 * size strings from source starting at index start
	 * @param source is array of strings from which copying occurs
	 * @param start starting index in source for strings to be copied
	 * @param size the number of strings copied
	 */
	public WordGram(String[] source, int start, int size) {
		myWords = new String[size];
		myToString = null;
		myHash = 0;
		for(int i=0; i<size; i++){
			myWords[i] = source[i+start];
		}
		myToString = this.toString();
		myHash = this.hashCode();
	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string 
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * Return length of the array that stores string of the WordGram.
	 * @return
	 */
	public int length(){
		return myWords.length;
	}


	/**
	 * Check is object can be expressed as a WordGram.
	 * If it is, see if length of array is same as length of first WordGram.
	 * Concatenate array elements to compare strings to see if objects are the same.
	 * @param o
	 * @return boolean
	 */
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof WordGram) || o == null) {
			return false;
		}
		WordGram w = (WordGram) o;
		int wlen = w.length();
		int len = this.length();
		if (len!=wlen)
			return false;
		else
				if (this.toString().equals(w.toString()))
					return true;
				else
					return false;
		}

	@Override
	public int hashCode(){
		if(myHash == 0){
			myHash = myToString.hashCode();
		}
		return myHash;
	}
	

	/**
	 * Array of WordGram is copies to new WordGram, with no strings.
	 * myToString and myHas automatically computed by setting values to new WordGram.
	 * @param last is last String of returned WordGram
	 * @return new WordGram object with additional string
	 */
	public WordGram shiftAdd(String last) {
		WordGram wg = new WordGram(myWords,0,myWords.length);
		ArrayList<String> a = new ArrayList<>(Arrays.asList(wg.myWords));
		a.remove(wg.myWords[0]);
		a.add(last);
		wg.myWords = new String[a.size()];
		wg.myWords = a.toArray(wg.myWords);
		WordGram ans = new WordGram(wg.myWords, 0, wg.myWords.length);

		return ans;
	}

	@Override
	public String toString(){
		if (myToString == null){
			myToString = String.join(" ", myWords);
		}
		return myToString;
	}
}
