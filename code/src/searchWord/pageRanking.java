package searchWord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;

import spellCheck.SpellCheck;

/**
 * @author utsav krishnatra
 *
 */
public class pageRanking {

	public static int rankedPageIndex = 1;
	
	
	/**
	 * This function rank pages on the basis of #String pattern 
	 * @param searchQuery
	 * @return
	 * @throws Exception
	 */
	public boolean rankPages(String searchQuery) throws Exception {

		// time at which action begins
		Long bt;
		// time at which action ends
		Long et;
		// time taken to complete the entire action
		float tt = 0;

		//
		rankedPageIndex = 1;

		// Search query in dictionary
		File fileLexicon = new File("dictionary.txt");

		// if the word is found in the dictionary; return true, else return false
		boolean ifWordExists = false;
		// try with resource statement ensures if we use any resource( BufferedReader
		// here), it is fetched only when the lock is available on.
		// Moreover, it is closed after the file is read instead of explicitly closing
		// it using try catch finally block.

		// Using bufferedreader to read the File data from dictionary.txt
		try (BufferedReader br = new BufferedReader(new FileReader(fileLexicon))) {
			
			// reading dictionary into the Arraylist of string, vocabulary
			ArrayList<String> vocabulary = new ArrayList<String>();

			// variable that stores each line of the file
			String currentLineInput;
			// reading input from the file line by lineF
			while ((currentLineInput = br.readLine()) != null) {
				// reading all data strings stored in the dictionary.txt line-by-line.
				vocabulary.add(currentLineInput);
				// vocabulary.insert(currentLineInput.toLowerCase());
			}
			// closing the bufferedreader after reading the file
			br.close();

			// reading each word from the dictionary. This method searched all the words in
			// the dictionary, hence making search T(n)=O(n),n= number of words in the
			// dictionary
			for (String eachWord : vocabulary) {
				// searching each word present in the dictionary whether it matches with the
				// string given in the query.
				if (searchQuery.toLowerCase().equals(eachWord.toLowerCase())) {
					// if the word is found,return true
					ifWordExists = true;
				}
			}

			// If word is not found in dictionary, provide suggestion for correct word and
			if (!ifWordExists) {
				System.out.println("\nUser entered word does not exist in dictionary");
				// spellcheck function to check whether the spelling of word is right, and
				// provide suggestions of the nearest words to it.
				SpellCheck suggestion = new SpellCheck();
				suggestion.provideSuggestion(searchQuery);
				return false;
			} else {
				SearchPattern searchPatternObj = new SearchPattern();
				bt = System.currentTimeMillis();
				Hashtable<String, Integer> rankedPages = searchPatternObj.searchPatterns(searchQuery);
				et = System.currentTimeMillis();
				tt = et - bt;

				// if pattern is not found and an empty ranking is returned by searchPattern
				// function.
				if (rankedPages.size() == 0) {
					SpellCheck suggestion = new SpellCheck();
					suggestion.provideSuggestion(searchQuery);
				} else {
					System.out.println("Looking for occurances of the word " + searchQuery + " from "
							+ rankedPages.size() + " pages." + "\n");
					// sum total of occurrence of the word across all the pages
					int ttlOcrnce = 0;

					for (int ocrnces : rankedPages.values())
						ttlOcrnce += ocrnces;

					// printing the page URL, number of occurrences of the word in the respective
					// pages for all the pages drawn.
					System.out.println("Found total " + ttlOcrnce + " matches for the word " + searchQuery + " in ("
							+ tt / 1000 + " seconds)");
					System.out.println(
							"word " + searchQuery + " exists in  " + rankedPages.size() + " number of web pages.\n");
					System.out.printf("| %-4s | %-4s | %-20s |\n", "Rank", "Value", "Key");
					System.out.println("+------+-------+----------------------");

					// Initializing a priority queue<entries> to rank pages in T(n) = O(nlogn)
					// time.Initializing priority queue for data type Map.Entry<String, Integer>
					// along with a lambda function to
					// to compare and align each entry in the priority queue in descending order.

					PriorityQueue<Map.Entry<String, Integer>> entries = new PriorityQueue<>(
							(e1, e2) -> e2.getValue().compareTo(e1.getValue()));
					entries.addAll(rankedPages.entrySet());

					// ranking the page with maximum frequency of the word.Ranking in descending
					// order.
					int rankedPageIndex = 1;

					// looping through all the entries in the priority queue to and displaying all
					// the pages and its frequency respectively.
					while (!entries.isEmpty()) {

						Map.Entry<String, Integer> entry = entries.poll();
						System.out.println(String.format("%02d", rankedPageIndex) + "	"
								+ String.format("%02d", entry.getValue()) + "          " + entry.getKey());

						rankedPageIndex++;

					}
					System.out.println("+------+-------+----------------------");
				}
				return true;
			}
		}
	}
}