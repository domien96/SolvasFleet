import React from 'react';
import { Link } from 'react-router';

import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import DownloadButton from '../buttons/DownloadButton.tsx'
import { th } from '../../utils/utils.ts';
import Confirm from 'react-confirm-bootstrap';

const EditLink = ({ id }: { id: number }) => {
  return (
    <div className='col-sm-6'>
      <Link to={ '/vehicles/' + id + '/edit' } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-edit' /> Edit
      </Link>
    </div>
  );
};

const DeleteLink = ({ handleDelete }: { handleDelete: () => void }) => {
  return (
    <div className='col-sm-6'>
      <Confirm
        onConfirm={handleDelete}
        body="Are you sure you want to archive this?"
        confirmText="Confirm Archive"
        title="Archive vehicle">
        <button className='btn btn-danger form-control'>
          <span className='glyphicon glyphicon-remove' /> Archive
        </button>
      </Confirm>
    </div>
  );
};

interface Props {
  handleDelete: () => void;
  vehicle: VehicleData
  onDownloadGreencard: () => void;
}

const VehicleView: React.StatelessComponent<Props> = props => {
  const { id, licensePlate, vin, brand, model, type, mileage, year, leasingCompany, value, fleet } = props.vehicle;

  const data = [
    th('vehicle.fleet', fleet),
    th('vehicle.vin', vin),
    th('vehicle.licensePlate', licensePlate),
    th('vehicle.brand', brand),
    th('vehicle.model', model),
    th('vehicle.type', type),
    th('vehicle.mileage', mileage),
    th('vehicle.year', year),
    th('vehicle.value', value),
    th('vehicle.leasingCompany', leasingCompany),
  ];

  return (
  <Card>
    <div>
      <div className='card-content user'>
        <h2>{ vin } </h2>
        <div className='row actions'>
          <EditLink id={ id } />
          <DeleteLink handleDelete={ props.handleDelete } />
        </div>
      </div>
      <div className='card-content'>
        <DetailTable data={ data }/>
      </div>
      <div className='card-content'>
        <div className='row actions'>
          <DownloadButton onDownload={ props.onDownloadGreencard } label='Download Greencard'/>
        </div>
      </div>
    </div>
  </Card>
  );
};

export default VehicleView;
