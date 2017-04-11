import React from 'react';
import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import { Link } from 'react-router';
import { th } from '../../utils/utils.ts';

interface Props {
	contract : ContractData
	handleDelete : () => void;
}

const EditLink = ({ id } : { id : number }) => {
  return (
    <div className='col-sm-6'>
      <Link to={ `${id}/edit` } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-edit' /> Edit
      </Link>
    </div>
  );
}

const DeleteLink = ({ handleDelete } : { handleDelete : () => void }) => {
  return (
    <div className='col-sm-6'>
      <button onClick={ handleDelete } className='btn btn-danger form-control'>
        <span className='glyphicon glyphicon-remove' /> Delete
      </button>
    </div>
  );
}

const ContractView : React.StatelessComponent<Props> = props => {
	var { franchise, id, insuranceCompany, premium, type, vehicle, startDate, endDate } = props.contract;

  const data = [
    th('contract.insuranceCompany', insuranceCompany),
    th('contract.vehicle',  				vehicle),
    th('contract.type',     				type),
		th('contract.franchise',     		franchise),
    th('contract.premium',     			premium),
		th('contract.startDate',     		startDate),
    th('contract.endDate',     			endDate),
  ];

  return (
    <Card>
      <div className='card-content user'>
        <h2>Contract {id}:</h2>
        <div className='row actions'>
          <EditLink id={ id }/>
          <DeleteLink handleDelete={ props.handleDelete } />
        </div>
      </div>
      <div className='card-content'>
        <DetailTable data={ data }/>
      </div>
    </Card>
  );
}

export default ContractView;