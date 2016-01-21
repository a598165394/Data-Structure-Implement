import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BwogBot {

private	 SeparateChainingMap<String,Integer> content ;
public BwogBot() {
	 content = new SeparateChainingMap<String,Integer>();
}

/*
 * If we have a very big table size of map, the separate chaining map will be much more efficiency. It will be always average O(1) for put and get, if we get the load factor small than 1
 * but rehash may take a little time. And the separate chaining map will have much more higher setup cost. Because we need initialize
 * a big table, and every table(index) has its own linkedlist . The put and get for avl tree map is O(log N), which is much more slower than the separate chaining map.
 * 
 */

@SuppressWarnings("deprecation")
public void readFile(String fileName) throws IOException {
	  
		@SuppressWarnings("resource")
		DataInputStream fileStream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));
		String line;

		while((line = fileStream.readLine())!=null){
			String[] word = line.trim().split(" ");
			for(int i=0;i<word.length;i++){
				if(content.get(word[i])==null){
					content.put(word[i], 1);
				}else{
					content.put(word[i], content.get(word[i])+1);
				
				}
			}
		}
			
}

public int getCount(String word) {
	
	  if(content.get(word.trim())!=null){
		  return content.get(word.trim());
	  }
	  
  return 0;
}

/*For the top 100 most commons words mostly are just plain English, it is not helpful to tell us anything bwog. According to this, only care about frequency
 * is not a good way to analyze the text. Firstly, we should ignore the word like "a" or "an" , it is not very helpful to determine the text. Secondly, we could based on the sentence structure to analyze the text, things will becomes helpful.
 * In addition, we could find stem of the word. Such as walking, walked and walk we could treat them all as walk. In addition, we need separate parts of speech. The verb and noun most of times are much more meaningful. We could separate the word to different group. Such as noun has it's own group, verb has it's own group. If one noun occur
 * a lot of times, for example ,if milk is top one popular word, the text content must have some relative to the milk. So we should be more care about the noun and the verb.
 * 
 * 
 */
public List<String> getNMostPopularWords(int n) {	
	ArrayList<String> result = new ArrayList<String>();
	ArrayList<Integer> resultNum = new ArrayList<Integer>();
	for(int j=0;j<content.getTableSize();j++){
		for(Pair<String,Integer> temp:content.list[j]){
			if(result.contains(temp.key)||temp.key.equals("")||temp.key.equals(" ")||temp.key==null){
				continue;
			}
			if(result.size()<n){
				result.add(temp.key);
				resultNum.add(temp.value);
				resort(resultNum,result);
			}else{
				if(temp.value>resultNum.get(0)){
					resultNum.remove(0);
					result.remove(0);
					result.add(0,temp.key);
					resultNum.add(0,temp.value);
					resort(resultNum,result);
				}
			}
		}
	}
	for(int i=0;i<result.size()/2;i++){
		String temp = result.get(i);
		result.set(i, result.get(result.size()-i-1));
		result.set(result.size()-1-i, temp);
	}
	return result;

}

private void resort(ArrayList<Integer> numberList, ArrayList<String> wordList) {
	  
	ArrayList<Integer> oldlist = new ArrayList<>();
	
	for(int i=0;i<numberList.size();i++){
		oldlist.add(numberList.get(i));
	}
	String tempword;
	int tempNum;
	
	Collections.sort(numberList);
	if(oldlist.equals(numberList)==false){
		for(int i=0;i<oldlist.size();i++){
			if(oldlist.get(i)!=numberList.get(i)){
				for(int j=i;j<oldlist.size();j++){
					if(oldlist.get(i) ==numberList.get(j)){
						tempword = wordList.get(i);
						wordList.set(i, wordList.get(j));
						wordList.set(j, tempword);
						tempNum = oldlist.get(i);
						oldlist.set(i, oldlist.get(j));
						oldlist.set(j, tempNum);
						i=i-1;
						break;
					}
				}
			}
		}
	}

	return ;
}

public Map<String, Integer> getMap() {
  return content ;
}
public static void main(String[] args) throws IOException {
	  BwogBot bot = new BwogBot();
	  
	
	    try {
	    	Calendar cal = Calendar.getInstance();
	    	long start =cal.getTimeInMillis();
	    	
	      bot.readFile("comments.txt");
	      Calendar cald = Calendar.getInstance();
	    	long end =cald.getTimeInMillis();
	    	System.out.println(end-start);
	      System.out.println("Pass");
	      System.out.println(bot.getCount("hamdel"));
	    } catch (Exception e) {
	    	System.out.println("Failed");
	    }

	    try {
	      Map<String, Integer> map = bot.getMap();
	      if (map instanceof AvlMap<?, ?>) {
	       System.out.println("Pass");
	      } else if (map instanceof SeparateChainingMap<?, ?>) {
	       System.out.println("Pass");
	      } else {
	      System.out.println( "Failed:get Map");
	      }
	    } catch (Exception e) {
	      System.out.println("Failed: get Map");
	    }
	    
	    try {
	        List<String> mostPopular = Arrays.asList(new String[] { "hodor", "the", "to", "a", "is" });
	    	Calendar cal = Calendar.getInstance();
	    	long start =cal.getTimeInMillis();
	        List<String> botMostPopular = bot.getNMostPopularWords(5);
	        List<String> test = bot.getNMostPopularWords(10);
	        for(String s:test){
	        	System.out.println(s);
	        }
	        
	        
	        Calendar cald = Calendar.getInstance();
	    	long end =cald.getTimeInMillis();
	    	System.out.println(end-start);
	        if (compareCollections(mostPopular, botMostPopular)) {
	          System.out.println("Successful");
	        } else {
	         System.out.println("getNMostPopularWords() did not return correct top n words");
	        }
	      } catch (Exception e) {
	       System.out.println("Exception throw");
	      }
}

private static <T> boolean compareCollections(Collection<T> l1, Collection<T> l2) {
    if (l1.size() != l2.size()) {
      return false;
    }
    T i2;
    Iterator<T> l2iter = l2.iterator();
    for (T i1 : l1) {
      i2 = l2iter.next();
      if (!(i2.equals(i1))) {
        return false;
      }
    }
    return true;
  }
  }


