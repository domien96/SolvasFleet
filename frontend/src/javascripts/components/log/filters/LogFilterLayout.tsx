import React from 'react';

import T from 'i18n-react';
import Filter from '../../filters/Filter.tsx';

interface Props {
  filter: LogFilterData;
  typeDisplay: string;
  methodDisplay: string;
  userData: string[];
  onFilterType: (type: string) => void;
  onFilterMethod: (type: string) => void;
  onFilterUser: (selectedUsers: string[]) => void;
  onFilterStartDate: (after: any) => void;
  onFilterEndDate: (before: any) => void;
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

const LogFilterLayout: React.StatelessComponent<Props> = props => {
  const {
    filter,
    typeDisplay,
    methodDisplay,
    userData,
    onFilterType,
    onFilterMethod,
    onFilterUser,
    onFilterStartDate,
    onFilterEndDate
  } = props;
  const { after, before, user } = filter;

  // Different choices for each type of log
  const typeAllTypes: Choice = createChoice(onFilterType, 'allTypes', 'log.types.allTypes');
  const typeUser: Choice = createChoice(onFilterType, 'User', 'log.types.User');
  const typeFunction: Choice = createChoice(onFilterType, 'Function', 'log.types.Function');
  const typeCompany: Choice = createChoice(onFilterType, 'Company', 'log.types.Company');
  const typeVehicle: Choice = createChoice(onFilterType, 'Vehicle', 'log.types.Vehicle');
  const typeContract: Choice = createChoice(onFilterType, 'Contract', 'log.types.Contract');
  const typeInvoice: Choice = createChoice(onFilterType, 'Invoice', 'log.types.Invoice');
  const typeRole: Choice = createChoice(onFilterType, 'Role', 'log.types.Role');
  const typeFleet: Choice = createChoice(onFilterType, 'Fleet', 'log.types.Fleet');

  const types: Choice[] = [ typeAllTypes, typeUser, typeFunction, typeCompany, typeVehicle, typeContract, typeInvoice, typeRole, typeFleet ];
  const typeSelection: Selectionfield = { name: 'Entry type', title: typeDisplay, choices: types };

  // Different choices for each method 
  const methodAllMethods: Choice = createChoice(onFilterMethod, 'allMethods', 'log.methods.allMethods');
  const methodInsert: Choice = createChoice(onFilterMethod, 'insert', 'log.methods.insert');
  const methodUpdate: Choice = createChoice(onFilterMethod, 'update', 'log.methods.update');
  const methodArchive: Choice = createChoice(onFilterMethod, 'archive', 'log.methods.archive');
  const methodDelete: Choice = createChoice(onFilterMethod, 'delete', 'log.methods.delete');

  const methods: Choice[] = [ methodAllMethods, methodInsert, methodUpdate, methodArchive, methodDelete ];
  const methodSelection: Selectionfield = { name: 'Method', title: methodDisplay, choices: methods };

  // Different typeahead fields
  const userInput: Typeaheadfield = {
    callback: onFilterUser,
    data: userData,
    name: T.translate('log.user'),
    selected: [ user ],
  };

  // Different date inputfields
  const afterInput: Datefield = {
    name: 'log.after',
    data: after,
    callback: onFilterStartDate
  }

  const beforeInput: Datefield = {
    name: 'log.before',
    data: before,
    callback: onFilterEndDate
  }

  const selections: Selectionfield[] = [ typeSelection, methodSelection ];
  const inputfields: Inputfield[] = [];
  const typeaheadfields: Typeaheadfield[] = [ userInput ];
  const datefields: Datefield[] = [ afterInput, beforeInput ];

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

export default LogFilterLayout;
