package com.bridgelabz.noteservice.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * Purpose : POZO class for Label.
 *  	
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    24/07/208
 */
@Document(indexName="labeldatabase",type="label")
public class Label implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ApiModelProperty(hidden=true)
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String labelName;
	
	@ApiModelProperty(hidden=true)
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	@Override
	public String toString() {
		return "Label [id=" + id + ", labelName=" + labelName + ", userId=" + userId + "]";
	}
	
}
