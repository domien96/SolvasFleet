import React from 'react';

import T from 'i18n-react';
import Filter from '../../filters/Filter.tsx';

interface Props {
  filter: CompanyFilterData;
  countryData: string[];
  vatData: string[];
  nameData: string[];
  typeDisplay: string;
  onFilterType: (type: string) => void;
  onFilterVat: (selected: string[]) => void;
  onFilterName: (selected: string[]) => void;  
  onFilterCountry: (selected: string[]) => void;
  onReset: () => void;
  onHide: () => void;
}

const createChoice = (callback: any, eventKey: string, name: string) => {
  const newChoice: Choice = {
    callback: callback,
    eventKey: eventKey,
    name: T.translate(name)
  };
  return newChoice;
}

const ClientFilterLayout: React.StatelessComponent<Props> = props => {
  const {
    filter,
    typeDisplay,
    nameData,
    vatData,
    countryData,
    onFilterType,
    onFilterVat,
    onFilterName,
    onFilterCountry
  } = props;
  const { vatNumber, name, country } = filter;

  // Different choices for each type of log
  const typeAllTypes: Choice = createChoice(onFilterType, 'allTypes', 'company.type.allTypes');
  const typeCustomer: Choice = createChoice(onFilterType, 'Customer', 'company.type.Customer');
  const typeLeasingCompany: Choice = createChoice(onFilterType, 'LeasingCompany', 'company.type.LeasingCompany');
  const typeInsuranceCompany: Choice = createChoice(onFilterType, 'InsuranceCompany', 'company.type.InsuranceCompany');

  const types: Choice[] = [ typeAllTypes, typeCustomer, typeLeasingCompany, typeInsuranceCompany ];
  const typeSelection: Selectionfield = { name: 'Company type', title: typeDisplay, choices: types };

  // Different typeahead fields
  const vatInput: Typeaheadfield = {
    callback: onFilterVat,
    data: vatData,
    name: T.translate('company.vatNumber'),
    selected: [ vatNumber ],
  };

  const nameInput: Typeaheadfield = {
    callback: onFilterName,
    data: nameData,
    name: T.translate('company.name'),
    selected: [ name ],
  };

  const countryInput: Typeaheadfield = {
    callback: onFilterCountry,
    data: countryData,
    name: T.translate('company.address.country'),
    selected: [ country ],
  };

  const selections: Selectionfield[] = [ typeSelection ];
  const inputfields: Inputfield[] = [];
  const typeaheadfields: Typeaheadfield[] = [ nameInput, vatInput, countryInput ];
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

export default ClientFilterLayout;
