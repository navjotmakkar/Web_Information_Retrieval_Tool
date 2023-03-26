package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import crawler.Crawler;
import crawler.Dictionary;
import crawler.URLValidation;
import searchhistory.SearchHistory;
import searchWord.pageRanking;
import FrequencyCounter.FrequencyCounter;
import spellCheck.SpellCheck;
import spellCheck.TST;


/**
 * Main class to start the web search engine
 * @author Navjot Makkar, Akshit Bhatia, 
 * Gagan Singh Golar and Utsav Krishnatra
 *
 */
public class SearchEngine {

	public static final int maxCrawlLimit = 10;
	static SearchHistory history = new SearchHistory(5);

	public static void startCrawlerParser(String webPageURL) throws Exception {
		Crawler crawler = new Crawler();

		// Crawl Web Pages and parse crawled HTML pages
		crawler.crawl(maxCrawlLimit, webPageURL);

		// create DIctionary from txt files
		Dictionary dictionary = new Dictionary();
		dictionary.createDictionary();
		System.out.println("\nDictionary created from the text files");
	}

	/**
	 * Method to delete existing data from file system
	 */
	public static void checkDirAndFiles() {
		File htmlDir = new File("HTMLFiles/");
		File textDir = new File("TextFiles/");
		File dictFile = new File("dictionary.txt");

		// Delete old files and directories and if doesn't exists and then create
		try {
			//Check for dictionary file
			if (!dictFile.exists()) {
				dictFile.createNewFile();
			} else {
				dictFile.delete();
			}
			
			//Check for HTML files
			if (!htmlDir.exists()) {
				htmlDir.mkdir();
			} else {
				for (File file : htmlDir.listFiles()) {
					if (!file.isDirectory())
						file.delete();
				}
			}
			
			//Check for text files
			if (!textDir.exists()) {
				textDir.mkdir();
			} else {
				for (File file : textDir.listFiles()) {
					if (!file.isDirectory())
						file.delete();
				}

			}
		} catch (IOException e) {
			System.out.println("Error occured while checking if directories "
					+ "already exists- "+e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("******************************"+ 
							"\nWelcome to Web Search Engine"+
							"\nDeveloped by-"+
							"\n Akshit Bhatia"+
							"\n Gagan Singh Golar"+
							"\n Navjot Makkar"+
							"\n Utsav Krishnatra"+
							"\n******************************"
							);
		
		String websiteURL;
		
		try (Scanner scn = new Scanner(System.in)) {

			// Delete Cache data
			checkDirAndFiles();

			// Get url from the user
			System.out.print("\nPlease enter the website URL you wish to crawl: ");
			URLValidation urlVal = new URLValidation();
			websiteURL = scn.nextLine();

			// validate the url
			while (!urlVal.validateURL(websiteURL)) {
				System.out.print("\nProvided url is invalid, please enter a valid URL: ");
				websiteURL = scn.nextLine();
			}

			// Start crawler, html parsing and dictionary creation
			startCrawlerParser(websiteURL);
			
			// Continue the code
			otherFunctionality();
		}
	}

	private static void otherFunctionality() throws FileNotFoundException, IOException, InterruptedException {
		String input = null;
		String query = null;
		System.out.print("\n\nWhat would you like to do next?\n\n");
		System.out.println("1. Search Word");
		System.out.println("2. Check Spelling");
		System.out.println("3. Check Suggestion");
		System.out.println("4. Frequency Count");
		System.out.println("5. Show history");
		System.out.println("6. Exit from search engine \n");
		System.out.println("So what do you want to do? ");
		
		
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();

//		while (!input.toLowerCase().equals("exit")) {
			switch (input) {
			case "1":
				// Input from user
				System.out.print("Enter something to search: ");
				query = sc.nextLine();
				pageRanking search = new pageRanking();
				//Storing to history
				history.addSearch(query);
				// Call method for searching 
				try {
					search.rankPages(query.toLowerCase());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "2":
				// Input from user
				System.out.print("Enter a word to check it's spelling: ");
				query = sc.nextLine();
				//Storing to history
				history.addSearch(query);
				SpellCheck suggestSpelling = new SpellCheck();

				// Call method for spell-check
				suggestSpelling.checkSpelling(query.toLowerCase());
				break;

			case "3":
				// Input from user
				System.out.print("Enter a word to get it's suggestion: ");
				query = sc.nextLine();
				//Storing to history
				history.addSearch(query);
				// Call method for get suggestions
				TST.suggestion(query);
				break;
			
			case "4":
				System.out.println("Enter a word to count it's frequency : ");
				query = sc.nextLine();
				FrequencyCounter countFreq = new FrequencyCounter();
				//Storing to history
				history.addSearch(query);
				// Call method for frequency Counter
				countFreq.countWords(query);
				break;

			case "5":
				//Calling function to display history
				// Prints the title of the most recent entry
				history.printHistory();
				break;
			case "6":
				System.out.println("Thank you!");
				sc.close();
				System.exit(0);
			default:
				System.out.println("Please enter a valid input");
			}

			otherFunctionality();
		}
//	}
}