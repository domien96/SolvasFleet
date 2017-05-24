package utils;

import org.junit.Test;
import solvas.rest.utils.JsonListWrapper;

import java.util.Collection;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.junit.Assert.assertEquals;

/**
 * Unit testing the JsonListWrapper
 */
public class JsonListWrapperTest {

    @Test
    public void defaultConstructor() {
        Collection<Object> collection = randomCollectionOf(100,Object.class);
        JsonListWrapper<Object> obj1 = new JsonListWrapper<>();
        obj1.put("key", collection);
        JsonListWrapper<Object> obj2 = new JsonListWrapper<>(collection, "key");
        assertEquals(obj1, obj2);
    }

    /**
     * Testing the default nokey constructor, this wraps the list under key: 'data'
     */
    @Test
    public void constructorWithCollectionArgument()
    {
        Collection<Object> collection = randomCollectionOf(100,Object.class);
        JsonListWrapper<Object> listWrapper = new JsonListWrapper<>(collection);
        assertEquals("wrapped collection is not correct size",((Collection)listWrapper.get("data")).size(),100);
    }

    /**
     * Testing the constructor with a given key
     */
    @Test
    public void constructorWithCollectionAndKeyArguments()
    {
        Collection<Object> collection = randomCollectionOf(100,Object.class);
        String key = random(String.class);
        JsonListWrapper<Object> listWrapper = new JsonListWrapper<>(collection,key);
        assertEquals("wrapped collection is not correct size",((Collection)listWrapper.get(key  )).size(),100);
    }
}
