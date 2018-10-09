package com.org.back.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Data Transfer Object for the Page object. This object keeps track of all the return 
 * data needed to page through a list of data.
 * 
 * @author Swap
 *
 * @param <T>
 */
public class PageDTO<T> implements Serializable{
	private static final long serialVersionUID = 7983855213393435630L;
	private List<T> content = new ArrayList<T>();
    private int number;
    private int numberOfElements;
    private int size;
    private long totalElements;
    private int totalPages;
 
 
	/**
     * Constructor for the PageDTO
     * 
     * @param content - List object of type T used to hold the return data 
     * @param number
     * @param numberOfElements - total number of elements returned
     * @param size
     * @param totalElements
     * @param totalPages - total number of pages for this list
     */
    
    
    
    public PageDTO(List<T> content, int number, int numberOfElements, int size, long totalElements, int totalPages) {
        super();

        this.content = content;
        this.number = number + 1;
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public PageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<T> getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

	public void setContent(List<T> content) {
		this.content = content;
	}

	
	
	public void setNumber(int number) {
		this.number = number;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "PageDTO [content=" + content + ", number=" + number
				+ ", numberOfElements=" + numberOfElements + ", size=" + size
				+ ", totalElements=" + totalElements + ", totalPages="
				+ totalPages + "]";
	}


}