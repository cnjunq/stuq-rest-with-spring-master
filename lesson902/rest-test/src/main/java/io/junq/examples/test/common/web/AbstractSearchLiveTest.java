package io.junq.examples.test.common.web;

import static io.junq.examples.common.search.ClientOperation.CONTAINS;
import static io.junq.examples.common.search.ClientOperation.EQ;
import static io.junq.examples.common.search.ClientOperation.NEG_EQ;
import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import io.junq.examples.client.IDtoOperations;
import io.junq.examples.common.interfaces.INameableDto;
import io.junq.examples.common.search.ClientOperation;
import io.junq.examples.common.util.SearchField;
import io.junq.examples.test.common.client.template.IRestClient;
import io.junq.examples.test.common.test.contract.ISearchTest;
import io.junq.examples.test.common.util.IDUtil;
import io.junq.examples.test.common.util.SearchIntegrationTestUtil;
import io.junq.examples.test.common.web.util.ClientConstraintsUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SuppressWarnings("unchecked")
@ActiveProfiles({ CLIENT, TEST })
public abstract class AbstractSearchLiveTest<T extends INameableDto> extends AbstractSearchReadOnlyLiveTest<T> implements ISearchTest {
	
	public AbstractSearchLiveTest() {
        super();
    }

    // tests

    @Override
    @Test
    public final void whenSearchByNameIsPerformed_thenNoExceptions() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        getApi().searchAll(nameConstraint);
    }

    // by id

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenNoExceptions() {
        final T existingResource = getApi().create(createNewResource());
        getApi().searchAsResponse(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), null);
    }

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenSearchOperationIsSuccessful() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final Response searchResponse = getApi().searchAsResponse(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), null);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceWithIdExists_whenResourceIsSearchedById_thenResourceIsFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    // find one - by attributes
    // note: kept as the same tests from AbstractLogicClientRestLiveTest are
    // still ignored (bug in RestTemplate)

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        getApi().searchOne(nameConstraint);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenResourceIsFound() {
        // Given
        final T existingResource = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        final T resourceByName = getApi().searchOne(nameConstraint);

        // Then
        assertNotNull(resourceByName);
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNameAttribute_thenFoundResourceIsCorrect() {
        // Given
        final T existingResource = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, existingResource.getName());
        final T resourceByName = getApi().searchOne(nameConstraint);

        // Then
        assertThat(existingResource, equalTo(resourceByName));
    }

    @Test
    /**/public final void givenResourceExists_whenResourceIsSearchedByNagatedNameAttribute_thenNoExceptions() {
        // Given
        final T existingResource = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), NEG_EQ, existingResource.getName());
        getApi().searchAll(nameConstraint);

        // Then
    }

    // by name

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenNoExceptions() {
        final T existingResource = getApi().create(createNewResource());
        getApi().searchAsResponse(null, ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenOperationIsSuccessful() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final Response searchResponse = getApi().searchAsResponse(null, ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByName_thenResourceIsFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByNegatedNameIsPerformed_thenResourcesAreCorrect() {
        final T existingResource1 = getApi().create(createNewResource());
        final T existingResource2 = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource1.getName());
        final List<T> searchResults = getApi().searchAll(nameConstraint);

        // Then
        assertThat(searchResults, hasItem(existingResource1));
        assertThat(searchResults, not(hasItem(existingResource2)));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByNameLowerCase_thenResourceIsFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName().toLowerCase()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    // by name - contains

    @Override
    @Test
    public final void givenResourceWithNameExists_whenResourceIsSearchedByContainsExactName_thenNoExceptions() {
        final T existingResource = getApi().create(createNewResource());
        getApi().searchAsResponse(null, ClientConstraintsUtil.createNameConstraint(CONTAINS, existingResource.getName()));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByContainsEntireNameIsPerformed_thenResourceIsFound() {
        final T existingEntity = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), CONTAINS, existingEntity.getName());
        final List<T> searchResults = getApi().searchAll(nameConstraint);

        // Then
        assertThat(searchResults, hasItem(existingEntity));
    }

    @Override
    @Test
    public final void givenResourceWithNameExists_whenSearchByContainsPartOfNameIsPerformed_thenResourceIsFound() {
        final T existingEntity = getApi().create(createNewResource());
        final String name = existingEntity.getName();
        final String partOfName = name.substring(2);

        // When
        final ImmutableTriple<String, ClientOperation, String> nameContainsConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), CONTAINS, partOfName);
        final List<T> searchResults = getApi().searchAll(nameContainsConstraint);

        // Then
        assertThat(searchResults, hasItem(existingEntity));
    }

    // starts with, ends with

    @Override
    @Test
    public final void givenResourceExists_whenSearchByStartsWithEntireNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewResource();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithEntireKeyIsPerformed_thenResourceIsFound(getApi(), newEntity, SearchField.name, ClientOperation.STARTS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByStartsWithPartOfNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewResource();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithPartOfKeyIsPerformed_thenResourceIsFound(getApi(), newEntity, SearchField.name, ClientOperation.STARTS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByEndsWithEntireNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewResource();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithEntireKeyIsPerformed_thenResourceIsFound(getApi(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Override
    @Test
    public final void givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewResource();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByEndsWithPartOfNameIsPerformed_thenResourceIsFound(getApi(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    @Test
    public final void givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound() {
        final T newEntity = createNewResource();
        SearchIntegrationTestUtil.givenResourceExists_whenSearchByStartsWithPartOfLowerCaseNameIsPerformed_thenResourceIsFound(getApi(), newEntity, SearchField.name, ClientOperation.ENDS_WITH, newEntity.getName());
    }

    // by id and name

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenOperationIsSuccessful() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final Response searchResponse = getApi().searchAsResponse(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndCorrectName_thenResourceIsFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(found, hasItem(existingResource));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByCorrectIdAndIncorrectName_thenResourceIsNotFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(EQ, existingResource.getId()), ClientConstraintsUtil.createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndCorrectName_thenResourceIsNotFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(EQ, IDUtil.randomPositiveLong()), ClientConstraintsUtil.createNameConstraint(EQ, existingResource.getName()));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourceWithNameAndIdExists_whenResourceIsSearchedByIncorrectIdAndIncorrectName_thenResourceIsNotFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(EQ, IDUtil.randomPositiveLong()), ClientConstraintsUtil.createNameConstraint(EQ, randomAlphabetic(8)));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    // by negated id, name

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedName_thenOperationIsSuccessful() {
        final T existingResource = getApi().create(createNewResource());

        final Triple<String, ClientOperation, String> negatedNameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource.getName());

        // When
        final Response searchResponse = getApi().searchAsResponse(null, negatedNameConstraint);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedId_thenOperationIsSuccessful() {
        final T existingResource = getApi().create(createNewResource());

        final Triple<String, ClientOperation, String> negatedIdConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource.getId().toString());

        // When
        final Response searchResponse = getApi().searchAsResponse(negatedIdConstraint, null);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Override
    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNegatedId_thenResourceIsNotFound() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(NEG_EQ, existingResource.getId()));

        // Then
        assertThat(found, not(hasItem(existingResource)));
    }

    @Override
    @Test
    public final void givenResourcesExists_whenResourceIsSearchedByNegatedId_thenTheOtherResourcesAreFound() {
        final T existingResource1 = getApi().create(createNewResource());
        final T existingResource2 = getApi().create(createNewResource());

        // When
        final List<T> found = getApi().searchAll(ClientConstraintsUtil.createIdConstraint(NEG_EQ, existingResource1.getId()));

        // Then
        assertThat(found, hasItem(existingResource2));
    }

    @Override
    @Test
    public final void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedName_thenResourcesAreFound() {
        final T existingResource1 = getApi().create(createNewResource());
        final T existingResource2 = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource1.getName());
        final List<T> searchResults = getApi().searchAll(nameConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource1)));
        assertThat(searchResults, hasItem(existingResource2));
    }

    @Override
    @Test
    public final void givenResourceAndOtherResourcesExists_whenResourceIsSearchedByNegatedId_thenResourcesAreFound() {
        final T existingResource1 = getApi().create(createNewResource());
        final T existingResource2 = getApi().create(createNewResource());

        // When
        final ImmutableTriple<String, ClientOperation, String> idConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.id.toString(), NEG_EQ, existingResource1.getId().toString());
        final List<T> searchResults = getApi().searchAll(idConstraint);

        // Then
        assertThat(searchResults, not(hasItem(existingResource1)));
        assertThat(searchResults, hasItem(existingResource2));
    }

    // with paging

    @Test
    public final void givenResourceExists_whenResourceIsSearchedByNameWithPaging_then200IsReceived() {
        final T existingResource = getApi().create(createNewResource());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), EQ, existingResource.getName());
        final Response searchResponse = getApi().searchAsResponse(null, nameConstraint, 0, 2);

        // Then
        assertThat(searchResponse.getStatusCode(), is(200));
    }

    @Test
    public final void givenResourcesExists_whenResourceIsSearchedByNameWithPagingOfSize2_thenMax2ResourcesAreReceived() {
        final T existingResource1 = getApi().create(createNewResource());
        getApi().create(createNewResource());
        getApi().create(createNewResource());
        getApi().create(createNewResource());

        // When
        final Triple<String, ClientOperation, String> nameConstraint = new ImmutableTriple<String, ClientOperation, String>(SearchField.name.toString(), NEG_EQ, existingResource1.getName());
        final List<T> searchResults = getApi().searchPaginated(null, nameConstraint, 0, 2);

        // Then
        assertThat(searchResults.size(), is(2));
    }

    // template

    protected T createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected abstract IRestClient<T> getApi();

    protected abstract IDtoOperations<T> getEntityOps();

    protected final RequestSpecification givenReadAuthenticated() {
        return getApi().givenReadAuthenticated();
    }

}
