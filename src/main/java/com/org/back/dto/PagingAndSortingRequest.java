package com.org.back.dto;

import java.io.Serializable;
import java.util.HashMap;

 
public class PagingAndSortingRequest implements Serializable {
	private static final long serialVersionUID = 9093686014222014790L;

	private Integer page;

	private Integer limit;

	private String sortBy;

	private String sortType;// "asc" or "desc"

	private HashMap<String, Object> fields = new HashMap<String, Object>();

	public PagingAndSortingRequest() {
	}

	
	
	
	public PagingAndSortingRequest(Integer page, Integer limit, String sortBy, String sortType,
			HashMap<String, Object> fields) {
		super();
		this.page = page;
		this.limit = limit;
		this.sortBy = sortBy;
		this.sortType = sortType;
		this.fields = fields;
	}




	public HashMap<String, Object> getFields() {
		return fields;
	}

	public void setFields(HashMap<String, Object> fields) {
		this.fields = fields;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		return sortType;
	}

	@Override
	public String toString() {
		return "PagingAndSortingRequest [page=" + page + ", limit=" + limit + ", sortBy=" + sortBy + ", sortType="
				+ sortType + ", fields=" + fields + "]";
	}

}
