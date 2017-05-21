import React from 'react';
import { Link } from 'react-router';
import DynamicGuiComponent from './DynamicGuiComponent.tsx';
import Auth from '../../modules/Auth.ts';

const LogLink = ({ id, type }: { id: number, type: string }) => {
  return (
    <DynamicGuiComponent authorized={ Auth.canReadRevisions() }>
      <div className='col-sm-4'>
        <Link to={ `/log?entity=${id}&entityType=${type}` } className='btn btn-default form-control'>
          <span className='glyphicon glyphicon-th-list' /> Log
        </Link>
      </div>
    </DynamicGuiComponent>
  );
};

export default LogLink;
