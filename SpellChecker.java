
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if (str.length() == 1) {
			return "";
		}
		return str.substring(1, str.length());
	}

	public static int levenshtein(String word1, String word2) {
		int length_a = word1.length();
		int length_b = word2.length();

		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();

		if (length_b == 0) {
			return length_a;
		}
		else if (length_a == 0) {
			return length_b;
		}

		else if (word1.charAt(0) == word2.charAt(0)) {
			return levenshtein(tail(word1), tail(word2));
		}


		int min = Math.min(levenshtein(tail(word1), word2), levenshtein(word1, tail(word2)));
		min = Math.min(min, levenshtein(tail(word1), tail(word2)));
		return (1 + min);
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		for (int i = 0; i < 3000; i++) {
			String word = in.readLine();
			dictionary[i] = word;
		}

		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		String current_similar_word = "";
		int current_lowest = levenshtein(word, dictionary[0]);
		for (int i = 1; i < 3000; i++) {
			int similarity = levenshtein(word, dictionary[i]);
			if (similarity < current_lowest) {
				current_lowest = similarity; 
				current_similar_word = dictionary[i];
			}
		}
		if (current_lowest <= threshold) {
			return current_similar_word;
		}
		return word;
	}

}
