/**
 * LinkStrand implementation of IDnaStrand
 * @author Arman Shekarriz and Jack Nastas
 */
public class LinkStrand implements IDnaStrand {

    /**
     * Private helper class within larger public class to define nodes.
     * Contains information to define Nodes objects in LinkStrand
     */
    private class Node {
        String info;
        Node next;

        public Node(String s) {
            info = s;
            next = null;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private int myLocalIndex;
    private Node myCurrent;

    /**
     * Create LinkStrands with a single Node from strand
     * @param strand is first node
     */
    public LinkStrand(String strand) {
        initialize(strand);
    }

    /**
     * Default constructor of LinkStrand that creates LinkStrand with
     * value of an empty String
     */
    public LinkStrand() {
        this("");
    }

    /**
     * Override method to return total number of characters
     * @return the number of characters
     */
    @Override
    public long size() {
        return mySize;
    }

    /**
     * Override the method that initializes private variables
     * @param source turns into the first node
     */
    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = source.length();
        myAppends = 0;
        myIndex = 0;
        myLocalIndex = 0;
        myCurrent = myFirst;
    }

    /**
     * Override getInstance method for LinkStrand
     * @param source is data from which object constructed
     * @return IDnaStrand that is created from source
     */
    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    /**
     * Override append method, adds a node to a string
     * @param dna is the string appended to this strand
     * @return changed IDnaStrand with new added Node
     */
    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna);
        myLast = myLast.next;
        mySize = mySize + dna.length();
        myAppends++;
        return this;
    }

    /**
     * toString method for nodes implementing StringBuilder object
     * @return LinkStrand object as String representation
     */
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        Node first = myFirst;
        while (first != null) {
            ans.append(first.info);
            first = first.next;
        }
        return ans.toString();
    }

    /**
     * Override reverse method
     * Reverses current LinkStrand object by creating a new one
     * @return IDnaStrand object containing reverse LinkStrand object
     */
    @Override
    public IDnaStrand reverse() {
        Node f = myFirst;
        LinkStrand l = new LinkStrand();
        while (f != null) {
            Node f_new = new Node(revhelper(f.info));
            f_new.next = l.myFirst;
            l.myFirst = f_new;
            l.myAppends++;
            f = f.next;
            l.mySize = mySize;
        }
        return l;
    }

    /**
     * Private helper method to reverse String stored in a given Node
     * @param s is String object to be reversed
     * @return reversed String object of s
     */
    private String revhelper(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb = sb.reverse();
        return sb.toString();
    }

    /**
     * Override method that returns number of times nodes have been appended
     * @return number of appends
     */
    @Override
    public int getAppendCount() {
        return myAppends;
    }

    /**
     * Override charAt method returning a specific characters
     * Efficient O(1) version of inefficient charAt method
     * @param index specifies which character will be returned
     * @return character object containing char value at index location
     */
    @Override
    public char charAt(int index) {
        if (index >= mySize || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (myIndex >= index) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }
        while (myIndex != index) {
            myIndex++;
            myLocalIndex++;
            if (myLocalIndex >= myCurrent.info.length()) {
                myLocalIndex = 0;
                myCurrent = myCurrent.next;
            }
        }
        return myCurrent.info.charAt(myLocalIndex);
    }
}
