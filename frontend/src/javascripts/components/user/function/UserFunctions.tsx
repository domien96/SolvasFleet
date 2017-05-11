import React from 'react';
import UserFunctionsView from './UserFunctionsView.tsx';
import { fetchFunctionsByUser, deleteFunction } from '../../../actions/user_actions.ts';
import { fetchClients } from '../../../actions/client_actions.ts';
import { fetchRoles } from '../../../actions/auth_actions.ts';
import T from 'i18n-react';

interface Props {
  user: UserData;
}

interface State {
  Sfunctions: SFunctionData[];
  roles: RoleData[];
  companies: CompanyData[];
}

class UserFunctions extends React.Component<Props, State> {
  constructor(props: any) {
    super(props);
    this.state = { Sfunctions: [], roles: [], companies: [] };
    this.handleFunctionDelete = this.handleFunctionDelete.bind(this);
    this.handleGetCompanyName = this.handleGetCompanyName.bind(this);
    this.handleGetRoleName = this.handleGetRoleName.bind(this);
  }

  componentDidMount() {
    if (this.props.user.id) {
      this.fetchFunctions(this.props.user.id);
    }
    this.fetchClients();
    this.fetchRoles();
  }

  componentWillReceiveProps(nextProps: any) {
    if (nextProps.user.id !== this.props.user.id) {
      this.fetchFunctions(nextProps.user.id);
    }
  }

  fetchFunctions(id: number) {
    fetchFunctionsByUser(id, ((data: SFunctions.Data) => {
      this.setState({ Sfunctions: data.data });
    }));
  }

  fetchClients() {
    fetchClients((data: Companies.Data) => {
      this.setState({ companies: data.data });
    });
  }

  fetchRoles() {
    fetchRoles((data: Roles.Data) => {
      this.setState({ roles: data.data });
    });
  }

  handleFunctionDelete(functionId: number) {
    const success = () => this.fetchFunctions(this.props.user.id);
    deleteFunction(this.props.user.id, functionId, success);
  }

  handleGetCompanyName(id: number) {
    if (id === -1) {
      return T.translate('company.allCompanies');
    }
    if (this.state.companies.length > 0) {
      const companiesFiltered = this.state.companies.filter((c: CompanyData) => {
        return c.id === id;
      });
      return companiesFiltered[0].name;
    }
    else {
      return id;
    }
  }

  handleGetRoleName(id: number) {
    if (this.state.roles.length > 0) {
      const rolesFiltered = this.state.roles.filter((r: RoleData) => {
        return r.id === id;
      });
      return rolesFiltered[0].name;
    }
    else {
      return id;
    }
  }

  render() {
    return (
      <UserFunctionsView
        userId={ this.props.user.id }
        Sfunctions={ this.state.Sfunctions }
        onFunctionDelete={ this.handleFunctionDelete }
        onGetCompanyName={ this.handleGetCompanyName }
        onGetRoleName={ this.handleGetRoleName } />
    );
  }
}

export default UserFunctions;
