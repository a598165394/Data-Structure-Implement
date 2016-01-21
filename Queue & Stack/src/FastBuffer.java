<<<<<<< HEAD
// This class should countain your implementation of the Buffer interface.
import java.util.ArrayDeque;
import java.util.Deque;

public class FastBuffer implements Buffer{
	public Deque<String> data = new ArrayDeque<>();
	public Deque<String> second = new ArrayDeque<>();
	public Deque<String> first = new ArrayDeque<>();
	public Deque<String> temp = new ArrayDeque<>();
	
	private int location= 0;

	@Override
	public int size() {
		if(data.size()!=0){
			return data.size();
		}else{
			return (first.size()+second.size());
		}
			
		
		
	}
	 public String toString() {  
		    return data.toString();  
		  } 
	@Override
	public void load(char[] initial, int cursorPosition) {
		location = cursorPosition;
		data.push(String.valueOf(initial));
		return;
		
	}

	@Override
	public char[] toArray() {	
		int len =second.size();
		for(int i=0;i<len;i++){
			String a = second.pop();

				temp.addFirst(a);

			
		}
		while(temp.size()!=0){
			data.push(temp.removeFirst());
		}
		while(first.size()!=0){
			String a = first.removeFirst();
				data.push(a);
		}

		String result ="";
		while(this.size()>=1){
			result+=this.pop();
		}

		return (result.toCharArray());
	}
	
	@Override
	public int getCursor() {

		return location;
	}
	
	@Override
	public void setCursor(int j) {
		
		while(second.size()!=0){
			this.push(second.removeFirst());
		}
		while(first.size()!=0){
			this.push(first.removeFirst());
		}
		location =j;
		int len =this.size();
		for(int i=0;i<len;i++){
			if(this.size()>location){

				second.addFirst(this.pop()) ;
				
			}else{
				temp.addFirst(this.pop());

			}
		}
		len = temp.size();
		for(int i=0;i<len;i++){
			first.addFirst(temp.pop());
		}
		return;
	}

	@Override
	public void moveRight() {
		first.addFirst(second.removeFirst());
	}

	@Override
	public void moveLeft() {
		second.addFirst(first.removeFirst());

	}

	@Override
	public void insertLeft(char c) {
		this.push(String.valueOf(c));
	}

	@Override
	public char deleteRight() {
		return (second.removeFirst()).charAt(0);
	}

	@Override
	public char deleteLeft() {
		
		return (first.removeFirst()).charAt(0);
	}

	
	public void push(String word){
		data.addFirst(word);;
		
	}
	public String pop(){
		return data.removeFirst();
	}
	public String peek(){
		return data.peekFirst();
	}
	

=======
// This class should contain your implementation of the Buffer interface.

import java.util.Deque;
import java.util.LinkedList;

public class FastBuffer implements Buffer {

  private Deque<Character> left;
  private Deque<Character> right;

  public FastBuffer() {
    init();
  }

  private void init() {
    left = new LinkedList<Character>();
    right = new LinkedList<Character>();
  }

  @Override
  public int size() {
    return left.size() + right.size();
  }

  @Override
  public void load(char[] initial, int cursorPosition) {
    init();
    for (char c : initial) {
      left.addFirst(c);
    }
    setCursor(cursorPosition);
  }

  @Override
  public char[] toArray() {
    char[] array = new char[size()];
    int i = 0;
    for (Character c : left) {
      array[left.size() - 1 - i] = c;
      i++;
    }
    for (Character c : right) {
      array[i] = c;
      i++;
    }
    return array;
  }

  @Override
  public int getCursor() {
    return left.size();
  }

  @Override
  public void setCursor(int j) {
    while (getCursor() != j) {
      if (j < getCursor()) {
        moveLeft();
      }
      if (j > getCursor()) {
        moveRight();
      }
    }
  }

  @Override
  public void moveRight() {
    left.addFirst(right.removeFirst());
  }

  @Override
  public void moveLeft() {
    right.addFirst(left.removeFirst());
  }

  @Override
  public void insertLeft(char c) {
    left.addFirst(c);
  }

  @Override
  public char deleteRight() {
    return right.removeFirst();
  }

  @Override
  public char deleteLeft() {
    return left.removeFirst();
  }

    
    public static void main(String[] args) {
        
        FastBuffer myBuff = new FastBuffer();
        //System.out.println("Cursor at "+myBuff.getCursor());
        myBuff.insertLeft('1');
        //System.out.println("Cursor at "+myBuff.getCursor());
        myBuff.insertLeft('2');
        myBuff.insertLeft('3');
        myBuff.insertLeft('4');
   
        myBuff.setCursor(3);
        myBuff.insertLeft('5');
        myBuff.insertLeft('6');
        myBuff.insertLeft('7');
  
        
        char[] theArray = myBuff.toArray();

        
        for (int i = 0; i < theArray.length; i++) 
        {
            System.out.print(theArray[i]);
        }
        System.out.println();
        
        //Add "*$" at position 6
        myBuff.setCursor(6);
        myBuff.insertLeft('*');
        myBuff.insertLeft('$');
      
        // Print again: 
        theArray = myBuff.toArray();
        for (int i = 0; i < theArray.length; i++) 
        {
            System.out.print(theArray[i]);
        } 
        System.out.println();
        
        // delete the junk characters
        System.out.println("Deleted: " + myBuff.deleteLeft());
        System.out.println("Deleted: " + myBuff.deleteLeft());
        //System.out.println("Deleted: " + myBuff.deleteRight());
        
        theArray = myBuff.toArray();
        for (int i = 0; i < theArray.length; i++) {
            System.out.print(theArray[i]);
        }
        System.out.println();
        
    }
    
>>>>>>> a6acd542ffdd1cc32e39b9bafa47e76888eaf046
}