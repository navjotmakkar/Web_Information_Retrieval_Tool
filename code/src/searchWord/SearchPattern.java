package searchWord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.regex.Matcher;

/**
 * Class for Pattern search
 *
 * @author (Utsav Krishnatra)
 */

//class that searches patterns that matches the string searchPattern
public class SearchPattern {
	
	/**
	 * searches patterns that matches the string searchPattern
	 * @param searchPattern
	 * @return Hashtable<String, Integer>
	 * @throws IOException
	 */
	public Hashtable<String, Integer> searchPatterns(String searchPattern) throws IOException {
		//changing it to lower case in order to avoid case-sensitivity.
		searchPattern = searchPattern.toLowerCase();
		//Hashtable that stores the rank of all pages found while crawling.
		Hashtable<String, Integer> pageRank = new Hashtable<String, Integer>();
		//directory path within which all files are specified. We have to search pattern within these files.
		File dirPath = new File("TextFiles");
		//listing all the files that are encapsulated within this directory.
		String fileDir[] = dirPath.list();
		//parsing the file and reading its content to variable (currstrFileContent)
		String currstrFileContent = "";
		//iterating until the end of the file and fetching all data.
		for (int i = 0; i < fileDir.length; i++) {
			//reading through all the files sequentially.
			String fileName = fileDir[i];
			//reading the file content
			currstrFileContent = readContentsWithinFile(fileName);
			// converting to lowercase
			currstrFileContent = currstrFileContent.toLowerCase();
			//Creating pattern object to use matcher function over it.
			Pattern patternObject = Pattern.compile(searchPattern);
			//Using matcher function to find all the patterns and counting their occureances.
			Matcher matcherObject = patternObject.matcher(currstrFileContent);
			//using matcher (find) function to find the next match of the pattern.
			while (matcherObject.find()) {
				//using hashtable merge function to sum up all the occurrences of the pattern within each file.
				pageRank.merge(fileName, 1, Integer::sum);
			}
		}
		
		//returning the Hashtable pageRank containing the occurrence of the word within each file.
		return pageRank;
	}

	
	/**
	 * function to read the contents of the file.
	 * @param fileName
	 * @return String
	 * @throws IOException
	 */
	public String readContentsWithinFile(String fileName) throws IOException {
		byte[] byteFile = Files.readAllBytes(Paths.get("./TextFiles/" + fileName));
		String strFileContent = new String(byteFile, StandardCharsets.US_ASCII);
		return strFileContent;
	}
}