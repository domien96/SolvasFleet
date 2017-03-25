package solvas.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Build a page response manually.
 *
 * Taken from https://github.com/pkainulainen/spring-data-jpa-examples/blob/master/query-methods/src/test/java/net/petrikainulainen/springdata/jpa/PageBuilder.java.
 *
 * @author Niko Strijbol
 * @author Petri Kainulainen
 */
public class PageBuilder<T> {

    private List<T> elements = new ArrayList<>();
    private Pageable pageRequest;
    private int totalElements;

    public PageBuilder() {}

    /**
     * Set the elements.
     *
     * @param elements The elements.
     *
     * @return this
     */
    public PageBuilder<T> elements(List<T> elements) {
        this.elements = elements;
        return this;
    }

    /**
     * Set the request.
     *
     * @param pageRequest the request.
     *
     * @return this
     */
    public PageBuilder<T> pageRequest(Pageable pageRequest) {
        this.pageRequest = pageRequest;
        return this;
    }

    /**
     * Set the total.
     *
     * @param totalElements Number of elements.
     *
     * @return this
     */
    public PageBuilder<T> totalElements(int totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    /**
     * @return The page.
     */
    public Page<T> build() {
        return new PageImpl<>(elements, pageRequest, totalElements);
    }
}
