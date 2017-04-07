import React from 'react';

import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';

export interface Props {
  clients : Company[];
  onClientSelect : (id : number) => void;
  fetchClients : (query?:any)=>void;
}

const Overview : React.StatelessComponent<Props> = props => {
  const tableHead = [
    th('id',        'company.id'),
    th('name', 'company.name'),
    th('vatNumber',  'company.vatNumber')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.clients } onClick={ props.onClientSelect } />
  );
}

export default Overview;
