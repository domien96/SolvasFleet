import React from 'react';
import { Link } from 'react-router';

const LogLink = ({ id }: { id: number }) => {
  return (
    <div className='col-sm-4'>
      <Link to={ `/log?entity=${id}` } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-th-list' /> Log
      </Link>
    </div>
  );
};

export default LogLink;
