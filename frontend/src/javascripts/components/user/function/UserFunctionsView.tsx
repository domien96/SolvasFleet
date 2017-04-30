import React from 'react';

import { th }    from '../../../utils/utils.ts';
import ExtendedInfoTable from '../../tables/ExtendedInfoTable.tsx';
import { Link } from 'react-router';

interface OverviewProps {
  Sfunctions : SFunctionData[];
  onFunctionDelete : (id : number) => void;
}

const Overview : React.StatelessComponent<OverviewProps> = props => {
  const tableHead = [
    th('role',      'function.role'),
    th('company',   'function.company')
  ];

  return (
    <ExtendedInfoTable head={ tableHead } data={ props.Sfunctions } onDelete={ props.onFunctionDelete } />
  );
}

interface Props {
  userId : number;
  Sfunctions : SFunctionData[];
  onFunctionDelete : (id : number) => void;
}

const UserFunctionsView : React.StatelessComponent<Props> = props => {

  return (
    <div>    
      <Link to={ '/users/'+ props.userId +'/functions/new' } className='btn btn-default pull-right'>
        <span className='glyphicon glyphicon-plus' aria-hidden='true'/> Add new function
      </Link>
      <h3>
        Functions
      </h3>
      <Overview Sfunctions={ props.Sfunctions } onFunctionDelete={ props.onFunctionDelete }/>
    </div>
  );
}

export default UserFunctionsView;
