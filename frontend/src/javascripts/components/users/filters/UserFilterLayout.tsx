import React from 'react';

import T from 'i18n-react';
import UserFilterView from './UserFilterView.tsx';

interface Props {
  filter: UserFilterData;
  firstNameData: string[];
  lastNameData: string[];
  emailData: string[];
  sortDisplay: string;
  onFilterFirstName: (selectedUsers: string[]) => void;
  onFilterLastName: (selectedUsers: string[]) => void;
  onFilterEmail: (selectedUsers: string[]) => void;
  onFilterArchive: () => void;
  onSort: (event: string) => void;
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

const UserFilterLayout: React.StatelessComponent<Props> = props => {
  const {
    filter,
    firstNameData,
    lastNameData,
    emailData,
    onFilterFirstName,
    onFilterLastName,
    onFilterEmail,
    onSort,
    sortDisplay
  } = props;
  const { firstName, lastName, email, archived } = filter;

  // Different choices for each method 
  const sortId: Choice = createChoice(onSort, 'id', 'user.id');
  const sortFirstName: Choice = createChoice(onSort, 'firstName', 'user.firstName');
  const sortLastName: Choice = createChoice(onSort, 'lastName', 'user.lastName');
  const sortEmail: Choice = createChoice(onSort, 'email', 'user.email');

  const sortChoices: Choice[] = [ sortId, sortFirstName, sortLastName, sortEmail ];
  const sortSelection: Selectionfield = { name: 'Sort', title: sortDisplay, choices: sortChoices };

  // Different typeahead fields
  const firstNameInput: Typeaheadfield = {
    callback: onFilterFirstName,
    data: firstNameData,
    name: T.translate('user.firstName'),
    selected: [ firstName ],
  };

  const lastNameInput: Typeaheadfield = {
    callback: onFilterLastName,
    data: lastNameData,
    name: T.translate('user.lastName'),
    selected: [ lastName ],
  };

  const emailInput: Typeaheadfield = {
    callback: onFilterEmail,
    data: emailData,
    name: T.translate('user.email'),
    selected: [ email ],
  };

  const typeaheadfields: Typeaheadfield[] = [ firstNameInput, lastNameInput, emailInput ];
  const selections: Selectionfield[] = [ sortSelection ];


  return(
    <UserFilterView
      typeaheadfields={ typeaheadfields }
      selections={ selections }
      onReset={ props.onReset }
      onHide={ props.onHide }
      toggleArchive={ props.onFilterArchive }
      archived={ archived } />
  );
};

export default UserFilterLayout;
