package solvas.rest.api.models;

import solvas.service.models.Model;

public abstract class ApiModel extends Model {

    private String url;

    public String getUrl(){
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
