import React    from 'react';
import Header     from '../../app/Header.tsx';

import { postRole, fetchPermissions } from '../../../actions/auth_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from'../../../routes/router.tsx';

import RoleForm from './form/RoleForm.tsx'

interface State{
	errors : Form.Error[];
	role: RoleData;
  permissions: string[];
}

class CreateRole extends React.Component<{}, State> {
	constructor() {
    super();
    this.state = {role: {name:'', permissions:[]}, errors: [], permissions: []}
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount(){
    this.fetchPermissions();
  }

  fetchPermissions(){
    fetchPermissions((data : any) => {
      this.setState({ permissions: data.data })
    });
  }

	handleChange(e : any) : any {
    var role : RoleData = this.state.role;
    if(e.target.type == 'checkbox'){
      let permissions = role['permissions'].slice();
      if(e.target.checked){
        permissions.push(e.target.value);
      }
      else{        
        if(permissions.includes(e.target.value)){
          let index = permissions.indexOf(e.target.value)
          permissions.splice(index, 1);
        }
      }
      role['permissions'] = permissions;
    }
    else{
      role['name'] = e.target.value;
    }
    this.setState({ role });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    console.log(this.state);
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = () => redirect_to(`/auth`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    postRole(this.state.role, success, fail);
  }

   
  render() {
    console.log(this.state)
    return (
      <div>
        <Header>
          <h2>Create a new role</h2>
        </Header>
        <RoleForm
          role={ this.state.role }
          permissions ={ this.state.permissions }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default CreateRole;

