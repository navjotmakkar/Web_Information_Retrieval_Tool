package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Creating Dictionary of all the unique words excluding the stop words
 *
 * @author (Navjot Makkar)
 */
public class Dictionary {

	HashSet<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "and", "are", "as", "at", "be", "by", "for",
			"from", "has", "he", "in", "is", "it", "its", "of", "on", "that", "the", "to", "was", "were", "she",
			"there", "them"));

	/**
	 * Create dictionary file from all the text files present in the directory
	 * 
	 */
	public void createDictionary() {
		// Getting all files from the directory
		File[] files = new File("TextFiles/").listFiles();
		
		// Initializing dictionary as list of strings
		ArrayList<String> dict = new ArrayList<String>();

		// Initializing Set to store all unique words
		Set<String> uniqueWordSet = new HashSet<>();

		//Iterate through all files and add words to the set object
		createUniqueWordSet(files, uniqueWordSet);
		
		// Adding the words to the dictionary list
		dict.addAll(uniqueWordSet);
		
		// Sorting the list
		Collections.sort(dict);
		
		//Save the list to the file in the disk
		saveDictToFile(dict);
	}

	/**
	 * Write the dictionary object to the text file
	 * @param dict Dictionary object which is the list of strings
	 */
	private void saveDictToFile(ArrayList<String> dict) {
		try {
			FileWriter fw = new FileWriter("dictionary.txt");
			//Iterating through each word in the dictionary list
			for (String word : dict)
				if(!word.isEmpty())
				//write word by word in the file
					fw.write(word + System.lineSeparator());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the unique word set iterating through all the files and removing the stop words.
	 * @param files List of files
	 * @param uniqueWordSet Set of all the words present in the files
	 */
	private void createUniqueWordSet(File[] files, Set<String> uniqueWordSet) {
		String line;
		// Iterate through each file
		for (File file : files) {
			try {
				BufferedReader buffReaderObj = new BufferedReader(new FileReader(file));
				while ((line = buffReaderObj.readLine()) != null) {
					try (Scanner scan = new Scanner(line)) {
						while (scan.hasNext()) {
							// Remove non-alphabetic characters and adding it to the set
							String word = scan.next().replaceAll("[^A-Za-z ]", "").toLowerCase();
							// Adding the word to the dictionary if it is not a stop-word.
							if (!stopWords.contains(word.toLowerCase()))
								uniqueWordSet.add(word);
						}
					}
				}
				buffReaderObj.close();
			} catch (IOException e) {
				System.out.println("Error occured while creating dictionary");
				e.printStackTrace();
			}
		}
	}
}
