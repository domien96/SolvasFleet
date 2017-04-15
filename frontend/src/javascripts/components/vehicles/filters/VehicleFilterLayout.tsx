import React from 'react';

import T     from 'i18n-react';
import Filter from '../../filters/Filter.tsx'

interface Props {
  filter: VehicleFilterData;
  typeDisplay: string
  licensePlateData: string[];
  vinData: string[];
  onFilterType : (type : string) => void;
  onFilterFleet : (fleet : any) => void;
  onFilterLeasingCompany : (company : string | number) => void;
  onFilterLicensePlate : (selectedLicenses : string[]) => void;
  onFilterVin : (selectedVins : string[]) => void;
  onFilterYear : (year : string | number) => void;
  onReset: () => void;
  onHide: () => void;
}

const VehicleFilterLayout :  React.StatelessComponent<Props> = props => {
  var { filter, typeDisplay, licensePlateData, vinData, onFilterType, onFilterFleet, onFilterLeasingCompany, onFilterLicensePlate, onFilterVin, onFilterYear } = props;
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
  var fleetInput :          Inputfield = {name:T.translate('vehicle.fleet'),          value:fleet,          type:'number',  callback:onFilterFleet};
  var leasingCompanyInput : Inputfield = {name:T.translate('vehicle.leasingCompany'), value:leasingCompany, type:'number',  callback:onFilterLeasingCompany};
  var yearInput :           Inputfield = {name:T.translate('vehicle.year'),           value:year,           type:'number',  callback:onFilterYear};


  //Different typeahead fields
  var licensePlateInput : Typeaheadfield = {name:T.translate('vehicle.licensePlate'), data:licensePlateData, selected:[licensePlate], callback:onFilterLicensePlate};
  var vinInput :          Typeaheadfield = {name:T.translate('vehicle.vin'),          data:vinData,          selected:[vin],          callback:onFilterVin};


  var selections : Selectionfield[] = [typeSelection];
  var inputfields : Inputfield[] = [fleetInput, leasingCompanyInput, yearInput];
  var typeaheadfields : Typeaheadfield[] = [licensePlateInput, vinInput];

  return(
    <Filter selections={ selections } inputfields={ inputfields } typeaheadfields={ typeaheadfields } onReset={ props.onReset } onHide={ props.onHide }/>
  );
}

export default VehicleFilterLayout;