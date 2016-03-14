package com.salesica.esrepos;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.salesica.elastic.CrawlEntity;

public interface CrawlRepository extends ElasticsearchRepository<CrawlEntity,String> {
}
