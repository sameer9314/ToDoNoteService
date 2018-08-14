package com.bridgelabz.sequence.dao;

import com.bridgelabz.sequence.exception.SequenceException;

public interface SequenceDao {

	String getNextSequenceId(String key) throws SequenceException;

}