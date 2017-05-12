import React from 'react';
import T from 'i18n-react';

import Card from './app/Card.tsx';
import FormField from './forms/FormField.tsx';
import { redirect_to } from '../routes/router.tsx';
import { SIGNED_IN_URL } from '../constants/constants.ts';

import { auth_login } from '../actions/auth_actions.ts';
import Auth from '../modules/Auth.ts';

interface State {
  bad_credentials: boolean;
  email: string;
  password: string;
}

interface BCProps {
  shown: boolean
}
const BadCredentials: React.StatelessComponent<BCProps> = ({ shown }) => {
  if (!shown) { return null; }

  return (
    <p className='bg-danger'>You have entered a wrong email/password.</p>
  );
};

class Login extends React.Component<{}, State> {

  constructor() {
    super();
    this.state = { bad_credentials: false, email: null, password: null };

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

    const fail = () => {
      this.setState({ bad_credentials: true });
    }

    auth_login(email, password, s, fail);
  }

  render() {
    return (
      <div className='container vertical-center'>
          <div className='center-block'>
            <Card>
              <div className='card-content'>
                <div className='center-block'>
                  <img className='login-logo' src='http://www.solvas.be/images/logo.png'/>
                </div>
                <div className='error'>
                </div>
                <div className='login-form-wrapper'>
                  <form method='POST' onSubmit={ this.onSubmit } className='login-form' >
                    <BadCredentials shown={ this.state.bad_credentials } />
                    <FormField
                      value={ this.state.email }
                      placeholder='form.placeholders.email'
                      type='email'
                      name='email'
                      callback={ this.handleEmailChange } />
                    <FormField
                      value={ this.state.password }
                      placeholder='form.placeholders.password'
                      type='password'
                      name='password'
                      callback={ this.handlePasswordChange } />
                    <button type='submit' className='btn btn-default' >
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
