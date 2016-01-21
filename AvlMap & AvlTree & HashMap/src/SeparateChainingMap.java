import java.util.LinkedList;

public class SeparateChainingMap<K extends Comparable<? super K>, V> implements Map<K, V> {

  public static final int SCALE_FACTOR = 2;
  public static final int INITIAL_TABLE_SIZE = 8;
  public static final double MAX_LOAD_FACTOR = 1.0;

  @SuppressWarnings("unchecked")
public LinkedList<Pair<K,V>>[] list;
public int currentsize=0;
  public SeparateChainingMap() {
	list =(LinkedList<Pair<K,V>>[]) new LinkedList[INITIAL_TABLE_SIZE];
	  for(int i=0;i<list.length;i++){
		  list[i] = new LinkedList<Pair<K,V>>();
	  }
  }

  public int getSize() {

       return currentsize;
  }

  public int getTableSize() {
    return list.length;
  }

  @SuppressWarnings("unchecked")
public void put(K key, V value) {
	  upsize();
	  Pair<K,V> pair = new Pair<K,V>(key,value);
	  int hashValue =key.hashCode()%(this.getTableSize()-1);
	  if(hashValue<0){
		  hashValue +=this.getTableSize();
	  }
	  for(Pair<K,V> temp: list[hashValue]){
		  if(temp.key.equals(key)){
			  temp.value =value;
			  return;
		  }
	  }
	list[hashValue].add(pair);
	currentsize+=1;
  }
  


  public V get(K key) {
	  int hashValue = key.hashCode()%(this.getTableSize()-1);
	  if(hashValue<0){
		  hashValue+=this.getTableSize();
	  }
	  for(Pair<K,V> pair: list[hashValue]){
		  if(pair.key.equals(key)){
			  return pair.value;
		  }
	  }

    return null;
    
  }

  @SuppressWarnings("unchecked")
  public void upsize() {
		if((currentsize/(double) this.getTableSize()) > MAX_LOAD_FACTOR){
			
			LinkedList<Pair<K,V>>[] oldList = list;
			
			list = (LinkedList<Pair<K,V>>[])new LinkedList[list.length*2];
			for(int i=0;i<list.length;i++){
				list[i] = new LinkedList<Pair<K,V>>();
			}
			currentsize=0;
			for(int i=0;i<oldList.length;i++){
				for(Pair<K,V> pair:oldList[i]){
					this.put(pair.key, pair.value);
					
				}
			}
		}
  }


}
