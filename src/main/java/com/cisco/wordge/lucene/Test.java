package com.cisco.wordge.lucene;

import com.google.gson.Gson;

public class Test {
	public static void main (String [] args) throws Exception{
		Segmentation s= new Segmentation();
		Gson gson = new Gson();
		String json = gson.toJson(s.parseDoc());
		System.out.println(json);
	}
}
