package com.salesica.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesica.elastic.AssetEntity;
import com.salesica.esrepos.AssetRepository;

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
@RequestMapping(value = "/v1/assets")
public class Assets {
 
	@Autowired
    private AssetRepository repository;
	
	@Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

	final Logger log = Logger.getLogger(Assets.class.getName());
	
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AssetEntity>> findbyTag(@RequestParam(value = "tags", required = true) String tags ) throws JSONException, IOException {   	  	    	
    	
    	String[] tokens = tags.split(",");
    	
    	BoolQueryBuilder qb = QueryBuilders.boolQuery();  			
    	for (String s : tokens) {
    		qb.must(QueryBuilders.matchQuery("tags", s.trim()));
    	}
    	NativeSearchQuery nativeSearch = new NativeSearchQuery(qb);   			
    	Page<AssetEntity> results = elasticsearchTemplate.queryForPage(nativeSearch, AssetEntity.class);
        return new ResponseEntity<Page<AssetEntity>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/{docid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetEntity> findbyId(@PathVariable("docid") String docid ) throws JSONException, IOException {   	  	    	
    	GetQuery getQuery = new GetQuery();
    	getQuery.setId(docid);
    	AssetEntity results = elasticsearchTemplate.queryForObject(getQuery, AssetEntity.class);
        return new ResponseEntity<AssetEntity>(results, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createasset(@RequestBody AssetEntity asset, UriComponentsBuilder ucBuilder) throws JSONException, IOException {
        repository.save(asset);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(asset.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{docid}", method = RequestMethod.PUT)
    public ResponseEntity<AssetEntity> updateAsset(@PathVariable("docid") String docid, @RequestBody AssetEntity asset) {
    	asset.setId(docid);    	
        repository.save(asset);
        return new ResponseEntity<AssetEntity>(asset, HttpStatus.OK);

    }

    @RequestMapping(value = "/{docid}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAsset(@PathVariable("docid") String docid) {
    	
    	AssetEntity asset = new AssetEntity();
    	
    	asset.setId(docid);    	
        repository.delete(asset);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

}