import React from 'react';
import Header from '../../app/Header.tsx';

import { fetchRoles } from '../../../actions/auth_actions.ts';
import { fetchClients } from '../../../actions/client_actions.ts';
import { postFunction } from '../../../actions/user_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from '../../../routes/router.tsx';
import T from 'i18n-react';

import FunctionForm from './form/FunctionForm.tsx';

interface Props {
  params: { id: string };
}

interface State {
  errors: Form.Error[];
  roles: RoleData[];
  companies: CompanyData[];
  Sfunction: {};
}

class CreateUserFunction extends React.Component<Props, State> {
  constructor() {
    super();
    this.state = { Sfunction: {}, errors: [], roles: [], companies: [] };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    this.fetchRoles();
    this.fetchClients();
  }

  fetchRoles() {
    fetchRoles((data: any) => {
      this.setState({ roles: data.data });
    });
  }

  fetchClients() {
    fetchClients((data: any) => {
      this.setState({ companies: data.data });
    });
  }

  handleChange(field: SFunction.Field, e: any): void {
    const newSfunction: SFunctionData = this.state.Sfunction;
    newSfunction[field] = e.target.value;
    this.setState({ Sfunction: newSfunction });
  }

  onSubmit(e: any): void {
    const newSfunction: SFunctionData = this.state.Sfunction;
    newSfunction.user = parseInt(this.props.params.id, 10);

    if (!newSfunction.role) {
      newSfunction.role = this.state.roles[0].id;
    }

    if (!newSfunction.company) {
      newSfunction.company = -1;
    }
    this.setState({ Sfunction: newSfunction });

    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = () => redirect_to(`/users/${this.props.params.id}`);
    const fail = (data: any) => {
      setErrors(data.errors.map((es: any) => ({ field: es, error: 'null' })));
    };

    postFunction(parseInt(this.props.params.id, 10), newSfunction, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('function.createNew') }</h2>
        </Header>
        <FunctionForm
          Sfunction={ this.state.Sfunction }
          roles ={ this.state.roles }
          companies ={ this.state.companies }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default CreateUserFunction;
