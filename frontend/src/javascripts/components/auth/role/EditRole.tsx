import React    from 'react';
import Header     from '../../app/Header.tsx';

import { putRole, fetchPermissions, fetchRole } from '../../../actions/auth_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from'../../../routes/router.tsx';

import RoleForm from './form/RoleForm.tsx'

interface Props {
  params : { id : number };
  fetchRoles : () => void;
}

interface State{
	errors : Form.Error[];
	role: RoleData;
  permissions: string[];
}

class EditRole extends React.Component<Props, State> {
	constructor() {
    super();
    this.state = {role: {}, errors: [], permissions: []}
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount(){
    this.fetchRole(this.props.params.id);
    this.fetchPermissions();
  }

  fetchPermissions(){
    fetchPermissions((data : any) => {
      this.setState({ permissions: data.data })
    });
  }

  fetchRole(id: number){
    fetchRole(id, (data : any) => {
      this.setState({ role: data })
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
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = () => redirect_to('/auth');
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    putRole(this.props.params.id, this.state.role, success, fail);
  }

   
  render() {
    return (
      <div>
        <Header>
          <h2>Edit role {this.state.role.name}</h2>
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

export default EditRole;
