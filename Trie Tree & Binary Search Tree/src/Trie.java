import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Trie {

  private TrieNode root;
  public static final char NULL = '0';

  public Trie() {
    root = new TrieNode(NULL, false);
  }

  // implement your methods here
  // feel free (and you probably should) add helper private methods
  // problem 4a
  public void addWord(String word) {
	 if(word == null ){
		 return;
	 }
	 TrieNode current = root;
	 char[] val = word.toCharArray();
	 for(int i=0;i<val.length;i++){
		 int location = val[i] - 'a';
		if(i!=val.length-1){
			if(current.children[location]==null){
				current.children[location] = new TrieNode(val[i], false);
			}
			current = current.children[location];
		}else{
			if(current.children[location]==null){
				current.children[location] = new TrieNode(val[i], true);
			}else{
				current.children[location].endOfWord = true;
			}
		}
	 }
  }

 

// problem 4b
  public boolean contains(String word) {
	  return contains(this.root,word);

  }

  private boolean contains(TrieNode root, String word) {
	  if(word == null || word.trim().equals("")){
		  return true;
	  }
	  char[] val = word.toCharArray();
	  for(int i=0;i<val.length;i++){
		  int location = val[i] - 'a';
		  if(root.children[location]==null){
			  return false;
		  }
		  if(i==val.length-1&&root.children[location].endOfWord==true){
			  return true;
		  }
		  root = root.children[location];
		  
	  }

	  return false;
}

// problem 4c
  public List<String> getStrings() {
	  TrieNode current = root;
	  return getString(current,"");
  }

  private List<String> getString(TrieNode current, String value) {
	  LinkedList<String> list = new LinkedList<>();
	  if(current !=null){
		if(current.endOfWord==true){
			list.add(value);
		}
		for(int i=0;i<current.children.length;i++){
			if(current.children[i]!=null){
				char cha = (char) (i +'a');
				String tempstring = value +cha;
				list.addAll(getString(current.children[i],tempstring));
			}
		}
	}
	return list;
}

// problem 4d
  public List<String> getStartsWith(String prefix) {
	  TrieNode current = root;
	  return getStartsWith(current,prefix);
  }



private List<String> getStartsWith(TrieNode current, String word) {
	LinkedList<String> list = new LinkedList<>();
	  if(word == null || word.trim().equals("")){
		  return list;
	  }
	  char[] var = word.toCharArray();
	  int i;
	  for(i=0;i<var.length;i++){
		  int location = var[i]-'a';
		  if(current.children[location]==null){
			  return list;
		  }	 
		  current = current.children[location];
		
	  }
	  if(i==var.length){
		 return getString(current,word);
	 }
	  
	  
	return list;
}

public String toString() {
    StringBuilder sb = new StringBuilder();
    buildString(root, sb, 0);
    return sb.toString().trim();
  }
  
  private void buildString(TrieNode node, StringBuilder sb, int layer) {
    for (int i = 0; i < layer; i++) {  // print some indentation
      sb.append(" ");
    }
    sb.append(node);    // print the node itself
    sb.append("\n");
    for (TrieNode child : node.children) {  // recursively print each subtree
      if (child != null) {
        buildString(child, sb, layer + 1);
      }
    }
  }

  private class TrieNode {
    public char letter;
    public boolean endOfWord;
    public TrieNode[] children;

    public TrieNode(char letter, boolean endOfWord) {
      this.letter = letter;
      this.endOfWord = endOfWord;
      children = new TrieNode[26]; // number of letters in English alphabet
    }

    public String toString() {
      return endOfWord ? Character.toString(Character.toUpperCase(letter)) : Character.toString(letter);
    }
  }

  public static void main(String[] args) {
	   HashSet<String> dictionary = new HashSet<>();
    Trie trie = new Trie();
    trie.addWord("hello");
    dictionary.add("hello");
    trie.addWord("help");
    dictionary.add("help");
//    trie.addWord("rattata");
//    dictionary.add("rattata");
//    trie.addWord("do");
//    dictionary.add("do");
//    trie.addWord("dog");
//    dictionary.add("dog");
//    trie.addWord("doll");
//    dictionary.add("doll");
//    trie.addWord("dock");
//    dictionary.add("dock");
//    trie.addWord("doctor");
//    dictionary.add("doctor");
    trie.addWord("hell");
    dictionary.add("hell");

   
    System.out.println(trie);
    System.out.println(trie.getStartsWith("hell"));
    System.out.println(trie.getStartsWith("rat"));
  
        if (trie.getStartsWith("rat").size() == 0) {
        	System.out.println("Success");
        } else {
        	System.out.println("Failed");
        }
      
    System.out.println(trie.getStrings());
   // HashSet<String> trieDictionary = new HashSet<String>(trie.getStrings());

//    if (dictionary.equals(trieDictionary)) {
//      System.out.println("True");
//    } else {
//      System.out.println("False");
//    }
  }
}
