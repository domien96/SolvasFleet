package solvas.service.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * This class shows all fields that will be ignored when saving revisions.
 *
 * @author Niko Strijbol
 */
abstract class IgnoreDataMixin {
    @JsonIgnore
    LocalDateTime createdAt;
    @JsonIgnore
    LocalDateTime updatedAt;
    @JsonIgnore
    String url;
}