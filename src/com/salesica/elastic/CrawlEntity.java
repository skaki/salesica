package com.salesica.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.NestedField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldIndex.analyzed;
import static org.springframework.data.elasticsearch.annotations.FieldIndex.not_analyzed;
import static org.springframework.data.elasticsearch.annotations.FieldType.Integer;
import static org.springframework.data.elasticsearch.annotations.FieldType.String;

@Document(indexName = "crawl", type = "url", shards = 1, replicas = 0, refreshInterval = "-1")
public class CrawlEntity {

    @Id
    private String id;
    
    @Field(type = String, store = true)
    private String uri;
        
    @Field(type = String, store = true)
    private String summary;

    @Field(type = String, store = true, index = not_analyzed)
    private Collection<String> tags = new ArrayList<String>();

    @Field(type = Integer, store = true)
    private int score;

    public CrawlEntity() {

    }

    public CrawlEntity(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
}