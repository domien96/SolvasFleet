/// <reference path="../types/interfaces.d.ts"/>
import React from 'react';
import T from 'i18n-react';

import FormField from './FormField.tsx';

class AddUser extends React.Component<UserProps, UserState> {

  constructor(props : UserProps) {
    super(props);
    this.state = { errors: [], firstName: null, lastName: null, email: null, password: null };

    this.handleFirstNameChange    = this.handleFirstNameChange.bind(this);
    this.handleLastNameChange = this.handleLastNameChange.bind(this);
    this.handleEmailChange = this.handleEmailChange.bind(this);
    this.handlePasswordChange = this.handlePasswordChange.bind(this);
    this.onSubmit             = this.onSubmit.bind(this);
  }

  public getErrors() : FormError[] {
    var errors : FormError[] = [];

    if (this.state.firstName == null || this.state.firstName == '') {
      errors.push({ field: 'firstName', error: 'is empty'});
    }

    if (this.state.lastName == null || this.state.lastName == '') {
      errors.push({ field: 'lastName', error: 'is empty'});
    }

    if (this.state.email == null || this.state.email == '') {
      errors.push({ field: 'email', error: 'is empty'});
    }

    if (this.state.password == null || this.state.password == '') {
      errors.push({ field: 'password', error: 'is empty'});
    }

    return errors;
  }

  public handleFirstNameChange(e : any) : void {
    this.setState({ firstName: e.target.value });
  }

  public handleLastNameChange(e : any) : void {
    this.setState({ lastName: e.target.value });
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
    const errors = this.state.errors.filter(function(el) {return el.field == k; });
    return (errors.length != 0);
  }

  render() {
    return (
      <div className='container'>
        <div className='row'>
          <div className='col-xs-12 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4'>
            <div className='addUser-form-wrapper'>
              <form method='POST' onSubmit={this.onSubmit} className='addUser-form' >
                <FormField placeholder='form.placeholders.firstName'    type='text'     callback={ this.handleFirstNameChange    } hasError={ this.hasError('firstName')}    />
                <FormField placeholder='form.placeholders.lastName' type='text' callback={ this.handleLastNameChange } hasError={ this.hasError('lastName')} />
                <FormField placeholder='form.placeholders.email' type='email' callback={ this.handleEmailChange } hasError={ this.hasError('email')} />
                <FormField placeholder='form.placeholders.password' type='password' callback={ this.handlePasswordChange } hasError={ this.hasError('password')} />
                <button type='submit' className='btn btn-default'> 
                  <T.text tag='span' text='addUser.submit' />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AddUser;
