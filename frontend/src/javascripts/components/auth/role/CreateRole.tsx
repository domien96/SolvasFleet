import React    from 'react';
import Header     from '../../app/Header.tsx';
import UserForm   from './CreatePermissionForm.tsx';

import { postPermission } from '../../../actions/auth_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from'../../../router.tsx';

interface State{
	errors : Form.Error[];
	role: RoleData;
}

class CreateRole extends React.Component<{}, State> {
	constructor() {
    super();
    this.state = {role: {}, errors: []}
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

	public handleChange(field : Permission.Field, e : any) : any {
    var role : RoleData = this.state.role;
    role[field] = e.target.value;
    this.setState({ role });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = (data : any) => redirect_to(`/roles/${data.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    postRole(this.state.role, success, fail);
  }

   
  render() {
    return (
      <div>
        <Header>
          <h2>Create a new role</h2>
        </Header>
        <RoleForm
          role={ this.state.role }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default CreatePRole;

