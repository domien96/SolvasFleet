import React from 'react';
import PermissionControlView from './PermissionControlView.tsx';
import { fetchPermissions, fetchRoles, deleteRole } from '../../actions/auth_actions.ts';
import { redirect_to } from'../../routes/router.tsx';


interface State{
  //Sfunctions : SFunctionData[];
  roles: RoleData[];
  permissions: string[];
}

class PermissionControl extends React.Component<{}, State> {
  constructor(props : any){
    super(props);
    this.state = { roles: [], permissions: [] };
  }

  componentDidMount() {
    //this.fetchFunctions();
    this.fetchRoles();
    this.fetchPermissions();
    this.handleRoleSelect = this.handleRoleSelect.bind(this);
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

  handleRoleSelect(id : number){
    redirect_to(`/auth/roles/${id}/edit`);
  }

  handleRoleDelete(id: number){
    deleteRole(id, this.fetchRoles);
  }

  render(){
    var {roles, permissions} = this.state;
    console.log(this.state)
    return (
      <PermissionControlView roles={ roles } permissions={ permissions } onRoleSelect={ this.handleRoleSelect } onRoleDelete={ this.handleRoleDelete }/>
    );
  }
}

export default PermissionControl;
