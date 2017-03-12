package solvas.rest.api.mappings;

import solvas.models.VehicleType;

/**
 * Created by steve on 11/03/2017.
 */
public class VehicleTypeMapping extends Mapping<VehicleType,String> {
    @Override
    public VehicleType convertToModel(String api) {
        return vehicleTypeDao.withType(api);
    }

    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
