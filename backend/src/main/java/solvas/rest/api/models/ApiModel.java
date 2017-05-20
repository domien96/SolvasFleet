package solvas.rest.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import solvas.service.models.Model;

/**
 * Abstract class for models in the API layer
 */
public abstract class ApiModel extends Model {

    private String url;

    public String getUrl(){
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean isArchived() {
        return super.isArchived();
    }

    @Override
    public void setArchived(boolean archived) {
        super.setArchived(archived);
    }
}
