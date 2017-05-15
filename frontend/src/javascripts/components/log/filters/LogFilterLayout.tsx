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
  const typeAllTypes: Choice = createChoice(onFilterType, 'allTypes', 'log.options.types.allTypes');
  const typeUser: Choice = createChoice(onFilterType, 'solvas.service.models.User', 'log.options.solvas.service.models.User');
  const typeFunction: Choice = createChoice(onFilterType, 'solvas.service.models.Function', 'log.options.solvas.service.models.Function');
  const typeCompany: Choice = createChoice(onFilterType, 'solvas.service.models.Company', 'log.options.solvas.service.models.Company');
  const typeVehicle: Choice = createChoice(onFilterType, 'solvas.service.models.Vehicle', 'log.options.solvas.service.models.Vehicle');
  const typeContract: Choice = createChoice(onFilterType, 'solvas.service.models.Contract', 'log.options.solvas.service.models.Contract');
  const typeInvoice: Choice = createChoice(onFilterType, 'solvas.service.models.Invoice', 'log.options.solvas.service.models.Invoice');
  const typeRole: Choice = createChoice(onFilterType, 'solvas.service.models.Role', 'log.options.solvas.service.models.Role');

  const types: Choice[] = [ typeAllTypes, typeUser, typeFunction, typeCompany, typeVehicle, typeContract, typeInvoice, typeRole ];
  const typeSelection: Selectionfield = { name: 'Entry type', title: typeDisplay, choices: types };

  // Different choices for each method 
  const methodAllMethods: Choice = createChoice(onFilterMethod, 'allMethods', 'log.options.methods.allMethods');
  const methodInsert: Choice = createChoice(onFilterMethod, 'insert', 'log.options.insert');
  const methodUpdate: Choice = createChoice(onFilterMethod, 'update', 'log.options.update');
  const methodArchive: Choice = createChoice(onFilterMethod, 'archive', 'log.options.archive');
  const methodDelete: Choice = createChoice(onFilterMethod, 'delete', 'log.options.delete');

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
