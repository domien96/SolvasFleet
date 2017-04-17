import React from 'react';

import Card from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';
import { Link } from 'react-router';

interface RoleOverviewProps {
  roles : RoleData[];
  onRoleSelect : (id : number) => void;
}

const RoleOverview : React.StatelessComponent<RoleOverviewProps> = props => {
  const tableHead = [
    th('name',          'role.name'),
    th('permissions',   'role.permissions')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.roles } onClick={ props.onRoleSelect } />
  );
}

interface PermissionOverviewProps {
  permissions : PermissionData[];
  onPermissionSelect : (id : number) => void;
}

const PermissionOverview : React.StatelessComponent<PermissionOverviewProps> = props => {
  const tableHead = [
    th('action',          'permission.action'),
    th('resource',        'permission.resource')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.permissions } onClick={ props.onPermissionSelect } />
  );
}

interface Props {
  roles : RoleData[];
  permissions : PermissionData[];
  onRoleSelect : (id : number) => void;
  onPermissionSelect : (id : number) => void;
}

const PermissionControlView : React.StatelessComponent<Props> = props => {

  return (
    <div>
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h2>
              Roles
              <Link to={ '/auth' } className='btn btn-default pull-right'>
                <span className='glyphicon glyphicon-edit' aria-hidden='true'/> Edit
              </Link>
            </h2>
          </div>
          <div className='card-content'>
            <RoleOverview roles={ props.roles } onRoleSelect={ props.onRoleSelect }/>
          </div>
        </Card>
      </div>
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h2>
              Permissions
              <Link to={ '/auth' } className='btn btn-default pull-right'>
                <span className='glyphicon glyphicon-edit' aria-hidden='true'/> Edit
              </Link>
            </h2>
          </div>
          <div className='card-content'>
            <PermissionOverview permissions={ props.permissions } onPermissionSelect={ props.onPermissionSelect }/>
          </div>
        </Card>
      </div>
    </div>  
  );
}

export default PermissionControlView;
