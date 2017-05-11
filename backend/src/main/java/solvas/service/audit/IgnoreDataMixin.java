package solvas.service.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * This class shows all fields that will be ignored when saving revisions.
 *
 * @author Niko Strijbol
 */
public abstract class IgnoreDataMixin {
    @JsonIgnore
    public LocalDateTime createdAt;
    @JsonIgnore
    public LocalDateTime updatedAt;
    @JsonIgnore
    public String url;
}