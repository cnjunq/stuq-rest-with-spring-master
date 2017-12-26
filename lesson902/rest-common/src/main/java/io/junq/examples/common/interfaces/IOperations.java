package io.junq.examples.common.interfaces;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import io.junq.examples.common.search.ClientOperation;

public interface IOperations<T extends Serializable> {

	T findOne(final long id);

	List<T> findAll();

	List<T> findAllSorted(final String sortBy, final String sortOrder);

	List<T> findAllPaginated(final int page, final int size);

	List<T> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);

	T create(final T resource);

	void update(final T resource);

	void delete(final long id);

	void deleteAll();

	long count();
	
    // search

    List<T> searchAll(final Triple<String, ClientOperation, String>... constraints);

    T searchOne(final Triple<String, ClientOperation, String>... constraints);
}
