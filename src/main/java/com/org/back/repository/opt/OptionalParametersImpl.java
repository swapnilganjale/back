package com.org.back.repository.opt;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.back.dto.PageDTO;
import com.org.back.dto.PagingAndSortingRequest;

@Component
@Transactional
public class OptionalParametersImpl<T> implements OptionalParameters<T> {

	@Autowired
	private WebApplicationContext appContext;

	@Autowired
	EntityManager entityManager;

	private static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			if (l > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			} else {
				throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
			}
		}
		return (int) l;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public PageDTO<T> findWithOptionalParameters(PagingAndSortingRequest request, Class<T> type) {
		JpaRepository<T, Long> repository = getRepository(type);
		if (request == null) {
			request = new PagingAndSortingRequest();
		}
		if (request.getPage() != null) {
			request.setPage(request.getPage() - 1);
		}
		long total = repository.count();
		long page = 0;
		long limit = 1;
		if (request.getPage() == null && request.getLimit() == null) {
			if (total > 0) {
				limit = total;
			}
		}
		if (request.getPage() != null && request.getLimit() != null) {
			if (request.getPage() >= 0) {
				page = request.getPage();
			}
			if (request.getLimit() >= 1) {
				limit = request.getLimit();
			}
		}
		if (request.getPage() == null && request.getLimit() != null) {
			if (request.getLimit() >= 1) {
				limit = request.getLimit();
			}
		}
		if (request.getPage() != null && request.getLimit() == null) {
			limit = total;
		}
		Sort.Direction direction = Sort.Direction.ASC;
		try {
			direction = Sort.Direction.fromString(request.getSortType());
		} catch (Exception ex) {
		}
		PageRequest pageRequest = null;
		if (request.getSortBy() == null || request.getSortBy().isEmpty()) {
			pageRequest = new PageRequest(safeLongToInt(page), safeLongToInt(limit));
		} else {
			pageRequest = new PageRequest(safeLongToInt(page), safeLongToInt(limit),
					new Sort(direction, request.getSortBy()));
		}

		if (request.getFields().isEmpty()) {
			System.out.println("************ Normal way");
			Page<T> pageobj = repository.findAll(pageRequest);
			System.err.println(pageobj);

			return new PageDTO<>(pageobj.getContent(), pageobj.getNumber(), pageobj.getNumberOfElements(),
					pageobj.getSize(), pageobj.getTotalElements(), pageobj.getTotalPages());

		} else {
			ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING);
			Example<T> example = Example.of(convertValue(request.getFields(), type), matcher);

			// filtering not working in spring boot 2.0.2 remove "example" from
			// findAll()
			System.out.println("*************");

			System.out.println(repository.findAll(pageRequest).getContent().size());
			System.out.println(repository.findAll(example, pageRequest).getContent().size());

			System.out.println("*************");

			Page<T> pageobj = repository.findAll(example, pageRequest);

		}
		return null;

	}

	@SuppressWarnings("unchecked")
	private JpaRepository<T, Long> getRepository(Class<T> type) {
		Repositories repositories = new Repositories(appContext);
		return (JpaRepository<T, Long>) repositories.getRepositoryFor(type).get();

	}

	private T convertValue(Map<String, Object> map, Class<T> type) {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(map, type);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public PageDTO<T> findWithSearchParameters(PagingAndSortingRequest pagingAndSortingRequest, Class<T> type) {
		System.out.println("findWithSearchParameters");

		if (pagingAndSortingRequest.getFields().isEmpty()) {
			return findWithOptionalParameters(pagingAndSortingRequest, type);
		}

		String query = createCustomQuery(pagingAndSortingRequest.getFields(), type);
		Query newQuery = entityManager.createQuery(query);

		Query countQuery = setQuery(pagingAndSortingRequest.getFields(), type, newQuery);
		int count = countQuery.getResultList().size();

 
		List<T> list = setQuery(pagingAndSortingRequest.getFields(), type, countQuery)
				.setMaxResults(pagingAndSortingRequest.getLimit())
				.setFirstResult(pagingAndSortingRequest.getPage() * pagingAndSortingRequest.getLimit()).getResultList();

		System.out.println("count *********" + count);
		System.out.println("*********filter way" + newQuery.getResultList());

		PageDTO<T> pageDto = new PageDTO<>();

		pageDto.setContent(list);
		pageDto.setSize(count);
		pageDto.setTotalElements(count);

		return pageDto;

	}

	private String createCustomQuery(HashMap<String, Object> map, Class<T> type) {

		StringBuffer buffer = new StringBuffer();

		buffer.append("SELECT obj from " + type.getSimpleName() + " obj where ");

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {

			if (map.get("type").equals("number") && field.getType().getSimpleName().equals("int")) {
				buffer.append(" obj." + field.getName() + "=:" + field.getName() + " OR");
			}

			if (map.get("type").equals("String") && field.getType().getSimpleName().equals("String")) {
				buffer.append(" obj." + field.getName() + " LIKE CONCAT('%',:" + field.getName() + ",'%') OR");
			}
		}

		String removeBuffer = buffer.substring(0, buffer.lastIndexOf("OR"));

		return removeBuffer;
	}

	private Query setQuery(HashMap<String, Object> map, Class<T> type, Query query) {

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {

			if (map.get("type").equals("number") && field.getType().getSimpleName().equals("int")) {
				query.setParameter(field.getName(), Integer.parseInt((String) map.get("search")));
			}

			if (map.get("type").equals("String") && field.getType().getSimpleName().equals("String")) {
				query.setParameter(field.getName(), map.get("search"));

			}
		}

		return query;
	}

}