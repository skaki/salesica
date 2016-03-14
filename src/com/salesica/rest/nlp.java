package com.salesica.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesica.elastic.AssetEntity;
import com.salesica.esrepos.AssetRepository;
import com.salesica.extractors.pdf;
import com.salesica.models.NlsModel;
import com.salesica.textanalytics.Concepts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONException;

@RestController
@RequestMapping(value = "/v1/nlp")
public class nlp {
	
	final Logger log = Logger.getLogger(nlp.class.getName());
    @RequestMapping(value = "/concepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> findConcepts(@RequestParam(value = "query", required = true) String query ) throws JSONException, IOException {   	  	    	
    	ObjectMapper mapper = new ObjectMapper();
    	NlsModel qbe = mapper.readValue(query, NlsModel.class);
    	
    	String type = qbe.getType();    	
    	
    	List<String> cons = null;
    	
    	if ( type.matches("text.doc")) 
    		cons = Concepts.findConceptsInText(qbe.getSubject());
    	else if ( type.matches("url.html"))
    		cons = Concepts.findConceptsInUrl(qbe.getSubject());
        else if ( type.matches("url.pdf"))
    		cons = Concepts.findConceptsInUrl(pdf.fromUrl(qbe.getSubject()));
    	
        return new ResponseEntity<List<String>>(cons, HttpStatus.OK);
 
    }
    
    @RequestMapping(value = "/summarize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getSummary(@RequestParam(value = "query", required = true) String query ) throws JSONException, IOException {   	  	    	
    	ObjectMapper mapper = new ObjectMapper();
    	NlsModel qbe = mapper.readValue(query, NlsModel.class);
    	String type = qbe.getType();    	

    	List<String> cons = null;
    	
    	if ( type.matches("text.doc")) 
    		cons = Concepts.SummarizeDoc("Title", qbe.getSubject());
    	else if ( type.matches("url.html"))
    		cons = Concepts.SummarizeUrl(qbe.getSubject());
        else if ( type.matches("url.pdf"))
    		cons = Concepts.SummarizeDoc("Title", pdf.fromUrl(qbe.getSubject()));
    	
        return new ResponseEntity<List<String>>(cons, HttpStatus.OK);

    }
}