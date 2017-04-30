import React from 'react';

import Card from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import RoleTable from '../tables/RoleTable.tsx';
import SimpleTable from '../tables/SimpleTable.tsx';
import T from 'i18n-react';

import { Link } from 'react-router';

interface RoleOverviewProps {
  roles : RoleData[];
  onRoleEdit : (id : number) => void;
  onRoleDelete : (id : number) => void;
}

const RoleOverview : React.StatelessComponent<RoleOverviewProps> = props => {
  const tableHead = [
    th('name',          'role.name')
  ];

  return (
    <RoleTable head={ tableHead } roles={ props.roles } onEdit={ props.onRoleEdit } onDelete={ props.onRoleDelete }/>
  );
}

interface PermissionOverviewProps {
  permissions : string[];
}

const PermissionOverview : React.StatelessComponent<PermissionOverviewProps> = props => {

  var rows : Permission.Data[] = props.permissions.map((permission : string) => {
    let resource;
    if(permission.split(':')[2]){
      resource = `${permission.split(':')[1]} : ${permission.split(':')[2]}`;
    }
    else{
      resource = `${permission.split(':')[1]}`;
    }
    var row : Permission.Data = [permission.split(':')[0], resource];
    return row; 
  });

  return (
    <SimpleTable rows={ rows } />
  );
}

interface Props {
  roles : RoleData[];
  permissions : string[];
  onRoleEdit : (id : number) => void;
  onRoleDelete : (id : number) => void;
}

const PermissionControlView : React.StatelessComponent<Props> = props => {

  return (
    <div className='row'>
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h2>
              { T.translate('role.roles') }
              <Link to={ '/auth/roles/new' } className='btn btn-default pull-right'>
                <span className='glyphicon glyphicon-plus' aria-hidden='true'/> { T.translate('role.addNew') }
              </Link>
            </h2>
          </div>
          <div className='card-content'>
            <RoleOverview roles={ props.roles } onRoleEdit={ props.onRoleEdit } onRoleDelete={ props.onRoleDelete }/>
          </div>
        </Card>
      </div>
      <div className='col-sm-6'>
        <Card>
          <div className='card-title'>
            <h2>
              { T.translate('permission.permissions') }
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
