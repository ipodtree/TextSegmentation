package com.cisco.wordge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cisco.wordge.lucene.*;
import com.google.gson.Gson;

@Controller
public class HomeController {
	
	@RequestMapping(value= "/home", method=RequestMethod.GET)
	public String Home(){
		return "home";
	}
	
	@RequestMapping(value="/doc", method=RequestMethod.GET)
	public String Segementation(Model m) throws Exception{
		Segmentation s= new Segmentation();
		Gson gson = new Gson();
		String json = gson.toJson(s.parseDoc());
		m.addAttribute("result", json);
		return "segmentation";
	}

}
