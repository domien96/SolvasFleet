package utils;

import org.junit.Test;
import solvas.rest.utils.IteratorUtils;

import java.util.Collection;
import java.util.List;

import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Unit testing the IteratorUtils class
 */
public class IteratorUtilsTest {

    /**
     * Test that the list created from an {@link Iterable} has the same (amount of) items
     */
    @Test
    public void testToList() {
        Collection<Integer> randomCollection = randomListOf(100, Integer.class);
        List<Integer> iteratedList = IteratorUtils.toList(randomCollection);
        assertEquals(randomCollection.size(), iteratedList.size());
        System.out.println(iteratedList);
        for(Integer x: randomCollection) {
            assertThat(iteratedList, hasItem(x));
        }
    }

}
