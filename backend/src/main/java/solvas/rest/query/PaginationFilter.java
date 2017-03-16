package solvas.rest.query;

/**
 * Object containing the pagination query parameters.
 *
 * @author Niko Strijbol
 */
public class PaginationFilter implements Pageable {

    public static final int DEFAULT_PER_PAGE = 100;

    private int limit = DEFAULT_PER_PAGE;

    private int page = 0;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return The current page number, or 0 if not present.
     */
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 0) {
            this.page = 0;
        } else {
            this.page = page;
        }
    }
}