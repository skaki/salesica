package com.salesica.textanalytics;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.ConceptsParams;
import com.aylien.textapi.parameters.ExtractParams;
import com.aylien.textapi.parameters.SummarizeParams;
import com.aylien.textapi.responses.Article;
import com.aylien.textapi.responses.Concept;
import com.aylien.textapi.responses.Summarize;
import com.aylien.textapi.responses.SurfaceForm;

public class Concepts {
	static final String AYLIEN_APP_ID = "ce2409de";
	static final String AYLIEN_APP_KEY = "f2eef1eeecdce04d34efdf43e080b1c6";
	static final TextAPIClient client = new TextAPIClient(AYLIEN_APP_ID, AYLIEN_APP_KEY);
	
	public static List<String> findConceptsInText(String doc) {
		
		List<String> conc = new ArrayList<String>();
		
		ConceptsParams.Builder builder = ConceptsParams.newBuilder();
		builder.setText(doc);
		com.aylien.textapi.responses.Concepts concepts;
		try {
			concepts = client.concepts(builder.build());
			for (Concept concept: concepts.getConcepts()) {
				
				SurfaceForm sfs[] = concept.getSurfaceForms();
				
				for (int i = 0; i < sfs.length; i++)
					conc.add(sfs[i].getString());
				}
			
		} catch (TextAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conc;
	}
	
    public static List<String> findConceptsInUrl(String url) {
		List<String> conc = new ArrayList<String>();
		Article a = getArticleFromUrl(url);
		
		if ( a != null )
			return findConceptsInText(a.getArticle());
		else return null;
	}
	
    public static Article getArticleFromUrl(String Url) {
    	ExtractParams.Builder builder = ExtractParams.newBuilder();
		java.net.URL url;
		try {
			url = new java.net.URL(Url);
			builder.setUrl(url);
			builder.setBestImage(true);
			Article extract = client.extract(builder.build());
			return extract;
		} catch (MalformedURLException | TextAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    public static List<String> SummarizeUrl(String Url)
    {
    	List<String> sums = new ArrayList<String>();
    	
    	try {
  	  		SummarizeParams.Builder builder = SummarizeParams.newBuilder();
  			java.net.URL url = new java.net.URL(Url);
			builder.setUrl(url);
			builder.setNumberOfSentences(3);
			Summarize summary;
			summary = client.summarize(builder.build());
	    	for (String sentence: summary.getSentences()) {
	      	  sums.add(sentence);	
	      	}
		} catch (TextAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sums;
    }

    public static List<String> SummarizeDoc(String title, String doc)
    {
    	List<String> sums = new ArrayList<String>();
    	
    	try {
  	  		SummarizeParams.Builder builder = SummarizeParams.newBuilder();
  	  		builder.setTitle(title);
  	  		builder.setText(doc);
			builder.setNumberOfSentences(3);
			Summarize summary;
			summary = client.summarize(builder.build());
	    	for (String sentence: summary.getSentences()) {
	      	  sums.add(sentence);	
	      	}
		} catch (TextAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sums;
    }

}
