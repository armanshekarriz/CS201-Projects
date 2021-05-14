import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;
	
	public EfficientMarkov(){
		this(3);
	}

	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	@Override
	public void setTraining(String text) {
		super.setTraining(text);
		myMap.clear();
		for(int ind=0; ind <=text.length() - myOrder; ind++){
			String key = myText.substring(ind, ind + myOrder);
			ArrayList<String> a = new ArrayList<>();
			String n = "";
			if(ind == text.length() - myOrder)
				n = PSEUDO_EOS;
			else
				n = myText.substring(ind + myOrder, ind + myOrder + 1);
			if(myMap.containsKey(key)){
				a = myMap.get(key);
				a.add(n);
				myMap.put(key, a);
			}
			else{
				a.add(n);
				myMap.put(key, a);
			}
		}
	}
	@Override
	public ArrayList<String> getFollows(String key) {
		if(! myMap.containsKey(key))
			throw new NoSuchElementException(key + " not in map");
		return myMap.get(key);
	}
}	
