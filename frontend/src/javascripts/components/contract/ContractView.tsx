import React from 'react';
import Card from '../app/Card.tsx';
import DetailContractTable from '../tables/DetailContractTable.tsx';
import { Link } from 'react-router';
import { th } from '../../utils/utils.ts';

interface Props {
  contract: ContractData;
  handleDelete: () => void;
  onGetCompanyName: (id: number) => any;
}

const EditLink = ({ id }: { id: number }) => {
  return (
    <div className='col-sm-6'>
      <Link to={ `/contracts/${id}/edit` } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-edit' /> Edit
      </Link>
    </div>
  );
};

const DeleteLink = ({ handleDelete }: { handleDelete: () => void }) => {
  return (
    <div className='col-sm-6'>
      <button onClick={ handleDelete } className='btn btn-danger form-control'>
        <span className='glyphicon glyphicon-remove' /> Delete
      </button>
    </div>
  );
};

const ContractView: React.StatelessComponent<Props> = props => {
  const { franchise, id, insuranceCompany, premium, type, vehicle, startDate, endDate } = props.contract;

  let startDateDisplay = startDate;
  let endDateDisplay = endDate;
  if (startDate) {
    startDateDisplay = startDate.split('T')[0];
  }
  if (endDate) {
    endDateDisplay = endDate.split('T')[0];
  }

  const insuranceCompanyData = th('contract.insuranceCompany', `${insuranceCompany}: ${props.onGetCompanyName(insuranceCompany)}`);
  const vehicleData = th('contract.vehicle', vehicle);

  const data = [
    th('contract.type', type),
    th('contract.franchise', franchise),
    th('contract.premium', premium),
    th('contract.startDate', startDateDisplay),
    th('contract.endDate', endDateDisplay),
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
        <DetailContractTable 
          data={ data } 
          insuranceCompanyData={ insuranceCompanyData } 
          vehicleData={ vehicleData } />
      </div>
    </Card>
  );
};

export default ContractView;
