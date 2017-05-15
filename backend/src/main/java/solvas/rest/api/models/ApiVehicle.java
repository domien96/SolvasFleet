package solvas.rest.api.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.hibernate.validator.constraints.NotBlank;
import solvas.rest.csv.LocalDateTimeBeanField;
import solvas.rest.utils.validators.UniqueVin;
import solvas.service.models.validators.AfterLocalDateTime;
import solvas.service.models.validators.IsValidVehicleType;
import solvas.service.models.validators.Vin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Vehicle in the API layer
 */
@UniqueVin
public class ApiVehicle extends ApiModel {

    @CsvBindByName(required = true)
    private String licensePlate;

    @Vin
    @NotNull
    @CsvBindByName(required = true)
    private String vin;

    @NotBlank
    @CsvBindByName(required = true)
    private String model;

    @NotBlank
    @IsValidVehicleType
    @CsvBindByName(required = true)
    private String type;

    @Min(value = 0)
    @CsvBindByName(required = true)
    private int mileage;

    @NotNull
    @AfterLocalDateTime(year = 1981,month = 1,dayOfMonth = 1,hour = 0,minute = 0)
    @CsvCustomBindByName(converter = LocalDateTimeBeanField.class)
    private LocalDateTime year;

    @CsvBindByName
    private int leasingCompany;

    @Min(value = 0)
    @CsvBindByName
    private int value;

    @CsvBindByName
    private int fleet = -1; //Use -1 as default value because 0 is removing it.

    @NotBlank
    @CsvBindByName(required = true)
    private String brand;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int kilometerCount) {
        this.mileage = kilometerCount;
    }

    public LocalDateTime getYear() {
        return year;
    }

    public void setYear(LocalDateTime year) {
        this.year = year;
    }

    public int getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(int leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFleet() {
        return fleet;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
