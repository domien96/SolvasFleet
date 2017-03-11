package solvas.rest.query;

/**
 * Object containing the pagination query parameters.
 *
 * @author Niko Strijbol
 */
public class PaginationFilter implements Pageable {

    private int limit = 100;

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
        this.page = page;
    }
}