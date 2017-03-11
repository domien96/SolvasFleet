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

    void setLimit(int limit);

    void setPage(int page);
}