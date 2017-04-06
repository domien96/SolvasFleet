import React from 'react';

import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';

export interface Props {
  users : UserData[];
  onUserSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<Props> = props => {
  const tableHead = [
    th('id',        'user.id'),
    th('firstName', 'user.firstName'),
    th('lastName',  'user.lastName')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.users } onClick={ props.onUserSelect } />
  );
}

export default Overview;
