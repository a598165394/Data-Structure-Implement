import java.util.*;

public class CountOut {

<<<<<<< HEAD
  public static List<Integer> play(int n, int k) {	  
=======
  public static List<Integer> play(int n, int k) 
  {
>>>>>>> a6acd542ffdd1cc32e39b9bafa47e76888eaf046
    Queue<Integer> remainingPlayers = new LinkedList<>();
    Queue<Integer> cycleLoop = new LinkedList<Integer>();  
    for(int i=1; i<n+1; i++)  
        cycleLoop.offer(i);  

    for(int i=1; cycleLoop.size()>=1; i++)  
    {  
        if(i%k == 0){
        	int temp = cycleLoop.poll();
        	 if(temp<n){
           	  temp =temp -1;
             }else{
           	  temp = n-1;
             }
            remainingPlayers.offer(temp);
        }
        else{
            cycleLoop.offer(cycleLoop.poll());  
        }
    }  

<<<<<<< HEAD

	return  (List<Integer>) remainingPlayers;

    
 
=======
    // Your code here.
    List<Integer> outPlayers = new ArrayList<>();
    for(int i = 0; i < n; i++) {
        remainingPlayers.add(i);
    }
    
    while (remainingPlayers.size() > 1) 
    {
        for (int i = 0; i < (k-1); i++) 
        {
            remainingPlayers.add(remainingPlayers.remove());;
        }
        outPlayers.add(remainingPlayers.remove());
    }
    outPlayers.add(remainingPlayers.remove()); // winner is last "out"
    return outPlayers;
>>>>>>> a6acd542ffdd1cc32e39b9bafa47e76888eaf046
  }

  public static Integer findWinner(int n, int k) {
	  Queue<Integer> loop = new LinkedList<Integer>();  
      for(int i=1; i<n+1; i++)  
          loop.offer(i);  
 
      for(int i=1; loop.size()>1; i++)  
      {  
          if(i%k == 0){
              loop.poll();
          }
          else{
              loop.offer(loop.poll());  
          }
      }  
      int temp = loop.poll();
      if(temp<n){
    	  temp =temp -1;
      }else{
    	  temp = n-1;
      }

      return temp;
    // Your code here.
    return play(n,k).get(n-1);
  }

  public static Integer findWinnerRec(int n, int k) {
<<<<<<< HEAD
	  
	return looFunction(n,k,n);
	

=======
      if (n == 0) {
          return 0;
      }
      return (findWinnerRec(n-1, k) + k) % n;
      
  }
  
  public static void main (String[] args) {
      System.out.println(play(9, 4));
      System.out.println(findWinner(9, 4));
      System.out.println(findWinnerRec(9, 4));
>>>>>>> a6acd542ffdd1cc32e39b9bafa47e76888eaf046
  }
  public static void main(String[] args) { 
	
	  play(9, 4);
	  findWinner(10,1);
  }
  

// The running time for this is O(N)
  private static Integer looFunction(int n, int k, int round) {
	  if(round==1){
		  int temp = (n+k-1)%n ;
		  return temp;
	  }else{
		  int temp =(looFunction(n-1,k,round-1)+k)%n;
		  return temp ;
	  }
		
	
  }
}
  	

