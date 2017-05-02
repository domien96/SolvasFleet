import React from 'react';
import Header from '../../app/Header.tsx';

import { postRole, fetchPermissions } from '../../../actions/auth_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from '../../../routes/router.tsx';
import T from 'i18n-react';
import RoleForm from './form/RoleForm.tsx';

interface State {
  errors: Form.Error[];
  role: RoleData;
  permissions: string[];
}

class CreateRole extends React.Component<{}, State> {
  constructor() {
    super();
    this.state = { role: { name: '', permissions: [] }, errors: [], permissions: [] };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    this.fetchPermissions();
  }

  fetchPermissions() {
    fetchPermissions((data: any) => {
      this.setState({ permissions: data.data });
    });
  }

  handleChange(e: any): any {
    const role: RoleData = this.state.role;
    if (e.target.type === 'checkbox') {
      const permissions = role.permissions.slice();
      if (e.target.checked) {
        permissions.push(e.target.value);
      } else {
        if (permissions.includes(e.target.value)) {
          const index = permissions.indexOf(e.target.value);
          permissions.splice(index, 1);
        }
      }
      role.permissions = permissions;
    } else {
      role.name = e.target.value;
    }
    this.setState({ role });
  }

  onSubmit(e: any): void {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = () => redirect_to(`/auth`);
    const fail = (data: any) => {
      setErrors(data.errors.map((es: any) => ({ field: es, error: 'null' })));
    };

    postRole(this.state.role, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('role.addNew') }</h2>
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
