package com.salesica.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesica.crawler.SalesicaCrawlController;
import com.salesica.elastic.CrawlEntity;
import com.salesica.esrepos.CrawlRepository;

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

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONException;

@RestController
@RequestMapping(value = "/v1/crawls")
public class Crawls {
 
	@Autowired
    private CrawlRepository repository;
	
	@Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

	final Logger log = Logger.getLogger(Crawls.class.getName());
	static final String  crawlStorageFolder = "/tmp";
	static final int numberOfCrawlers = 10;
	
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CrawlEntity>> findbyTag(@RequestParam(value = "query", required = true) String query ) throws JSONException, IOException {   	  	    	
    	ObjectMapper mapper = new ObjectMapper();
    	CrawlEntity qbe = mapper.readValue(query, CrawlEntity.class);
    	BoolQueryBuilder qb = QueryBuilders.boolQuery();  			
    	for (String s : qbe.getTags()) {
    		qb.must(QueryBuilders.matchQuery("tags", s));
    	}
    	NativeSearchQuery nativeSearch = new NativeSearchQuery(qb);   			
    	Page<CrawlEntity> results = elasticsearchTemplate.queryForPage(nativeSearch, CrawlEntity.class);
        return new ResponseEntity<Page<CrawlEntity>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/{docid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrawlEntity> findbyId(@PathVariable("docid") String docid ) throws JSONException, IOException {   	  	    	
    	GetQuery getQuery = new GetQuery();
    	getQuery.setId(docid);
    	CrawlEntity results = elasticsearchTemplate.queryForObject(getQuery, CrawlEntity.class);
        return new ResponseEntity<CrawlEntity>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUrl(@RequestBody CrawlEntity url, UriComponentsBuilder ucBuilder) throws JSONException, IOException {
        
    	
    	repository.save(url);
        
        try {
        	SalesicaCrawlController.crawl(url.getUri(), 0, crawlStorageFolder, numberOfCrawlers);
		} catch (Exception e) {
			log.debug(e);
		}	
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/crawls/{id}").buildAndExpand(url.getId()).toUri());
        
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{docid}", method = RequestMethod.PUT)
    public ResponseEntity<CrawlEntity> updateUrl(@PathVariable("docid") String docid, @RequestBody CrawlEntity url) {
    	url.setId(docid);    	
        repository.save(url);
        return new ResponseEntity<CrawlEntity>(url, HttpStatus.OK);

    }

    @RequestMapping(value = "/{docid}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUrl(@PathVariable("docid") String docid) {
    	
    	CrawlEntity url = new CrawlEntity();
    	
    	url.setId(docid);    	
        repository.delete(url);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

}