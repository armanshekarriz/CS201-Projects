import java.util.*;
import java.io.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    /**
     * @param terms is a list of words to form terms from
     * @param weights, a corresponding list of weights, such that terms[i] hasweight[i].
     * @return a HashListAutocomplete whose myTerms object has myTerms[i] = a Term with word terms[i] and weight weights[i].
     * @throws NullPointerException if either argument passed in is null
     */
    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }
        initialize(terms, weights);
    }

    /**
     * Required by the Autocompletor interface. Returns an array containing the
     * k words in myTerms with the largest weight which match the given prefix,
     * in descending weight order. If less than k words exist matching the given
     * prefix (including if no words exist), then the array instead contains all
     * those words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then
     * topKMatches("b", 2) should return {"bell", "bat"}, but topKMatches("a",
     * 2) should return {"air"}
     *
     * @param prefix
     *            - A prefix which all returned words must start with
     * @param k
     *            - The (maximum) number of words to be returned
     * @return An array of the k words with the largest weights among all words
     *         starting with prefix, in descending weight order. If less than k
     *         such words exist, return an array containing all those words If
     *         no such words exist, return an empty array
     * @throws NullPointerException if prefix is null
     */

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (prefix.length() > MAX_PREFIX) {
            prefix = prefix.substring(0, MAX_PREFIX);
        }
        if (!myMap.containsKey(prefix)) {
            return new ArrayList<Term>();
        }
        List<Term> all = myMap.get(prefix);
        List<Term> list = all.subList(0, Math.min(k, all.size()));
        return list;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        mySize = 0;
        myMap = new HashMap<String, List<Term>>();
        HashSet<String> words = new HashSet<>();
        ArrayList<Term> t = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        for (int i=0; i < terms.length; i++) {
            Term k = new Term(terms[i], weights[i]);
            mySize = mySize + BYTES_PER_DOUBLE + (BYTES_PER_CHAR * k.getWord().length());
            words.add(terms[i]);
            int a = k.getWord().length();
            if (a > MAX_PREFIX) {
                a = MAX_PREFIX;
            }
            t.add(k);
            for (int j=0; j <= a; j++) {
                String s = terms[i].substring(0, j);
                if (!set.contains(s)) {
                    set.add(s);
                    List<Term> n = new ArrayList<>();
                    myMap.put(s, n);
                }
                List<Term> e = myMap.get(s);
                e.add(k);
                myMap.put(s, e);
            }
        }
        myMap.put("", t);
        for (String str: myMap.keySet()) {
            mySize = mySize + (str.length() * BYTES_PER_CHAR);
            List<Term> f = myMap.get(str);
            Collections.sort(f, Comparator.comparing(Term::getWeight).reversed());
            myMap.put(str, f);
        }
    }

    @Override
    public int sizeInBytes() {
        return mySize;
    }
}
