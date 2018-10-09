package com.org.back.repository.opt;

import com.org.back.dto.PageDTO;
import com.org.back.dto.PagingAndSortingRequest;


public interface OptionalParameters<T> {

	public PageDTO<T> findWithOptionalParameters(PagingAndSortingRequest request, Class<T> type);

	public PageDTO<T> findWithSearchParameters(PagingAndSortingRequest pagingAndSortingRequest, Class<T> type);
}
