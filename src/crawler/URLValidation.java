package crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validating the URL with the help of regex
 *
 * @author (Navjot Makkar)
 */
public class URLValidation {

	public static final String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	/**
	 * Validate the given url with the regex
	 * 
	 * @param url (URL to validate)
	 * @return boolean 
	 */
	public boolean validateURL(String url) {

		// Match input with regular expression
		try {
			Pattern patternObj = Pattern.compile(regex);
			Matcher matcherObj = patternObj.matcher(url);
			return matcherObj.matches();
		} catch (RuntimeException e) {
			return false;
		}
	}
}

