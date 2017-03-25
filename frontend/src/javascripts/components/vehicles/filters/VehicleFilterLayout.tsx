import React from 'react';

import T     from 'i18n-react';
import Filter from '../../filters/Filter.tsx'

interface Props {
  filter: VehicleFilterData;
  typeDisplay: string
  onFilterType : (type : string) => void;
  onFilterFleet : (fleet : any) => void;
  onFilterLeasingCompany : (company : string | number) => void;
  onFilterLicensePlate : (license : string) => void;
  onFilterVin : (vin : string) => void;
  onFilterYear : (year : string | number) => void;
  onReset: () => void;
}

const VehicleFilterLayout :  React.StatelessComponent<Props> = props => {
  var { filter, typeDisplay, onFilterType, onFilterFleet, onFilterLeasingCompany, onFilterLicensePlate, onFilterVin, onFilterYear } = props;
  var { fleet, leasingCompany, licensePlate, vin, year } = filter;

  //Different choices for each type of vehicle
  var typeAllVehicles : Choice = {name:T.translate('vehicle.options.allVehicles'),  eventKey:'allVehicles',   callback:onFilterType};
  var typePersonalCar : Choice = {name:T.translate('vehicle.options.personalCar'),  eventKey:'personalCar',   callback:onFilterType};
  var typeVan :         Choice = {name:T.translate('vehicle.options.van'),          eventKey:'van',           callback:onFilterType};
  var typeSemiTrailer : Choice = {name:T.translate('vehicle.options.semiTrailer'),  eventKey:'semiTrailer',   callback:onFilterType};
  var typeTrailer :     Choice = {name:T.translate('vehicle.options.trailer'),      eventKey:'trailer',       callback:onFilterType};
  var typeTruck :       Choice = {name:T.translate('vehicle.options.truck'),        eventKey:'truck',         callback:onFilterType};
  
  var types : Choice[] = [typeAllVehicles, typePersonalCar, typeVan, typeSemiTrailer, typeTrailer, typeTruck];

  var typeSelection : Selectionfield = { name:'Vehicle type', title:typeDisplay, choices:types };

  //Different input fields for properties of a vehicle
  var fleetInput : Inputfield = {name:'Fleet ID', value:fleet, type:'number', callback:onFilterFleet};
  var leasingCompanyInput : Inputfield = {name:'Leasing company ID', value:leasingCompany, type:'number', callback:onFilterLeasingCompany};
  var licensePlateInput : Inputfield = {name:'License plate', value:licensePlate, type:'text', callback:onFilterLicensePlate};
  var vinInput : Inputfield = {name:'Vehicle Identification Number', value:vin, type:'text', callback:onFilterVin};
  var yearInput : Inputfield = {name:'Year', value:year, type:'number', callback:onFilterYear};

  var selections : Selectionfield[] = [typeSelection];
  var inputfields : Inputfield[] = [fleetInput, leasingCompanyInput, licensePlateInput, vinInput, yearInput];

  return(
    <Filter selections={ selections } inputfields={ inputfields } onReset={ props.onReset }/>
  );
}

export default VehicleFilterLayout;