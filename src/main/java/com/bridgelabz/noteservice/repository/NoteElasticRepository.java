package com.bridgelabz.noteservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.noteservice.model.Notes;
/**
 * Purpose : NoteElasticRepository interface which extends ElasticsearchRepository to store 
 *           the Note into Elasticsearch Database.
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    31/07/2018
 */
public interface NoteElasticRepository extends ElasticsearchRepository<Notes, String>{
	public List<Notes> findByUserId(String userId);
}
