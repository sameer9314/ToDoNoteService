package com.bridgelabz.Utility;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  

public class JsoupImpl {
	
	 public static String image(String foundUrl) throws IOException{  
         Document doc = Jsoup.connect(foundUrl).get();  
         Element image = doc.select("img").first();
         String url = image.attr("abs:src");
         
         return url;
	 }
      public static String getLinkTitle(String foundUrl) throws IOException {
    	  Document doc = Jsoup.connect(foundUrl).get();  
    	  String title=doc.title();
    	  
    	  return title;
      }   
      
      public static String getDomain(String foundUrl) throws IOException {
    	  URL url=new URL(foundUrl);
    	  return  url.getHost();
      } 
      
}
