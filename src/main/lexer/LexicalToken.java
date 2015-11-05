package main.lexer;

public class LexicalToken {
	
	private String tag;
	
	private String wordRead;
	
	public LexicalToken(String tag, String wordRead) {
		this.tag = tag;
		this.wordRead = wordRead;
	}
	
	public String getTag() {
		return tag;
	}
	
	public String getWord() {
		return wordRead;
	}

}
