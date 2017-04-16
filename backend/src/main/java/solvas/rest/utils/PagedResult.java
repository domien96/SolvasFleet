package solvas.rest.utils;

import org.springframework.data.domain.Page;
import solvas.rest.SimpleUrlBuilder;

/**
 * Represents an API response that is paged.
 *
 * @param <T> The type of the entities over which the pagination happens.
 *
 * @author Niko Strijbol
 */
public class PagedResult<T> extends JsonListWrapper<T> {

    public static final String PAGINATION_QUERY_PAGE = "page";
    public static final String PAGINATION_QUERY_SIZE = "limit";

    private static final String PAGE_LIMIT_KEY = "limit";
    private static final String PAGE_OFFSET_KEY = "offset";
    private static final String PAGE_TOTAL_KEY = "total";
    private static final String PAGE_FIRST_KEY = "first";
    private static final String PAGE_LAST_KEY = "last";
    private static final String PAGE_PREVIOUS_KEY = "previous";
    private static final String PAGE_NEXT_KEY = "next";

    /**
     * Constructs a paged result from a page.
     *
     * @param page The data.
     */
    public PagedResult(Page<T> page) {
        super(page.getContent());

        put(PAGE_LIMIT_KEY, page.getSize());
        put(PAGE_OFFSET_KEY, page.getNumber() * page.getSize());
        put(PAGE_TOTAL_KEY, page.getTotalElements());

        if (!page.isFirst()) {
            put(PAGE_FIRST_KEY, buildPageUri(0, page.getSize()));
        }
        if (!page.isLast()) {
            // This can be -1, because we don't do this if there is only one page.
            put(PAGE_LAST_KEY, buildPageUri(page.getTotalPages() - 1, page.getSize()));
        }

        if (page.hasPrevious()) {
            put(PAGE_PREVIOUS_KEY, buildPageUri(page.previousPageable().getPageNumber(), page.getSize()));
        }
        if (page.hasNext()) {
            put(PAGE_NEXT_KEY, buildPageUri(page.nextPageable().getPageNumber(), page.getSize()));
        }
    }

    /**
     * Construct an URL from the current request for the given page and limit.
     *
     * @param page The page.
     * @param limit The limit.
     *
     * @return The full URI.
     */
    private String buildPageUri(int page, int limit) {
        return SimpleUrlBuilder.baseBuilder()
                .queryParam(PAGINATION_QUERY_PAGE, page)
                .queryParam(PAGINATION_QUERY_SIZE, limit)
                .toUriString();
    }
}