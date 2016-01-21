import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.*;



public class MergeSort {
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
        /**
         * Internal method that merges two sorted halves of a subarray (from Weiss Data Structures and Algorithm Analysis in Java)
         * @param a an array of Comparable items.
         * @param tmpArray an array to place the merged result.
         * @param leftPos the left-most index of the subarray.
         * @param rightPos the index of the start of the second half.
         * @param rightEnd the right-most index of the subarray.
         */
        private static void merge(Integer[] a, Integer[] tmpArray, int leftPos, int rightPos, int rightEnd) {
            int leftEnd = rightPos - 1;
            int tmpPos = leftPos;
            int numElements = rightEnd - leftPos + 1;

            // Main loop
            while(leftPos <= leftEnd && rightPos <= rightEnd) {
                if( a[leftPos] <= a[rightPos ]) { 
                    tmpArray[tmpPos++] = a[leftPos++];
                } else {
                    tmpArray[tmpPos++] = a[rightPos++];
                }   
            }  

            while( leftPos <= leftEnd ) {   // Copy rest of first half
                tmpArray[tmpPos++] = a[leftPos++];
            }

            while( rightPos <= rightEnd ) { // Copy rest of right half
                tmpArray[tmpPos++] = a[rightPos++];
            }

            // Copy tmpArray back
            for( int i = 0; i < numElements; i++, rightEnd-- ) {
                a[rightEnd] = tmpArray[rightEnd];
            }
        }

        /**
         * Merge Sort algorithm.
         * This is the Merge Sort algorithm from from Weiss, Data Structures and Algorithm Analysis in Java, 
         * as presented in class. 
         * @param a an array of Comparable items.
         */
        public static void mergeSort(Integer[] a ) {
            Integer[] tmpArray = new Integer[a.length];
            mergeSort(a, tmpArray, 0, a.length - 1 );
        }
        /**
         * Internal method that makes re   cursive calls. 
         * This is part of the MergeSort algorithm from from Weiss, Data Structures and Algorithm Analysis in Java, 
         * as presented in class. 
         * @param a an array of Comparable items.
         * @param tmpArray an array to place the merged result.
         * @param left the left-most index of the subarray.
         * @param right the right-most index of the subarray.
         */
        private static void mergeSort(Integer[] a, Integer[] tmpArray, int left, int right) {
            if(left < right) {
                int center = (left + right) / 2;
                mergeSort(a, tmpArray, left, center);
                mergeSort( a, tmpArray, center + 1, right);
                merge(a, tmpArray, left, center + 1, right);
            }
        }

        
        
       /** 
         * Problem 5: Iterative Bottom-up Merge Sort
         */
        public static void mergeSortB(Integer[] inputArray) {
       
        	
        	if(inputArray.length==1){
        		return;
        	}
        	Integer temp[] = new Integer[inputArray.length];
        	for(int i=1;i<inputArray.length;i*=2){ 
        		for(int j=i;j<inputArray.length;j+=2*i){
        			int start = j-i;
        			int middle = j;
        			int end = start+2*i-1;
        			if(start+2*i-1> inputArray.length-1){
        				end = inputArray.length-1;
        			}
        			merge(inputArray, temp, start,middle,end);
        		}
        	}
        	
            return;
        }


        /** 
         * Problem 6: Merge Sort for Lists, Without Side Effects
         */
        public static List<Integer> sortList(List<Integer> inputList) {
        	if(inputList.size()!=1){
        	 	return mergeLists(sortList(inputList.subList(0, (0+inputList.size())/2))
        	 			,sortList(inputList.subList((0+inputList.size())/2, inputList.size())));
        	}
       
            return inputList; 
        }
        
        
        //2(N/2) + 4(N/4) + 8(N/8) + ...+ N/2(2) = N+ N + ...+ N = Nlog(N)  So the space require is O(Nlog(N))
        /**
         * New merge method that merges two lists and returns a new list.
         * Use this method to implement sortList.
         */
        public static List<Integer> mergeLists(List<Integer> left, List<Integer> right) { 
        	LinkedList<Integer> list = new LinkedList<Integer>();
        	Iterator<Integer> leftItor  = left.iterator();
        	Integer numLeft = leftItor.next();
    
        	Iterator<Integer> rightItor = right.iterator();
        	Integer numRight = rightItor.next();
        	if(leftItor.hasNext()==false &&rightItor.hasNext()==false){
        		if(numLeft<=numRight){
        			list.add(numLeft);
                	list.add(numRight);
        		}else{
        			list.add(numRight);
        			list.add(numLeft);
        		}
            
            	return list;
        	}
        	while(leftItor.hasNext()&&rightItor.hasNext()){   
        		if(numLeft<numRight){
       				list.add(numLeft);
       				numLeft= leftItor.next();
       			}else{
       				list.add(numRight);
       				numRight = rightItor.next();
       			}
       		}
 
        
        	if(leftItor.hasNext()){
        		while(numRight!=null ){

        			if(numLeft>numRight){
        				list.add(numRight);
        			
        				numRight =null;
        				list.add(numLeft);
        				while(leftItor.hasNext()){
                			list.add(leftItor.next());
                		}
        			}else{
        				list.add(numLeft);
        				if(leftItor.hasNext()){
        					numLeft = leftItor.next();
        				}else if(numRight!=null){
        					list.add(numRight);
        					numLeft =null;
        					numRight =null;
        				}	
        			}
        		}		
        	}
        	
        	if(rightItor.hasNext()){
        		while(numLeft!=null ){
        		
        			if(numLeft>numRight){
        				list.add(numRight);
        				if(rightItor.hasNext()){
        					numRight = rightItor.next();
        				}else if(numLeft!=null){
        					list.add(numLeft);
        					numLeft=null;
        					numRight=null;
        				}
        			
        			}else{
        				list.add(numLeft);
        				numLeft = null;
        				list.add(numRight);	
        				while(rightItor.hasNext()){
                			list.add(rightItor.next());
                		}
        			}
        		}
        		
      
        	}
        	
    
            return list;
        }
        
 
        public static void main(String[] args) {
        	 try {
        	      List<Integer> left = Arrays.asList(new Integer[] { 1 });
        	      List<Integer> leftGold = new LinkedList<Integer>(left);
        	      List<Integer> right = Arrays.asList(new Integer[] { 2 });
        	      List<Integer> rightGold = new LinkedList<Integer>(right);
        	      List<Integer> resultGold = Arrays.asList(new Integer[] { 1, 2 });

        	      List<Integer> resultStudent = MergeSort.mergeLists(left, right);

        	      if (compareCollections(resultStudent, resultGold)) {
        	        if (compareCollections(left, leftGold) && compareCollections(right, rightGold)) {
        	         System.out.println("Pass line 170");
        	        } else {
        	         System.out.println("Original left and right lists modified during merge");
        	        }
        	      } else {
        	        System.out.println(
        	            "List incorrect\nExpected: " + resultGold + "\nActual: " + resultStudent);
        	      }
        	    } catch (Exception e) {
        	      System.out.println("Exception");
        	    }

        	    try {
        	      List<Integer> left = Arrays.asList(new Integer[] { 1, 3 });
        	      List<Integer> leftGold = new LinkedList<Integer>(left);
        	      List<Integer> right = Arrays.asList(new Integer[] { 2, 4 });
        	      List<Integer> rightGold = new LinkedList<Integer>(right);
        	      List<Integer> resultGold = Arrays.asList(new Integer[] { 1, 2, 3, 4 });

        	      List<Integer> resultStudent = MergeSort.mergeLists(left, right);

        	      if (compareCollections(resultStudent, resultGold)) {
        	        if (compareCollections(left, leftGold) && compareCollections(right, rightGold)) {
        	          System.out.println("Pass line 193");
        	        } else {
        	         System.out.println("Original left and right lists modified during merge");
        	        }
        	      } else {
        	       System.out.println(
        	            "List incorrect\nExpected: " + resultGold + "\nActual: " + resultStudent);
        	      }
        	    } catch (Exception e) {
        	      System.out.println("Exception");
        	    }

        	    try {
        	      List<Integer> left = Arrays.asList(new Integer[] { 1, 3, 5, 6 });
        	      List<Integer> leftGold = new LinkedList<Integer>(left);
        	      List<Integer> right = Arrays.asList(new Integer[] { 2, 4 });
        	      List<Integer> rightGold = new LinkedList<Integer>(right);
        	      List<Integer> resultGold = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6 });

        	      List<Integer> resultStudent = MergeSort.mergeLists(left, right);

        	      if (compareCollections(resultStudent, resultGold)) {
        	        if (compareCollections(left, leftGold) && compareCollections(right, rightGold)) {
        	         System.out.println("Pass line 216");
        	        } else {
        	         System.out.println("Original left and right lists modified during merge");
        	        }
        	      } else {
        	       System.out.println(
        	            "List incorrect\nExpected: " + resultGold + "\nActual: " + resultStudent);
        	      }
        	    } catch (Exception e) {
        	    	System.out.println("Exception");
        	    }
//        	    List<Integer> inputList = Arrays.asList(new Integer[] { 3,2,8,9,7,6,4,1,5});
//        	    List<Integer> testing = MergeSort.sortList(inputList);
//        	    System.out.println(testing);
//        	    
//        	    sectionName = "MergeSort.sortList(): correct result for power 2 sized list";
//                sectionScoreMax = 5;
                try {
                

                	List inputList = IntStream.range(1, 9).boxed().collect(Collectors.toList());
                  Collections.shuffle(inputList);
                  List<Integer> inputGold = new LinkedList<Integer>(inputList);
                  List resultGold = IntStream.range(1, 9).boxed().collect(Collectors.toList());

                  List<Integer> resultStudent = MergeSort.sortList(inputList);

                  if (compareCollections(resultStudent, resultGold)) {
                    if (compareCollections(inputList, inputGold)) {
                      System.out.println("Successful+line 300");
                    } else {
                     System.out.println( "Original left and right lists modified during merge");
                    }
                  } else {
                    System.out.println(
                        "List incorrect\nExpected: " + resultGold + "\nActual: " + resultStudent);
                  }
                } catch (Exception e) {
                	e.printStackTrace();
                	System.out.println("Exception");
                }

         
                try {
                  List inputList = IntStream.range(1, 12).boxed().collect(Collectors.toList());
                  Collections.shuffle(inputList);
                  List<Integer> inputGold = new LinkedList<Integer>(inputList);
                  List resultGold = IntStream.range(1, 12).boxed().collect(Collectors.toList());

                  List<Integer> resultStudent = MergeSort.sortList(inputList);

                  if (compareCollections(resultStudent, resultGold)) {
                    if (compareCollections(inputList, inputGold)) {
                     System.out.println("Successful + line 323");
                    } else {
                     System.out.println( "Original left and right lists modified during merge");
                    }
                  } else {
                   System.out.println(
                        "List incorrect\nExpected: " + resultGold + "\nActual: " + resultStudent);
                  }
                } catch (Exception e) {
                 System.out.println("Exception");
                }
        	
        }
     

}
