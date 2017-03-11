import React from 'react';
import T from 'i18n-react';

import FormField from './forms/FormField.tsx';

class Login extends React.Component<LoginProps, LoginState> {

  constructor(props : LoginProps) {
    super(props);
    this.state = { errors: [], email: null, password: null };

    this.handleEmailChange    = this.handleEmailChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.onSubmit             = this.onSubmit.bind(this);
  }

  public handleEmailChange(e : any) : void {
    this.setState({ email: e.target.value });
  }

  public handlePasswordChange(e : any) : void {
    this.setState({ password: e.target.value });
  }

  public onSubmit() : void {
  }

  public hasError(k : string) : boolean {
    const errors = this.state.errors.filter(function(el) { return el.field == k; });
    return (errors.length != 0);
  }

  render() {
    return (
      <div className='container'>
        <div className='row'>
          <div className='col-xs-12 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4'>
            <div className='login-form-wrapper'>
              <form method='POST' onSubmit={this.onSubmit} className='login-form' >
                <FormField placeholder='form.placeholders.email'    type='email'     callback={ this.handleEmailChange    } hasError={ this.hasError('email')}    />
                <FormField placeholder='form.placeholders.password' type='password' callback={ this.handlePasswordChange } hasError={ this.hasError('password')} />
                <button type='submit' className='btn btn-default' >
                  <T.text tag='span' text='login.submit' />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;
