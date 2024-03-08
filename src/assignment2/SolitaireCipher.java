package assignment2;

public class SolitaireCipher {
	public Deck key;

	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}

	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		/**** ADD CODE HERE ****/
		int[] keyStream = new int[size];
		for (int i = 0; i < size; i++){
			keyStream[i] = key.generateNextKeystreamValue();
		}
		return keyStream;
	}

	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		/**** ADD CODE HERE ****/
		msg = msg.toUpperCase();
		String tmp = "";
		for (int i = 0; i < msg.length(); i++){
			if (msg.charAt(i) >= 'A' && msg.charAt(i) <= 'Z'){
				tmp = tmp + msg.charAt(i);
			}
		}
		int[] keyStream = getKeystream(tmp.length());
		String encoded = "";
		for (int i = 0; i < tmp.length(); i++){
			char tmpChar = tmp.charAt(i);
			int position = tmpChar - 'A';
			position += keyStream[i];
			position %= 26;
			tmpChar = (char) ('A' + position);
			encoded = encoded + tmpChar;
		}

		return encoded;
	}

	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		/**** ADD CODE HERE ****/
		int[] keyStream = getKeystream(msg.length());
		String decoded = "";
		for (int i = 0; i < msg.length(); i++){
			int shift = keyStream[i];
			shift = shift % 26;
			shift = 26 - shift;
			char tmpChar = msg.charAt(i);
			int position = tmpChar - 'A';
			position += shift;
			position %= 26;
			tmpChar = (char) ('A' + position);
			decoded = decoded + tmpChar;
		}
		return decoded;
	}

}
