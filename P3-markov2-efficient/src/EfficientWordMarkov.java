
import java.util.*;
import java.io.*;
public class EfficientWordMarkov extends BaseWordMarkov {
    private Map<WordGram, ArrayList<String>> myMap;

    public EfficientWordMarkov(){
            this(2);
        }

    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<>();
    }

    @Override
    public void setTraining(String text) {
        super.setTraining(text);
        myMap.clear();
        for(int ind=0; ind <= myWords.length - myOrder; ind++){
            WordGram key = new WordGram(myWords, ind, myOrder);
            ArrayList<String> a = new ArrayList<>();
            String n = "";
            if(ind == myWords.length - myOrder) {
                n = PSEUDO_EOS;
            }
            else {
                n = myWords[ind + myOrder];
            }
            if(! myMap.containsKey(key)) {
                a.add(n);
                myMap.put(key, a);
            }
            else {
                a = myMap.get(key);
                a.add(n);
                myMap.put(key, a);
            }
    }
}
    @Override
    public ArrayList<String> getFollows(WordGram key) {
        if(myMap.containsKey(key)) {
            return myMap.get(key);
        }
        else {
            throw new NoSuchElementException((key + " not in map"));
        }
    }

}
