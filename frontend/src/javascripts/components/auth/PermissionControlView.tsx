import React from 'react';

import Card from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import RoleTable from '../tables/RoleTable.tsx';
import SimpleTable from '../tables/SimpleTable.tsx';

import { Link } from 'react-router';

interface RoleOverviewProps {
  roles : RoleData[];
  onRoleSelect : (id : number) => void;
  onRoleDelete : (id : number) => void;
}

const RoleOverview : React.StatelessComponent<RoleOverviewProps> = props => {
  const tableHead = [
    th('name',          'role.name')
  ];

  return (
    <RoleTable head={ tableHead } roles={ props.roles } onClick={ props.onRoleSelect } onDelete={ props.onRoleDelete }/>
  );
}

interface PermissionOverviewProps {
  permissions : string[];
}

const PermissionOverview : React.StatelessComponent<PermissionOverviewProps> = props => {

  var rows : Permission.Data[] = props.permissions.map((permission : string) => {
    var row : Permission.Data = [permission.split(':')[0], permission.split(':')[1]];
    return row; 
  });

  return (
    <SimpleTable rows={ rows } />
  );
}

interface Props {
  roles : RoleData[];
  permissions : string[];
  onRoleSelect : (id : number) => void;
  onRoleDelete : (id : number) => void;
}

const PermissionControlView : React.StatelessComponent<Props> = props => {

  return (
    <div>
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h2>
              Roles
              <Link to={ '/auth/roles/new' } className='btn btn-default pull-right'>
                <span className='glyphicon glyphicon-plus' aria-hidden='true'/> Add new Role
              </Link>
            </h2>
          </div>
          <div className='card-content'>
            <RoleOverview roles={ props.roles } onRoleSelect={ props.onRoleSelect } onRoleDelete={ props.onRoleDelete }/>
          </div>
        </Card>
      </div>
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h2>
              Permissions
            </h2>
          </div>
          <div className='card-content'>
            <PermissionOverview permissions={ props.permissions.sort() }/>
          </div>
        </Card>
      </div>
    </div>  
  );
}

export default PermissionControlView;
