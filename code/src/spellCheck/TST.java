package spellCheck;

//Import required libraries
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author Akshit Bhatia 
 */

// Structure of the Ternary Search Tree node
class Node {
	// Current character
	char character;
	String value;
	Node left, center, right;

	// Constructor to create a new node
	public Node(char character) {
		this.character = character;
	}
}

//Define the Ternary Search Tree class
public class TST {
	// root of the tree
	private Node root;

	/*
	 * Method to retrieve the value associated with a given key
	 * 
	 * @param search - To get the value associated with a search
	 */
	public String get(String search) {
		Node node = get(root, search, 0);
		if (node == null)
			return null;
		else
			return node.value;
	}

	/*
	 * Method to retrieve the node in the TST that corresponds to a given word
	 * 
	 * @param node - the current node
	 * 
	 * @param search - word to search
	 * 
	 * @param index - index of the current character in the word
	 */
	private Node get(Node node, String search, int index) {
		if (node == null) {
			return null;
		}
		char currentCharacter = search.charAt(index);
		if (currentCharacter < node.character)
			return get(node.left, search, index);
		else if (currentCharacter > node.character)
			return get(node.right, search, index);
		else if (index < search.length() - 1)
			return get(node.center, search, index + 1);
		else
			return node;
	}

	/*
	 * Method to insert a new key-value pair
	 * 
	 * @param key - Word to be added to dictionary
	 * 
	 * @param value - Word to be added to dictionary
	 */
	public void put(String key, String value) {
		root = put(root, key, value, 0);
	}

	/*
	 * Recursive helper method to insert a new key-value pair
	 * 
	 * @param Node - Root node
	 * 
	 * @param key - Word to be added to dictionary
	 * 
	 * @param value - Word to be added to dictionary
	 * 
	 * @index - Index
	 */
	private Node put(Node node, String key, String value, int index) {
		char currentCharacter = key.charAt(index);
		// create a new node if the current node is null
		if (node == null)
			node = new Node(currentCharacter);
		if (currentCharacter < node.character)
			node.left = put(node.left, key, value, index);
		else if (currentCharacter > node.character)
			node.right = put(node.right, key, value, index);
		else if (index < key.length() - 1)
			node.center = put(node.center, key, value, index + 1);
		else
			node.value = value;
		return node;
	}

	/*
	 * Method to retrieve the suggestions for a given word
	 * 
	 * @param word - Word to be added to dictionary
	 */
	public List<String> suggestions(String word) {
		List<String> results = new ArrayList<>();
		Node node = get(root, word, 0);
		if (node != null) {
			if (node.value != null)
				results.add(word);
			collect(node.center, word, results);
		}
		return results;
	}

	/*
	 * Method collects all the words stored
	 * 
	 * @param node: the current node being looped
	 * 
	 * @paramword: the prefix being searched for
	 * 
	 * @param results: the list of matching words found so far
	 * 
	 */
	private void collect(Node node, String word, List<String> results) {
		if (node == null)
			return;
		collect(node.left, word, results);
		if (node.value != null)
			results.add(word + node.character);
		collect(node.center, word + node.character, results);
		collect(node.right, word, results);
	}

	public static void suggestion(String input) throws FileNotFoundException, IOException {
		// Read words from file
		List<String> words = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"))) {
			String line;
			while ((line = reader.readLine()) != null)
				words.add(line);
		}

		// Create TST and add words to it
		TST tree = new TST();
		for (String str : words) {
			tree.put(str, str);
		}

		List<String> suggestions = tree.suggestions(input);
		System.out.println("Suggestions for " + input + ": " + suggestions.subList(0, Math.min(5, suggestions.size())));
	}
}