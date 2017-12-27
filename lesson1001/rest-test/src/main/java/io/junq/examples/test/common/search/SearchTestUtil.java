package io.junq.examples.test.common.search;

import org.apache.commons.lang3.tuple.Triple;

import io.junq.examples.client.util.SearchUriBuilder;
import io.junq.examples.common.search.ClientOperation;
import io.junq.examples.common.util.SearchField;

public final class SearchTestUtil {
	
    private SearchTestUtil() {
        throw new UnsupportedOperationException();
    }

    // API

    public static String constructQueryString(final String idVal, final String nameVal) {
        return new SearchUriBuilder().consume(ClientOperation.EQ, SearchField.id.toString(), idVal, false).consume(ClientOperation.EQ, SearchField.name.toString(), nameVal, false).build();
    }

    public static String constructQueryString(final String idVal, final boolean negatedId, final String nameVal, final boolean negatedName) {
        return new SearchUriBuilder().consume(ClientOperation.EQ, SearchField.id.toString(), idVal, negatedId).consume(ClientOperation.EQ, SearchField.name.toString(), nameVal, negatedName).build();
    }

    public static String constructQueryString(final Triple<String, ClientOperation, String> idConstraint, final Triple<String, ClientOperation, String> nameConstraint) {
        return new SearchUriBuilder().consume(idConstraint).consume(nameConstraint).build();
    }
}
