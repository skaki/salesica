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

@Document(indexName = "assets", type = "asset", shards = 1, replicas = 0, refreshInterval = "-1")
public class AssetEntity {

    @Id
    private String id;

    @Field(type = String, store = true)
    private String title;
    
    @Field(type = String, store = true)
    private String uri;
    
    @Field(type = String, store = true)
    private String description;
    
    @Field(type = String, store = true)
    private String summary;
/*    
    @MultiField(
            mainField = @Field(type = String, index = analyzed),
            otherFields = {
                    @NestedField(dotSuffix = "untouched", type = String, store = true, index = not_analyzed),
                    @NestedField(dotSuffix = "sort", type = String, store = true, indexAnalyzer = "keyword")
            }
    )
    private List<String> authors = new ArrayList<String>();
*/
    @Field(type = String, store = true, index = not_analyzed)
    private Collection<String> tags = new ArrayList<String>();

    @Field(type = Integer, store = true)
    private int score;

    public AssetEntity() {

    }

    public AssetEntity(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
/*
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
*/
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