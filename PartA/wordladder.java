package PartA;

import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class wordladder {
	public static void main(String[] args) {
		
		//save dictionary
		Set<String> dict = new HashSet<>();
		//input the dictionary
		read_dict(dict);
		
		String[] words = new String[2];
		while (true) {
			//input two words
			read_word(words);
			//check word valid
			while (!word_valid(words)) {
				read_word(words);
			}
			
			Queue<Stack<String>> ladder = new LinkedList<Stack<String>>();
			Stack<String> tmp_Stack = new Stack<>();
			
			tmp_Stack.push(words[0]);
			ladder.offer(tmp_Stack);
			
			//BFS
			BFS(ladder, words, dict);	
		}
	}


	private static void BFS(Queue<Stack<String>> ladder, String[] words, Set<String> dict) {
		String word1 = words[0];
		String word2 = words[1];
		//to save used word
		Set<String> used_dict = new HashSet<>();
		Stack<String> loopStack = new Stack<>();
		
		while (!ladder.isEmpty()) {
			//clear temporary stack
			while (!loopStack.isEmpty()) 
				loopStack.clear();
			//Retrieves and removes the head of this queue
			loopStack = ladder.poll();
			//BFS
			String top = loopStack.peek();
			for (int i = 0; i < top.length(); i++) {
				for (char c = 'a'; c <='z'; c++) {
					// if the replace char is the same as origin top, pass
					if (top.charAt(i) == c)
						continue;
					// replace i index to c 
					String tmp_word = top.substring(0,i) + c + top.substring(i+1);
					// the word after replace is used
					if (used_dict.contains(tmp_word))
						continue;
					// the word after replace is the same as word1
					if (tmp_word.equals(word2)) {
						System.out.printf("A ladder from %s back to %s:\n%s ",word2,word1,word2);
						while (!loopStack.isEmpty()) {
							System.out.printf("%s ",loopStack.peek());
							loopStack.pop();
						}
						System.out.println();
						return;
					}
					// the word after replace exists in dict
					if (dict.contains(tmp_word)) {
						loopStack.push(tmp_word);
						Stack<String> tmpS = (Stack<String>) loopStack.clone();
						ladder.offer(tmpS);
						//recover tmpS
						loopStack.pop();
						//add the word to used, or infinite loop
						used_dict.add(tmp_word);
					}
				}
			}
		}
		// not found until queue empty
		System.out.printf("No word ladder found from %s back to %s.\n",word2,word1);
		return;
	}

	private static void read_dict(Set<String> dict) {
		// a set contain all valid name of dictionary
		Set<String> dicts = new HashSet<>() {{
			add("dictionary.txt");
			add("EnglishWords.txt");
			add("smalldict1.txt");
			add("smalldict2.txt");
			add("smalldict3.txt");
		}};
		//choose dictionary
		Scanner in = new Scanner(System.in);
		System.out.print("Dictionary file name?");
		String dict_name = in.nextLine();
		while (!dicts.contains(dict_name)) {
			System.out.print("Unable to open that file.  Try again.\nDictionary file name?");
			dict_name = in.nextLine();
		}
		//in.close();
		File file = new File("src\\PartA\\" + dict_name);
		try {
			//read by line
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String nextLine = null;
			
			while ((nextLine = reader.readLine()) != null) {
                dict.add(nextLine);
            }
			reader.close();
		} catch (IOException e) {
            e.printStackTrace();
        } 
		/*
		Iterator<String> iter = dict.iterator();  
	    while(iter.hasNext()){  
	    System.out.println(iter.next());         
	    }  
	    */
	}

	private static void read_word(String[] words) {
		//read word1 and word2, check \n
		Scanner in = new Scanner(System.in);
		System.out.print("Word #1 (or Enter to quit):");
		String word1 = new String(in.nextLine());
		words[0] = word1;
		
		if (word1.length() == 0) {
			//if \n, exit
			System.out.println("Have a nice day.");
			System.exit(0);
		}
		
		System.out.print("Word #2 (or Enter to quit):");
		String word2 = new String(in.nextLine());
		words[1] = word2;
		if (word2.length() == 0) {
			//if \n, exit
			System.out.println("Have a nice day.");
			System.exit(0);
		}
	}
	
	private static boolean word_valid(String[] words) {
		//check words length is not same
		if (words[0].length() != words[1].length()) {
			System.out.print("The two words must be the same length.");
			return false;
		}
		//check words is not same
		if (words[0].equals(words[1])) {
			System.out.print("The two words must be different.");
			return false;
		}
		return true;
	}
	
}

/*
summary
1. string is different from that in c++
reference is by value, when in func as argu
changed within a func, but won't change at global

2.when offer a Queue a Stack as element, all object is reference
if this Stack is changed at other place, then the stack in the queue is also changed

	loopStack.push(tmp_word);
	Stack<String> tmpS = (Stack<String>) loopStack.clone();
	ladder.offer(tmpS);

above style is OK
*/