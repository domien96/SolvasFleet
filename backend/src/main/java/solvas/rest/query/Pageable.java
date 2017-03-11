package solvas.rest.query;

/**
 * Item that contains page-related information.
 *
 * @author Niko Strijbol
 */
public interface Pageable {

    /**
     * Get the maximal number of items per page. The default is 100.
     *
     * @return The maximal items per page.
     */
    int getLimit();

    /**
     * Get the requested page. If not present in the request, this method will return 0.
     *
     * @return The page.
     */
    int getPage();

    /**
     * Set the limit per page.
     *
     * @param limit The limit.
     */
    void setLimit(int limit);

    /**
     * Set the current page, starting from 0. If the page is less than 0, 0 is used.
     *
     * @param page The current page.
     */
    void setPage(int page);
}