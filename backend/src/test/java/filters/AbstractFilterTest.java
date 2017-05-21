package filters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import solvas.persistence.api.Filter;
import solvas.rest.query.ArchiveFilter;
import solvas.service.models.Model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractFilterTest<T extends Model> {

    private ArchiveFilter<T> filter;

    @Mock
    private CriteriaBuilder criteriaBuilderMock;

    @Mock
    private Root<T> rootMock;

    @Mock
    private Join join;

    @Mock
    private Predicate predicate;

    @Before
    public void setUp()
    {
        when(rootMock.join(Matchers.anyString())).thenReturn(join);
        when(criteriaBuilderMock.equal(any(),any())).thenReturn(predicate);
        when(join.join(Matchers.anyString())).thenReturn(join);
    }

    @Test
    public void asPredicatesWithCorrectFilterParameters()
    {

        Collection<Predicate> predicates=getFilterWithCorrectParameters().asPredicates(criteriaBuilderMock,rootMock);
        assertEquals("Amount of predicates",parameterSize(),predicates.size());
    }

    @Test
    public void asPredicatesWithBadFilterParameters()
    {

        Collection<Predicate> predicates=getFilterWithBadParameters().asPredicates(criteriaBuilderMock,rootMock);
        assertEquals("Amount of predicates",emptyFilterParameterSize(),predicates.size());
    }

    abstract Filter<T> getFilterWithCorrectParameters();
    abstract Filter<T> getFilterWithBadParameters();

    abstract int parameterSize();

    /**
     *
     * @return Expected amount of predicates on an empty filter
     */
    protected int emptyFilterParameterSize() {
        return 1;
    }
}
