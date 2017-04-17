import React from 'react';

import Card from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';


interface Props {
  Sfunctions : SFunctionData[];
  onFunctionSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<Props> = props => {
  const tableHead = [
    th('role',      'function.role'),
    th('company',   'function.company')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.Sfunctions } onClick={ props.onFunctionSelect } />
  );
}

const UserFunctionsView : React.StatelessComponent<Props> = props => {

  return (
    <div className='col-xs-12'>
      <Card>
        <div className='card-title'>
          <h5>
            Functions
            <Link to='/users/new' className='btn btn-default pull-right'>
              <span className='glyphicon glyphicon-edit' aria-hidden='true'/> Edit
            </Link>
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
