import React from 'react';
import T from 'i18n-react';

import Card from './app/Card.tsx';
import FormField from './forms/FormField.tsx';
import { redirect_to } from '../routes/router.tsx';
import { SIGNED_IN_URL } from '../constants/constants.ts';

import { auth_login } from '../actions/auth_actions.ts';
import Auth from '../modules/Auth.ts';

interface State {
  errors: Form.Error[];
  email: string;
  password: string;
}

class Login extends React.Component<{}, State> {

  constructor() {
    super();
    this.state = { errors: [], email: null, password: null };

    this.handleEmailChange    = this.handleEmailChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.onSubmit             = this.onSubmit.bind(this);
  }

  public handleEmailChange(e: any): void {
    this.setState({ email: e.target.value });
  }

  public handlePasswordChange(e: any): void {
    this.setState({ password: e.target.value });
  }

  public onSubmit(e: any): void {
    e.preventDefault();

    const { email, password } = this.state;

    const s = (data: any) => {
      Auth.authenticateUser(
        data.refreshToken.token,
        data.accessToken.token
      );

      redirect_to(SIGNED_IN_URL);
    };

    auth_login(email, password, s);
  }

  public hasError(k: string): boolean {
    const errors = this.state.errors.filter((el) => (el.field === k));
    return (errors.length !== 0);
  }

  render() {
    return (
      <div className='container vertical-center'>
          <div className='center-block'>
            <Card>
              <div className='card-content'>
                <div className='center-block'>
                  <img className='login-logo' src="http://www.solvas.be/images/logo.png"/>
                </div>
                <div className="error">
                </div>
                <div className='login-form-wrapper'>
                  <form method='POST' onSubmit={this.onSubmit} className='login-form clearfix' >
                    <FormField
                      value={ this.state.email }
                      placeholder='form.placeholders.email'
                      type='email'
                      name='email'
                      callback={ this.handleEmailChange }
                      hasError={ this.hasError('email')} />
                    <FormField
                      value={ this.state.password }
                      placeholder='form.placeholders.password'
                      type='password'
                      name='password'
                      callback={ this.handlePasswordChange }
                      hasError={ this.hasError('password')} />
                    <button type='submit' className='btn btn-default btn-primary center-block login-button' >
                      <T.text tag='span' text='login.submit' />
                    </button>
                  </form>
                </div>
              </div>
            </Card>
        </div>
      </div>
    );
  }
}

export default Login;
