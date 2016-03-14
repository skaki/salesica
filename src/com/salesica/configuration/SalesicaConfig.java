package com.salesica.configuration;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.salesica")
@EnableElasticsearchRepositories(basePackages = "com.salesica.esrepos") 
public class SalesicaConfig {
	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
	            return new ElasticsearchTemplate(nodeBuilder().clusterName("elasticsearch_skaki").node().client());	            
	}
}


