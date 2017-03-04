/// <reference path="../types/interfaces.d.ts"/>
import React from 'react';

import FormField from './FormField.tsx';

class Login extends React.Component<LoginProps, LoginState> {

  constructor(props : LoginProps) {
    super(props);
    this.state = { errors: [], email: null, password: null };

    this.handleEmailChange    = this.handleEmailChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.onSubmit             = this.onSubmit.bind(this);
  }

  public getErrors() : FormError[] {
    var errors : FormError[] = [];

    if (this.state.email == null || this.state.email == '') {
      errors.push({ field: 'email', error: 'is empty'});
    }

    if (this.state.password == null || this.state.password == '') {
      errors.push({ field: 'password', error: 'is empty'});
    }

    return errors;
  }

  public handleEmailChange(e : any) : void {
    this.setState({ email: e.target.value });
  }

  public handlePasswordChange(e : any) : void {
    this.setState({ password: e.target.value });
  }

  public onSubmit(e : any) : void {
    const errors = this.getErrors();
    if (errors.length != 0) {
      e.preventDefault();
      this.setState({ errors: errors });
    }
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
                <FormField field='email'    type='email'     callback={ this.handleEmailChange    } hasError={ this.hasError('email')}    />
                <FormField field='password' type='password' callback={ this.handlePasswordChange } hasError={ this.hasError('password')} />
                <input type='submit' value='Login' className='btn btn-default' />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;
