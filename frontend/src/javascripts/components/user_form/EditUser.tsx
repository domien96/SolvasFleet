import React from 'react';

import Header from '../app/Header.tsx';
import UserForm from './UserForm.tsx';

import { fetchUser, putUser } from '../../actions/user_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from '../../routes/router.tsx';
import T from 'i18n-react';
import Errors from '../../modules/Errors.ts';

interface Props {
  params: { id: number };
  fetchUsers: () => void;
}

interface State {
  errors: Form.Error[];
  user: UserData;
}

class EditUser extends React.Component<Props, State> {
  constructor() {
    super();
    this.state = {
      errors: [],
      user: {},
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    fetchUser(this.props.params.id, (data: any) => this.setState({ user: data }));
  }

  handleChange(field: User.Field, e: any): any {
    const user: UserData = this.state.user;
    user[field] = e.target.value;
    this.setState({ user });
  }

  onSubmit(e: any): void {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = () => redirect_to(`/users/${this.state.user.id}`);

    putUser(this.state.user.id, this.state.user, success, Errors.handle(setErrors));
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('user.edit') }</h2>
        </Header>
        <UserForm
          user={ this.state.user }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    );
  }
}

export default EditUser;
