public class AvlMap<K extends Comparable<? super K>, V> implements Map<K, V> {

	AvlTree<Pair<K,V>> root = new AvlTree<Pair<K,V>>();

  public AvlMap() {
	  
  }
  @Override
  public void put(K key, V value) {
	  Pair<K,V> t = new Pair<K,V>(key,value);
	  root.insert(t);  
	  return;
  }

@Override
  public V get(K key) {
	Pair<K,V> t = new Pair<K,V>(key,null);
	Pair<K,V> temp =root.get(t);
	return temp == null ? null: temp.value ;
  }
public static void main(String[] argv){
	Map<String,Integer> map = new AvlMap<>();

}

}