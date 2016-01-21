
public class Pair<Double extends Comparable<? super Double> , String> implements Comparable<Pair<Double,String>> {
	public Double cost;
	public String name;
	public Pair(Double cost, String name) {
		this.name = name;
		this.cost = cost;
	}
	@Override
	public int compareTo(Pair<Double, String> other) {
		return cost.compareTo(other.cost);
	}

}
