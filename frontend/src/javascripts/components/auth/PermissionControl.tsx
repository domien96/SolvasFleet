import React from 'react';
import PermissionControlView from './PermissionControlView.tsx';
import { fetchPermissions, fetchRoles, deleteRole } from '../../actions/auth_actions.ts';
import { redirect_to } from'../../routes/router.tsx';

interface State{
  roles: RoleData[];
  permissions: string[];
}

class PermissionControl extends React.Component<{}, State> {
  constructor(props : any){
    super(props);
    this.state = { roles: [], permissions: [] };
  }

  componentDidMount() {
    this.fetchRoles();
    this.fetchPermissions();
    this.handleRoleEdit = this.handleRoleEdit.bind(this);
    this.handleRoleDelete = this.handleRoleDelete.bind(this);
  }

  fetchRoles(){
    fetchRoles((data : Roles.Data) => {
      this.setState({ roles: data.data })
    });
  }

  fetchPermissions(){
    fetchPermissions((data : any) => {
      this.setState({ permissions: data.data })
    });
  }

  handleRoleEdit(id : number){
    redirect_to(`/auth/roles/${id}/edit`);
  }

  handleRoleDelete(id: number){
    console.log(id);
    deleteRole(id, () => redirect_to('/auth'));
  }

  render(){
    var {roles, permissions} = this.state;
    return (
      <PermissionControlView roles={ roles } permissions={ permissions } onRoleEdit={ this.handleRoleEdit } onRoleDelete={ this.handleRoleDelete }/>
    );
  }
}

export default PermissionControl;
