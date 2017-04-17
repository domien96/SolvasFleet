import React from 'react';

import Card from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';
import { Link } from 'react-router';

interface OverviewProps {
  Sfunctions : SFunctionData[];
  onFunctionSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<OverviewProps> = props => {
  const tableHead = [
    th('role',      'function.role'),
    th('company',   'function.company')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.Sfunctions } onClick={ props.onFunctionSelect } />
  );
}

interface Props {
  userId : number;
  Sfunctions : SFunctionData[];
  onFunctionSelect : (id : number) => void;
}

const UserFunctionsView : React.StatelessComponent<Props> = props => {

  return (
    <div className='col-xs-12'>
      <Card>
        <div className='card-title'>
          <Link to={ '/users/'+ props.userId +'/functions/edit' } className='btn btn-default pull-right'>
            <span className='glyphicon glyphicon-edit' aria-hidden='true'/> Edit
          </Link>
          <h5>
            Functions
          </h5>
        </div>
        <div className='card-content'>
          <Overview Sfunctions={ props.Sfunctions } onFunctionSelect={ props.onFunctionSelect }/>
        </div>
      </Card>
    </div>
  );
}

export default UserFunctionsView;
