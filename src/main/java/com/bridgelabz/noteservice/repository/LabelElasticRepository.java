package com.bridgelabz.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.noteservice.model.Label;

/**
 * Purpose : LabelElasticRepository interface which extends ElasticsearchRepository to store 
 *           the Label details into Elasticsearch Database.
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    31/07/2018
 */
public interface LabelElasticRepository extends ElasticsearchRepository<Label,String> {
	public Optional<Label> findLabelByLabelName(String labelName);
	public List<Label> findByUserId(String userId);
}
