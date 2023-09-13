# Web Information Retrieval Utility
**About:**
The Web Information Retrieval Tool is a comprehensive solution for efficiently searching, crawling, and analyzing web content. Here are some of its key features:

- **Web Crawling with Jsoup:** This tool leverages Jsoup to connect to websites, download HTML content, and operate on HTML elements, attributes, and text using DOM selectors. It also provides advanced encoding techniques for parsing HTML content to text format.

- **URL Validation with Regex:** To ensure the quality of data, the tool includes URL validation using regular expressions. This helps in validating and filtering out invalid or irrelevant links during web crawling.

- **Creating a Dictionary of Words:** The project employs a HashSet to create a unique set of words found in text files, removing stop-words. The resulting dictionary is saved as a .txt file and is used in various functionalities such as pattern search and spell check.

- **Search Word and Spell Check:** Utilizing regular expressions and the Edit Distance Algorithm, the tool offers powerful search capabilities and efficient spell checking. It sorts results using a priority queue and stores them in a hashtable for quick retrieval.

- **Word Suggestion:** For enhanced user experience, a word suggestion feature is implemented using a Ternary Search Tree (TST), allowing users to correct spelling errors seamlessly.

- **Frequency Counter:** This functionality enables the tool to count the frequency of specific words across a collection of websites crawled. It provides valuable insights into website themes and trends.

- **Storing History:** Implementing priority queues and HashMaps, the tool efficiently stores historical data. Priority queues organize data by priority, while HashMaps allow for fast retrieval of historical information.

**How to run**
1. Import the project in Eclipse. Make sure you have empty folders "HTMLFiles" and "TextFiles" in your project root directory.
2. Use Java 8 to build the project
3. Run the SearchEngine.java file inside 'main' package.
4. Provide the url in the console. Example- https://walmart.com
