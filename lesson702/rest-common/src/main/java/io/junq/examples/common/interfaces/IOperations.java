package io.junq.examples.common.interfaces;

import java.io.Serializable;
import java.util.List;

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
}
