package solvas.rest.api.models.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import solvas.rest.api.models.ApiPermission;

import java.io.IOException;

/**
 * Serialize permissions to JSON in the format required by the API.
 *
 * @author David
 */
public class PermissionSerializer extends JsonSerializer<ApiPermission> {

    @Override
    public void serialize(ApiPermission value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getScope());
    }
}