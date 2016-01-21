import java.math.BigInteger;
import java.util.Arrays;

public class SuperSoda {
// The running time for minimalSodaCost is O(costs.length*N)
  public static double minimalSodaCost(int[] sodaSizes, double[] costs, int n) {
	  double[] number = new double[n+1];
	  number[0] = 0;
	  number[1] = 0.8;
	  for(int i=1;i<n+1;i++){
		  double[] record = new double[sodaSizes.length];
		  for(int j=0;j<sodaSizes.length;j++){
			  if(i-sodaSizes[j]>=0){
				  record[j] = number[i-sodaSizes[j]]+costs[j];
			  }else{
				  record[j] = Double.MAX_VALUE;
			  }
		  }
		  double optimize = Double.MAX_VALUE;
		  for(double recordVal:record){
			  if(optimize>recordVal){
				  optimize = recordVal;
			  }
		  }
		  number[i] = optimize;
	//	  System.out.println(Arrays.toString(number));
	  }
    return number[n]; 
  }
// The running time for maximumSodaNumber is O(10*costs.length*N)
  public static int maximumSodaNumber(int[] sodaSizes, double[] costs, double cost) {
	  int money = (int) (cost*10);
	  cost = money/10.0;
	  int size = (int) (cost/0.1);
	  int[] number = new int[size+1];
	  for(int i=0;i<=6;i++){
		  number[i] = 0;
	  }
	  number[7] = 1;
	  for(int i=8;i<size+1;i++){
		  int[] record = new int[costs.length];
		  for(int j=0;j<costs.length;j++){
			  if(i*0.1-costs[j]>=0){
				  record[j] = number[i-(int)(costs[j]/0.1)]+sodaSizes[j];
			  }else{
				  record[j] = Integer.MAX_VALUE;
			  }
		  }
		  int optimize = Integer.MIN_VALUE;
		  for(int goods:record){
			  if(goods>optimize){
				  optimize = goods;
			  }
		  }
		  number[i] = optimize;
		
	  }
	
	
			  
    return number[size];
  }

  public static int[] minimalSodaCostCombinations(int[] sodaSizes, double[] costs, int n) {
	  int[] result = new int[sodaSizes.length];
	  double[] number = new double[n+1];
	  double[][] cache = new double[n+1][sodaSizes.length];
	  number[0] = 0;
	  number[1] = 0.8;
	  for(int i=1;i<n+1;i++){
		  double[] record = new double[sodaSizes.length];
		  double[][] tempCache = new double[sodaSizes.length][sodaSizes.length];
		  for(int j=0;j<sodaSizes.length;j++){
			  if(i-sodaSizes[j]>=0){
				  record[j] = number[i-sodaSizes[j]]+costs[j];
				  for(int z=0;z<sodaSizes.length;z++){
					  tempCache[j][z] = cache[i-sodaSizes[j]][z];
				  }
				  tempCache[j][j]=tempCache[j][j]+1;
			  }else{
				  record[j] = Double.MAX_VALUE;
			  }
		  }
		  double optimize = Double.MAX_VALUE;
		  for(int k=0;k<sodaSizes.length;k++){
			  if(optimize>record[k]){
				  optimize = record[k];
				  cache[i]=tempCache[k];
			  }
		  }
		  number[i] = optimize;	  
		  for(int k=0;k<sodaSizes.length;k++){
			  result[k] = (int) cache[n][k];
		  }
	  }
    return result; 
  }

  public static void main(String[] args) {
    int[] sodaSizes = new int[] { 1, 6, 12, 25, 36 };
    double[] costs = new double[] { 0.8, 4, 7.5, 14, 20 };
    System.out.println(minimalSodaCost(sodaSizes, costs, 105));
    System.out.println(maximumSodaNumber(sodaSizes, costs,100));
    System.out.println(Arrays.toString(minimalSodaCostCombinations(sodaSizes, costs, 105)));
  }
}
