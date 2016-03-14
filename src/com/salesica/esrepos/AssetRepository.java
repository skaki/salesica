package com.salesica.esrepos;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.salesica.elastic.AssetEntity;

public interface AssetRepository extends ElasticsearchRepository<AssetEntity,String> {
}
