import React from 'react';

import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';

export interface Props {
  clients : Company[];
  onClientSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<Props> = props => {
  const tableHead = [
    th('id',        'client.id'),
    th('firstName', 'client.firstName'),
    th('lastName',  'client.lastName')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.clients } onClick={ props.onClientSelect } />
  );
}

export default Overview;
