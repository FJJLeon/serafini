package partA.test;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.*;
import java.util.*;

import org.junit.Test;

import partA.wordladder;

public class unitTest {
	
	
	@Test
	public void test_JUnit() {
		assertEquals(2, 1 + 1);
	}
	
	@Test
	public void SuccessReadDict() {
		Set<String> dict = new HashSet<String>();
		
		//PrintStream console = null;
		//console = System.out;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		
		System.setIn(new ByteArrayInputStream("smalldict1.txt".getBytes()));
		wordladder.readDict(dict);
		assertEquals("Dictionary file name?", bytes.toString());
		assertEquals(dict.size(), 91);
		assertFalse(!dict.contains("bee"));
	}
	
	@Test
	public void FailReadDict() {
		Set<String> dict = new HashSet<String>();
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		
		System.setIn(new ByteArrayInputStream("wrongname.txt\nsmalldict1.txt".getBytes()));;
		wordladder.readDict(dict);
		assertEquals("Dictionary file name?Unable to open that file.  Try again.\nDictionary file name?", bytes.toString());
		assertEquals(dict.size(), 91);
		assertFalse(!dict.contains("bee"));
	}
	
	@Test
	public void ReadWord() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		//read dict first
		Set<String> dict = new HashSet<String>();
		System.setIn(new ByteArrayInputStream("smalldict1.txt".getBytes()));;
		wordladder.readDict(dict);
		//read word
		String[] words = new String[2];
		System.setIn(new ByteArrayInputStream("bee\nbog".getBytes()));;
		wordladder.readWord(words, dict);
		assertEquals("bee",words[0]);
		assertEquals("bog",words[1]);
	}
	
	@Test
	public void WordIsvalid1() {
		String[] valid = new String[2];
		valid[0] = "bee";
		valid[1] = "bog";
		assertFalse(!wordladder.wordIsvalid(valid));
	}
	
	@Test
	public void WordIsvalid2() {
		String[] valid = new String[2];
		valid[0] = "bee";
		valid[1] = "boge";
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		// not valid
		assertFalse(wordladder.wordIsvalid(valid));
		// output 
		assertEquals("The two words must be the same length.\n", bytes.toString());
	}
	
	@Test
	public void WordIsvalid3() {
		String[] valid = new String[2];
		valid[0] = "bee";
		valid[1] = "bee";
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		// not valid
		assertFalse(wordladder.wordIsvalid(valid));
		// output 
		assertEquals("The two words must be different.\n", bytes.toString());
	}
	
	@Test
	public void successBFS() {
		//prepare for BFS
		Set<String> dict = new HashSet<String>();
		System.setIn(new ByteArrayInputStream("smalldict1.txt".getBytes()));;
		wordladder.readDict(dict);
		
		String[] words = new String[2];
		words[0] = "bee";
		words[1] = "bog";
		
		Queue<Stack<String>> ladder = new LinkedList<Stack<String>>();
		Stack<String> tmp_Stack = new Stack<String>();
		
		tmp_Stack.push(words[0]);
		ladder.offer(tmp_Stack);
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		
		wordladder.BFS(ladder, words, dict);
		
		assertEquals("A ladder from bog back to bee:\nbog beg bee \r\n",bytes.toString());
		
	}
	
	@Test
	public void failBFS() {
		//prepare for BFS
		Set<String> dict = new HashSet<String>();
		System.setIn(new ByteArrayInputStream("smalldict1.txt".getBytes()));;
		wordladder.readDict(dict);
		
		String[] words = new String[2];
		words[0] = "bush";
		words[1] = "code";
		
		Queue<Stack<String>> ladder = new LinkedList<Stack<String>>();
		Stack<String> tmp_Stack = new Stack<String>();
		
		tmp_Stack.push(words[0]);
		ladder.offer(tmp_Stack);
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		
		wordladder.BFS(ladder, words, dict);
		
		assertEquals("No word ladder found from code back to bush.\n",bytes.toString());
		
	}
	/*tip
	 * 1.
	 * println give a end with "\r\n"
	 * while print("\n") with "\n"
	 * 
	 * 2.
	 * use redirect to get io 
	 * example:
	 * ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	 * System.setOut(new PrintStream(bytes));
	 * 
	 * System.setIn(new ByteArrayInputStream("smalldict1.txt".getBytes()));;
	 * 
	 */
}
