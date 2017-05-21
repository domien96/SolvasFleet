import React from 'react';
import { Link } from 'react-router';

import Auth from '../../modules/Auth.ts';
import Card from '../app/Card.tsx';
import DynamicGuiComponent from '../app/DynamicGuiComponent.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import UserFunctions from './function/UserFunctions.tsx';
import { th } from '../../utils/utils.ts';
import Confirm from 'react-confirm-bootstrap';
import LogLink from '../app/LogLink.tsx';

interface Props {
  user: UserData;
  handleDelete: () => void;
  handleUnarchive: () => void;
}

const EditLink = ({ id }: { id: number }) => {
  return (
    <div className='col-sm-4'>
      <Link to={ `/users/${id}/edit` } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-edit' /> Edit
      </Link>
    </div>
  );
};

const DeleteLink = ({ handleDelete }: { handleDelete: () => void }) => {
  return (
    <div className='col-sm-4'>
      <Confirm
        onConfirm={ handleDelete }
        body="Are you sure you want to archive this?"
        confirmText="Confirm Archive"
        title="Archive user">
        <button className='btn btn-danger form-control'>
          <span className='glyphicon glyphicon-remove' /> Archive
        </button>
      </Confirm>
    </div>
  );
};

const UnarchiveLink = ({ handleUnarchive }: { handleUnarchive: () => void }) => {
  return (
    <div className='col-sm-4'>
      <Confirm
        onConfirm={ handleUnarchive }
        body="Are you sure you want to restore this?"
        confirmText="Confirm Unarchive"
        title="Unarchive user">
        <button className='btn btn-success form-control'>
          <span className='glyphicon glyphicon-share-alt' /> Unarchive
        </button>
      </Confirm>
    </div>
  );
};

const UserCard: React.StatelessComponent<Props> = props => {
  const { id, firstName, lastName, email, archived } = props.user;

  const data = [
    th('user.firstName', firstName),
    th('user.lastName', lastName),
    th('user.email', email),
  ];

  let deleteLink = <DeleteLink handleDelete={ props.handleDelete } />
  if (archived) {
    deleteLink = <UnarchiveLink handleUnarchive={ props.handleUnarchive } />
  }

  return (
    <Card>
      <div className='card-content user'>
        <h2>{ firstName } { lastName }</h2>
        <div className='row actions'>
          <DynamicGuiComponent authorized={ Auth.canWriteUsers() }>
            <EditLink id={ id } />
            <DeleteLink handleDelete={ props.handleDelete } />
          </DynamicGuiComponent>
          <LogLink id={ id } type='User' />
        </div>
      </div>
      <div className='card-content'>
        <DetailTable data={ data }/>
        <UserFunctions user={ props.user }/>
      </div>
    </Card>
  );
};

export default UserCard;
