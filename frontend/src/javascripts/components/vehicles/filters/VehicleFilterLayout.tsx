import React from 'react';

import T from 'i18n-react';
import Filter from '../../filters/Filter.tsx';

interface Props {
  filter: VehicleFilterData;
  typeDisplay: string;
  licensePlateData: string[];
  vinData: string[];
  onFilterType: (type: string) => void;
  onFilterFleet: (fleet: any) => void;
  onFilterLeasingCompany: (company: string | number) => void;
  onFilterLicensePlate: (selectedLicenses: string[]) => void;
  onFilterVin: (selectedVins: string[]) => void;
  onFilterYear: (year: string | number) => void;
  onReset: () => void;
  onHide: () => void;
}

const VehicleFilterLayout: React.StatelessComponent<Props> = props => {
  const {
    filter,
    typeDisplay,
    licensePlateData,
    vinData,
    onFilterType,
    onFilterFleet,
    onFilterLeasingCompany,
    onFilterLicensePlate,
    onFilterVin,
    onFilterYear,
  } = props;
  const { fleet, leasingCompany, licensePlate, vin, year } = filter;

  // Different choices for each type of vehicle
  const typeAllVehicles: Choice = {
    callback: onFilterType,
    eventKey: 'allVehicles',
    name: T.translate('vehicle.options.allVehicles'),
  };
  const typePersonalCar: Choice = {
    callback: onFilterType,
    eventKey: 'personalCar',
    name: T.translate('vehicle.options.personalCar'),
  };
  const typeVan: Choice = {
    callback: onFilterType,
    eventKey: 'van',
    name: T.translate('vehicle.options.van'),
  };
  const typeSemiTrailer: Choice = {
    callback: onFilterType,
    eventKey: 'semiTrailer',
    name: T.translate('vehicle.options.semiTrailer'),
  };
  const typeTrailer: Choice = {
    callback: onFilterType,
    eventKey: 'trailer',
    name: T.translate('vehicle.options.trailer'),
  };
  const typeTruck: Choice = {
    callback: onFilterType,
    eventKey: 'truck',
    name: T.translate('vehicle.options.truck'),
  };

  const types: Choice[] = [ typeAllVehicles, typePersonalCar, typeVan, typeSemiTrailer, typeTrailer, typeTruck ];

  const typeSelection: Selectionfield = { name: 'Vehicle type', title: typeDisplay, choices: types };

  // Different input fields for properties of a vehicle
  const fleetInput: Inputfield = {
    callback: onFilterFleet,
    name: T.translate('vehicle.fleet'),
    type: 'number',
    value: fleet,
  };
  const leasingCompanyInput: Inputfield = {
    callback: onFilterLeasingCompany,
    name: T.translate('vehicle.leasingCompany'),
    type: 'number',
    value: leasingCompany,
  };
  const yearInput: Inputfield = {
    callback: onFilterYear,
    name: T.translate('vehicle.year'),
    type: 'number',
    value: year,
  };

  // Different typeahead fields
  const licensePlateInput: Typeaheadfield = {
    callback: onFilterLicensePlate,
    data: licensePlateData,
    name: T.translate('vehicle.licensePlate'),
    selected: [ licensePlate ],
  };
  const vinInput: Typeaheadfield = {
    callback: onFilterVin,
    data: vinData,
    name: T.translate('vehicle.vin'),
    selected: [ vin ],
  };

  const selections: Selectionfield[] = [ typeSelection ];
  const inputfields: Inputfield[] = [ fleetInput, leasingCompanyInput, yearInput ];
  const typeaheadfields: Typeaheadfield[] = [ licensePlateInput, vinInput ];
  const datefields: Datefield[] = [];

  return(
    <Filter
      selections={ selections }
      inputfields={ inputfields }
      typeaheadfields={ typeaheadfields }
      datefields={ datefields }
      onReset={ props.onReset }
      onHide={ props.onHide } />
  );
};

export default VehicleFilterLayout;
