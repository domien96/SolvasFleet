import React from 'react';

import T     from 'i18n-react';
import Filter from '../../filters/Filter.tsx'


const VehicleFilterLayout :  React.StatelessComponent<FilterProps> = props => {
  var { filter, typeDisplay, onFilterType, onFilterFleet } = props;
  var { fleet } = filter;

  //Different choices for each type of vehicle
  var typeAllVehicles : Choice = {name:T.translate('vehicle.options.allVehicles'), eventKey:'allVehicles', callback:onFilterType};
  var typePersonalCar : Choice = {name:T.translate('vehicle.options.personalCar'), eventKey:'personalCar', callback:onFilterType};
  var typeVan : Choice = {name:T.translate('vehicle.options.van'), eventKey:'van', callback:onFilterType};
  var typeSemiTrailer : Choice = {name:T.translate('vehicle.options.semiTrailer'), eventKey:'semiTrailer', callback:onFilterType};
  var typeTrailer : Choice = {name:T.translate('vehicle.options.trailer'), eventKey:'trailer', callback:onFilterType};
  var typeTruck : Choice = {name:T.translate('vehicle.options.truck'), eventKey:'truck', callback:onFilterType};
  
  var types : Choice[] = [typeAllVehicles, typePersonalCar, typeVan, typeSemiTrailer, typeTrailer, typeTruck];
  
  //TODO: this gives a type error:
  var typeSelection : Selection = { title:typeDisplay, choices:types };

  //Different input fields for properties of a vehicle
  var fleetInput : Inputfield = {name:'fleetId', value:fleet, type:'number', callback:onFilterFleet};

  var selections : Selection[] = [typeSelection];
  var inputfields : Inputfield[] = [fleetInput];

  return(
    <Filter selections={ selections } inputfields={ inputfields } onReset={ props.onReset }/>
  );
}

export default VehicleFilterLayout;