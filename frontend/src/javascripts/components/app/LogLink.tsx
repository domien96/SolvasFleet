import React from 'react';
import { Link } from 'react-router';

const LogLink = ({ id, type }: { id: number, type: string }) => {
  return (
    <div className='col-sm-3'>
      <Link to={ `/log?entity=${id}&entityType=${type}` } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-th-list' /> Log
      </Link>
    </div>
  );
};

export default LogLink;
