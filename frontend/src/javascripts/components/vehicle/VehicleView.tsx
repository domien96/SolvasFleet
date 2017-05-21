import React from 'react';
import { Link } from 'react-router';

import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import DownloadButton from '../buttons/DownloadButton.tsx';
import { th } from '../../utils/utils.ts';
import Confirm from 'react-confirm-bootstrap';
import LogLink from '../app/LogLink.tsx';

const EditLink = ({ id }: { id: number }) => {
  return (
    <div className='col-sm-3'>
      <Link to={ '/vehicles/' + id + '/edit' } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-edit' /> Edit
      </Link>
    </div>
  );
};

const DeleteLink = ({ handleDelete }: { handleDelete: () => void }) => {
  return (
    <div className='col-sm-3'>
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

const UnarchiveLink = ({ handleUnarchive }: { handleUnarchive: () => void }) => {
  return (
    <div className='col-sm-3'>
      <Confirm
        onConfirm={ handleUnarchive }
        body="Are you sure you want to restore this?"
        confirmText="Confirm Unarchive"
        title="Unarchive vehicle">
        <button className='btn btn-success form-control'>
          <span className='glyphicon glyphicon-share-alt' /> Unarchive
        </button>
      </Confirm>
    </div>
  );
};

interface Props {
  handleDelete: () => void;
  vehicle: VehicleData
  onDownloadGreencard: () => void;
  onGetFleetName: (id: number) => string;
  onGetCompanyName: (id: number) => any;
  handleUnarchive: () => void;
}

const VehicleView: React.StatelessComponent<Props> = props => {
  const { id, licensePlate, vin, brand, model, type, mileage, year, leasingCompany, value, fleet, archived } = props.vehicle;

  let fleetName : number | string = fleet;
  if (fleet) {
    fleetName = `${fleet}: ${props.onGetFleetName(fleet)}`;
  }

  let companyName : number | string = leasingCompany;
  if (leasingCompany) {
    companyName = `${leasingCompany}: ${props.onGetCompanyName(leasingCompany)}`;
  }

  const data = [
    th('vehicle.fleet', fleetName),
    th('vehicle.vin', vin),
    th('vehicle.licensePlate', licensePlate),
    th('vehicle.brand', brand),
    th('vehicle.model', model),
    th('vehicle.type', type),
    th('vehicle.mileage', mileage),
    th('vehicle.year', year),
    th('vehicle.value', value),
    th('vehicle.leasingCompany', companyName)
  ];

  let deleteLink = <DeleteLink handleDelete={ props.handleDelete } />
  if (archived) {
    deleteLink = <UnarchiveLink handleUnarchive={ props.handleUnarchive } />
  }

  return (
  <Card>
    <div>
      <div className='card-content user'>
        <h2>{ vin } </h2>
        <div className='row actions'>
          <EditLink id={ id } />
          { deleteLink }
          <LogLink id={ id } type='Vehicle' />
          <div className='col-sm-3'>
            <Link to={ `/commissions/clients/4/fleets/${fleet}/vehicles/${id}/${type}` } className='btn btn-info form-control'>
              <span className='glyphicon glyphicon-euro' /> Commissions
            </Link>
          </div>
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
