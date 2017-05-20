import React from 'react';

import T from 'i18n-react';
import Filter from '../../filters/Filter.tsx';

interface Props {
  filter: VehicleFilterData;
  typeDisplay: string;
  licensePlateData: string[];
  vinData: string[];
  fleetData: string[];
  leasingCompanyData: string[];
  onFilterType: (type: string) => void;
  onFilterFleet: (selected: string[]) => void;
  onFilterLeasingCompany: (selected: string[]) => void;
  onFilterLicensePlate: (selectedLicenses: string[]) => void;
  onFilterVin: (selectedVins: string[]) => void;
  onFilterYear: (year: string | number) => void;
  onReset: () => void;
  onHide: () => void;
  onFilterArchive: () => void;
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
    fleetData,
    leasingCompanyData
  } = props;
  const { fleet, leasingCompany, licensePlate, vin, year, archived } = filter;

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
  const leasingCompanyInput: Typeaheadfield = {
    callback: onFilterLeasingCompany,
    data: leasingCompanyData,
    name: T.translate('vehicle.leasingCompany'),
    selected: [ leasingCompany ],
  }
  const fleetInput: Typeaheadfield = {
    callback: onFilterFleet,
    data: fleetData,
    name: T.translate('vehicle.fleet'),
    selected: [ fleet ],
  }

  const selections: Selectionfield[] = [ typeSelection ];
  const inputfields: Inputfield[] = [ yearInput ];
  const typeaheadfields: Typeaheadfield[] = [ vinInput, licensePlateInput, fleetInput, leasingCompanyInput ];
  const datefields: Datefield[] = [];

  return(
    <Filter
      selections={ selections }
      inputfields={ inputfields }
      typeaheadfields={ typeaheadfields }
      datefields={ datefields }
      onReset={ props.onReset }
      onHide={ props.onHide } 
      toggleArchive={ props.onFilterArchive }
      archived={ archived } />
  );
};

export default VehicleFilterLayout;
