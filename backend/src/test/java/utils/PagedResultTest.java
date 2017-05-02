package utils;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import solvas.rest.utils.PagedResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PagedResultTest {

    private static final String PAGE_LIMIT_KEY = "limit";
    private static final String PAGE_OFFSET_KEY = "offset";
    private static final String PAGE_TOTAL_KEY = "total";
    private static final String PAGE_FIRST_KEY = "first";
    private static final String PAGE_LAST_KEY = "last";
    private static final String PAGE_PREVIOUS_KEY = "previous";
    private static final String PAGE_NEXT_KEY = "next";
    private static final String BASE_URL="http://localhost?";

    @Mock
    private Page page;

    @Mock
    private Pageable prev;

    @Mock
    private Pageable nextPageable;

    @Before
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }


    @Test
    public void pagedResult()
    {
        int limit=10; //pagesize
        long total=100;
        int current=4;
        int getTotalPages=10;
        when(page.getNumber()).thenReturn(current);
        when(prev.getPageNumber()).thenReturn(current-1);
        when(nextPageable.getPageNumber()).thenReturn(current+1);
        when(page.getSize()).thenReturn(limit);
        when(page.getTotalElements()).thenReturn(total);
        when(page.previousPageable()).thenReturn(prev);
        when(page.nextPageable()).thenReturn(nextPageable);
        when(page.hasNext()).thenReturn(true);
        when(page.hasPrevious()).thenReturn(true);
        //page isn't last/first
        when(page.getTotalPages()).thenReturn(getTotalPages);

        PagedResult result = new PagedResult(page);
        assertThat(result.get(PAGE_LIMIT_KEY),is(limit));
        assertThat(result.get(PAGE_OFFSET_KEY),is(current*limit));
        assertThat(result.get(PAGE_FIRST_KEY),is(BASE_URL+"page="+0+"&limit="+limit));
        assertThat(result.get(PAGE_LAST_KEY),is(BASE_URL+"page="+9+"&limit="+limit));
        assertThat(result.get(PAGE_NEXT_KEY),is(BASE_URL+"page="+(current+1)+"&limit="+limit));
        assertThat(result.get(PAGE_PREVIOUS_KEY),is(BASE_URL+"page="+(current-1)+"&limit="+limit));
        assertThat(result.get(PAGE_TOTAL_KEY),is(total));
    }
}
