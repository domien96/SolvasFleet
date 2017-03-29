package solvas.rest.api.models;

import solvas.service.models.Model;

/**
 * Created by steve on 11/03/2017.
 */
public abstract class ApiModel extends Model {
    private int lastUpdatedBy=0;

    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    private String url;

    public String getUrl(){
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
