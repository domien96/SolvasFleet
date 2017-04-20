import React from 'react';
import { Link } from 'react-router';

import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import UserFunctions from './function/UserFunctions.tsx';
import { th } from '../../utils/utils.ts';

interface Props {
  user : UserData;
  handleDelete : () => void;
}

const EditLink = ({ id } : { id : number }) => {
  return (
    <div className='col-sm-6'>
      <Link to={ `/users/${id}/edit` } className='btn btn-default form-control'>
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

const UserCard : React.StatelessComponent<Props> = props => {
  var { id, firstName, lastName, email } = props.user;

  const data = [
    th('user.firstName', firstName),
    th('user.lastName',  lastName),
    th('user.email',     email)
  ];

  return (
    <Card>
      <div className='card-content user'>
        <h2>{ firstName } { lastName }</h2>
        <div className='row actions'>
          <EditLink id={ id } />
          <DeleteLink handleDelete={ props.handleDelete } />
        </div>
      </div>
      <div className='card-content'>
        <DetailTable data={ data }/>
        <UserFunctions user={ props.user }/>
      </div>
    </Card>
  );
}

export default UserCard;
