package main.fileio;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import main.parser.grammar.ParseTree;

public class ParseTreeWriter {

	public static void writeTree(ParseTree tree, String string) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(string, "UTF-8");
		pw.write(tree.toString());
		pw.close();
	}

}
