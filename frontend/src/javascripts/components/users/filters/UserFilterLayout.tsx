import React from 'react';

import T from 'i18n-react';
import UserFilterView from './UserFilterView.tsx';

interface Props {
  filter: UserFilterData;
  firstNameData: string[];
  lastNameData: string[];
  emailData: string[];
  onFilterFirstName: (selectedUsers: string[]) => void;
  onFilterLastName: (selectedUsers: string[]) => void;
  onFilterEmail: (selectedUsers: string[]) => void;
  onReset: () => void;
  onHide: () => void;
}

const UserFilterLayout: React.StatelessComponent<Props> = props => {
  const {
    filter,
    firstNameData,
    lastNameData,
    emailData,
    onFilterFirstName,
    onFilterLastName,
    onFilterEmail
  } = props;
  const { firstName, lastName, email } = filter;

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

  return(
    <UserFilterView
      typeaheadfields={ typeaheadfields }
      onReset={ props.onReset }
      onHide={ props.onHide } />
  );
};

export default UserFilterLayout;
