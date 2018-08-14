package com.bridgelabz.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.noteservice.model.Label;


/**
 * Purpose : LabelRepository interface which extends MongoRepository to store 
 *           the Label details into Mongo Database.
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    24/07/2018
 */
public interface LabelRepository extends MongoRepository<Label,String>{
	public Optional<Label> findLabelByLabelName(String labelName);
	public List<Label> findByUserId(String userId);
}
