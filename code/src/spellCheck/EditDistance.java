package spellCheck;

public class EditDistance {
	public int editDistance(String one, String two) {
		int[][] matrix = new int[one.length() + 1][two.length() + 1];

		for (int i = 0; i <= one.length(); i++)
			matrix[i][0] = i;

		for (int j = 0; j <= two.length(); j++)
			matrix[0][j] = j;

		for (int i = 0; i < one.length(); i++)
			for (int j = 0; j < two.length(); j++)
				if (one.charAt(i) == two.charAt(j))
					matrix[i + 1][j + 1] = matrix[i][j];
				else {
					int deleteLetter = matrix[i + 1][j] + 1;
					int replaceLetter = matrix[i][j] + 1;
					int insertLetter = matrix[i][j + 1] + 1;

					matrix[i + 1][j + 1] = Math.min(Math.min(replaceLetter, insertLetter), deleteLetter);
				}
		return matrix[one.length()][two.length()];
	}
}