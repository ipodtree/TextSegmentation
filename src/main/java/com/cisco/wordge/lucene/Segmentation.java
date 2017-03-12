package com.cisco.wordge.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Segmentation {

	private Analyzer analyzer = new StandardAnalyzer();

	public Map<String, String> parseDoc() throws Exception {
		Map result = new HashMap<String, String>();
		File dir = new File("./sampleDoc");

		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		

		for (int i = 0; i < files.length; i++) {
			Map docMap = new HashMap <String, Map<String, Map<String, String>>>();
			Map termMap = new HashMap <String, Map<String, String>> ();
			docMap.put("doc"+String.valueOf(i+1), termMap);


			List termList = new ArrayList<String>();
			String text = readDoc(files[i].getAbsolutePath());
			TokenStream tokenStream = analyzer.tokenStream("", text);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
				termList.add(charTermAttribute.toString());
			}
			for (int j = 0; j < termList.size(); j++) {
				String term = (String) termList.get(j);
				Map termContent = new HashMap <String, String>();

				// if the term appears before
				if (termMap.containsKey(term)) {
					termContent = (HashMap<String, String>) termMap.get(term);
					int oldNumber = Integer.parseInt((String) termContent.get("count"));
					termContent.replace("count", String.valueOf(oldNumber + 1));
					termContent.replace("frequency", String.valueOf((double)(oldNumber + 1) / termList.size()));
				}

				// if it is a new term
				else {
					termContent.put("count", String.valueOf(1));
					termContent.put("frequency", String.valueOf((double)1 / termList.size()));
				}
				termMap.put(term, termContent); //update term in doc
			}
			result.put("Term_Summary", docMap);
		}
		return result;
	}

	public void generateDoc() {

	}

	private String readDoc(String filePath) throws Exception {
		String everything = "";
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		return everything;
	}
}
