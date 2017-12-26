package io.junq.examples.common.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;

import io.junq.examples.common.interfaces.IOperations;
import io.junq.examples.common.persistence.model.IEntity;
import io.junq.examples.common.search.ClientOperation;

public interface IRawService<T extends IEntity> extends IOperations<T> {
	
    // search

    List<T> searchAll(final String queryString);

    List<T> searchPaginated(final String queryString, final int page, final int size);

    Page<T> searchPaginated(final int page, final int size, final Triple<String, ClientOperation, String>... constraints);
	
    Page<T> findAllPaginatedAndSortedRaw(final int page, final int size, final String sortBy, final String sortOrder);
    Page<T> findAllPaginatedRaw(final int page, final int size);

}
