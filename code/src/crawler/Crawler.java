package crawler;

import java.io.File;
import java.io.FileWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Web Crawler Class: Crawling web page, converting to HTML file 
 * and then to text file for further processing
 *
 * @author (Navjot Makkar)
 */
public class Crawler {

	/**
	 * Crawl the webpages starting from the given URL until the maximum crawler
	 * limite is not reached.
	 * 
	 * @param websiteUrl    - URL of the website that needs to be crawled
	 * @param maxCrawlLimit - Maximum number of webpages crawler will crawl.
	 */
	public void crawl(int maxCrawlLimit, String websiteUrl) {
		// initializing the set to store the links which are already crawled
		Set<String> crawledURLs = new HashSet<String>();
		// initializing the Queue to store the the URLs
		Queue<String> webpageUrlQueue = new ArrayDeque<>();
		// count of skipped URLS
		int numDuplicateURLs = 0, numInvalidURLs = 0;

		System.out.println("Crawling the website: " + websiteUrl);

		// Add the webpage URL to the websiteUrlList list
		webpageUrlQueue.add(websiteUrl);
		
		//List of files present in HTMLFiles directory
		File totalPages = new File("HTMLFiles/");

		while (!webpageUrlQueue.isEmpty()) {
			String url = webpageUrlQueue.remove();

			// Check if maximum crawl limit is reached
			if (totalPages.list().length >= maxCrawlLimit) {
				break;
			}
			
			// Validate URL
			URLValidation urlVal = new URLValidation();
			if (!urlVal.validateURL(url)) {
				System.out.println("Invalid URL: "+ url);
				numInvalidURLs++;
				continue;
			}

			// Check if URL exists in the alreadyCrawledUrls list
			if (crawledURLs.contains(url)) {
				System.out.println("Skipping the duplicate URL: "+ url);
				numDuplicateURLs++;
				continue;
			} // Add the URL to the list of already Crawled URLs
			else {
				crawledURLs.add(url);
			}

			System.out.println("Crawling : " + url);
			String filePath = null;
			try {
				Document jsoupDocObj = Jsoup.connect(url).get();
				String title = jsoupDocObj.title();
				String body = jsoupDocObj.html();

				// Save the webpage in html format on the disk
				if (!title.isEmpty()) {
					filePath = saveHTMLFile(title, body);
				}

				// Fetch hyperlink from current URL and add to crawler queue
				Elements hyperlinks = jsoupDocObj.select("a[href]");
				for (Element link : hyperlinks) {
					webpageUrlQueue.add(link.attr("abs:href"));
				}
				
				String textFileContent = convertHtmlToText(filePath);
				saveTextFile(filePath, textFileContent);
				
			} catch (IOException e) {
				System.out.println("error in crawling the webpage: " + url);
			}
		}
		System.out.println("\nCrawling completed, extracted HTML files and upon parsing the resulted text files are stored in the disk.");
		//printing results of crawling
		System.out.println("*****************Crawling Report*****************");
		System.out.println("Number of HTML files generated: "+totalPages.list().length);
		System.out.println("Number of Invalid URLs encountered: "+numInvalidURLs);
		System.out.println("Number of Duplicate URLs encountered: "+numDuplicateURLs);
		System.out.println("*************************************************");		
	}

	/**
	 * Save the webpage on the disk in the html format
	 * 
	 * @param title title of the webpage
	 * @param body  content of the webpage
	 * @return
	 */
	public String saveHTMLFile(String title, String body) {
		String fileName = null;
		try {
			//remove non-alphnumeric character
			title = title.replaceAll("[^A-Za-z0-9]", " ");
			title = title.trim();
			fileName = title + ".html";
			FileWriter fw = new FileWriter("./HTMLFiles/" + fileName);
			//writing the content to the file
			fw.write(body);
			fw.close();
		} catch (IOException e) {
			System.out.println("Error while saving web page: " + title);
		}
		return fileName;
	}

	/**
	 * Convert HTML file to Text File and save in the disk
	 * 
	 * @param fname (HTML file name)
	 * @return (HTML file content)
	 * @throws IOException
	 */
	public String convertHtmlToText(String fname) throws IOException {
		String currDirPath = System.getProperty("user.dir");
		String htmlFilePath = currDirPath + "\\HTMLFiles\\" + fname;
		//reading the file content and setting encoding 
		byte[] htmlContentBytes = Files.readAllBytes(Paths.get(htmlFilePath));
		Charset charEncoding = StandardCharsets.US_ASCII;
		//Parsing html content through jsoup and returning the text
		String htmlFileContent = new String(htmlContentBytes, charEncoding);
		Document jsoupDocObj = Jsoup.parse(htmlFileContent);
		return jsoupDocObj.text();
	}

	/**
	 * Save the text file in the disk at ./TextFiles
	 * 
	 * @param fName file name
	 * @param textContent  file content
	 * @throws IOException
	 */
	public void saveTextFile(String fName, String fContent) {
		FileWriter fw;
		try {
			//c freating the file writer object with proper filename
			fw = new FileWriter(
					"TextFiles/" + fName.substring(0, fName.lastIndexOf(".")) + ".txt");
			//writing to the txt file
			fw.write(fContent);
			fw.close();
		} catch (IOException e) {
			System.out.println("Error while saving text file for "+ fName);
		}
		
	}
}
