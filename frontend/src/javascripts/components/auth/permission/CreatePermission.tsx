import React    from 'react';
import Header     from '../../app/Header.tsx';
import UserForm   from './CreatePermissionForm.tsx';

import { postPermission } from '../../../actions/auth_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from'../../../router.tsx';

interface State{
	errors : Form.Error[];
	permission: {}
}

class CreatePermission extends React.Component<{}, State> {
	constructor() {
    super();
    this.state = {permission: {}, errors: []}
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

	public handleChange(field : Permission.Field, e : any) : any {
    var permission : PermissionData = this.state.permission;
    permission[field] = e.target.value;
    this.setState({ permission });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = (data : any) => redirect_to(`/permissions/${data.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    postPermission(this.state.permission, success, fail);
  }

   
  render() {
    return (
      <div>
        <Header>
          <h2>Create a new permission</h2>
        </Header>
        <PermissionForm
          permission={ this.state.permission }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default CreatePermission;

