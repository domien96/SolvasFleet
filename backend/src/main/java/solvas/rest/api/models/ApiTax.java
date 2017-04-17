package solvas.rest.api.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * Created by steve on 16/04/2017.
 */
public class ApiTax extends ApiModel{

    private double tax;


    private String contractType;
    private String vehicleType;


    @Override
    @JsonIgnore
    public String getUrl() {
        return super.getUrl();
    }

    @Override
    @JsonIgnore
    public void setUrl(String url) {
        super.setUrl(url);
    }

    @Override
    @JsonIgnore
    public int getId() {
        return super.getId();
    }

    @Override
    @JsonIgnore
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    @JsonIgnore
    public void setCreatedAt(LocalDateTime createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    @JsonIgnore
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    @JsonIgnore
    public void setUpdatedAt(LocalDateTime updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

    @Override
    @JsonIgnore
    public boolean isArchived() {
        return super.isArchived();
    }

    @Override
    @JsonIgnore
    public void setArchived(boolean archived) {
        super.setArchived(archived);
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @JsonIgnore
    public String getContractType() {
        return contractType;
    }

    @JsonIgnore
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    @JsonIgnore
    public String getVehicleType() {
        return vehicleType;
    }

    @JsonIgnore
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
