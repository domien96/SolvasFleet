package solvas.rest.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import solvas.service.models.Model;

/**
 * Created by steve on 11/03/2017.
 */
public abstract class ApiModel extends Model {

    private String url;

    public String getUrl(){
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    @Override
    public boolean isArchived() {
        return super.isArchived();
    }

    @JsonIgnore
    @Override
    public void setArchived(boolean archived) {
        super.setArchived(archived);
    }
}
