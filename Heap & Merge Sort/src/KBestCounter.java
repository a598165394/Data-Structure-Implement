import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.List;

public class KBestCounter<T extends Comparable<T>> {
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

    PriorityQueue<T> heap;
   private int len =0;
    public KBestCounter(int k) {
    	heap = new PriorityQueue<>(new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				if(o1.compareTo(o2)>0){
					return -1;
				}else if(o1.compareTo(o2)==0){
					return 0;
				}else{
					return 1;
				}
				
			}
    		
		});
    	len =k;
        return;        
    }

    public void count(T x) {
    	heap.add(x);
        return;
    }

    public List<T> kbest() {
    	
    	int i =0;
    	ArrayList<T> list = new ArrayList<T>();
    	while(heap.iterator().hasNext() && i<len){
    		++i;
    		T temp = heap.poll();
    		list.add(temp);
    	}
    	for(int j=0;j<list.size();j++){
    		this.count(list.get(j));
    	}
    	
        return list; 
    } 

	public static void main(String[] argv){
		int k = 5;
	    List<Integer> numbers = Arrays.asList(new Integer[] { 1, 2, 3 });
	    KBestCounter<Integer> counter = new KBestCounter<>(k);


	    try {
	      List<Integer> kbestStudent = counter.kbest();
	      List<Integer> kbestGold = Arrays.asList(new Integer[] { 3, 2, 1 });
	      if (compareCollections(kbestStudent, kbestGold)) {
	    
	      } else {
	       System.out.println(
	            "List incorrect\nExpected: " + kbestGold + "\nActual: " + kbestStudent);
	      }
	    } catch (Exception e) {
	      System.out.println("Line 85+Exception");
	    }

	    try {
	      for (int i = 4; i < 100; i++) {
	        counter.count(i);
	      }
	     
	    
	    } catch (Exception e) {
	      System.out.println("Line 94+Exception");
	    }

	   
	    try {
	      List<Integer> kbestStudent = counter.kbest();
	      List<Integer> kbestGold = Arrays.asList(new Integer[] { 99, 98, 97, 96, 95 });
	      if (compareCollections(kbestStudent, kbestGold)) {
	        
	      } else {
	        System.out.println(
	            "List incorrect\nExpected: " + kbestGold + "\nActual: " + kbestStudent);
	      }
	    } catch (Exception e) {
	      System.out.println("Line 108+Exception");
	    }

	    try {
	      counter.count(101);
	      counter.count(102);
	      counter.count(100);
	      List<Integer> kbestStudent = counter.kbest();
	      List<Integer> kbestGold = Arrays.asList(new Integer[] { 102, 101, 100, 99, 98 });
	      if (compareCollections(kbestStudent, kbestGold)) {
	       
	      } else {
	       System.out.println(
	            "List incorrect\nExpected: " + kbestGold + "\nActual: " + kbestStudent);
	      }
	    } catch (Exception e) {
	      System.out.println("Line 123+Exception");
	    }
    }
	
}

