/// <reference path="../types/interfaces.d.ts"/>
import React from 'react';
import T from 'i18n-react';

import FormField from './FormField.tsx';

class AddRole extends React.Component<RoleProps, RoleState> {

  constructor(props : RoleProps) {
    super(props);
    this.state = { errors: [], company: null, permission: null, user: null, startDate: null, endDate: null };

    this.handleUserChange    = this.handleUserChange.bind(this);
    this.handleCompanyChange = this.handleCompanyChange.bind(this);
    this.handlePermissionChange = this.handlePermissionChange.bind(this);
    this.handleStartDateChange = this.handleStartDateChange.bind(this);
    this.handleEndDateChange = this.handleEndDateChange.bind(this);

    this.onSubmit             = this.onSubmit.bind(this);
  }

  public getErrors() : FormError[] {
    var errors : FormError[] = [];

    if (this.state.user == null || this.state.user == '') {
      errors.push({ field: 'user', error: 'is empty'});
    }

    if (this.state.company == null || this.state.company == '') {
      errors.push({ field: 'company', error: 'is empty'});
    }

    if (this.state.permission == null || this.state.permission == '') {
      errors.push({ field: 'permission', error: 'is empty'});
    }

    if (this.state.startDate == null || this.state.startDate == '') {
      errors.push({ field: 'startDate', error: 'is empty'});
    }

    if (this.state.endDate == null || this.state.endDate == '') {
      errors.push({ field: 'endDate', error: 'is empty'});
    }

    return errors;
  }

  public handleUserChange(e : any) : void {
    this.setState({ user: e.target.value });
  }

  public handleCompanyChange(e : any) : void {
    this.setState({ company: e.target.value });
  }

  public handlePermissionChange(e : any) : void {
    this.setState({ permission: e.target.value });
  }

  public handleStartDateChange(e : any) : void {
    this.setState({ startDate: e.target.value });
  }

  public handleEndDateChange(e : any) : void {
    this.setState({ endDate: e.target.value });
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
            <div className='addRole-form-wrapper'>
              <form method='POST' onSubmit={this.onSubmit} className='addRole-form' >

                <FormField placeholder='form.placeholders.name'    type='text'     callback={ this.handleUserChange    } hasError={ this.hasError('user')}    />
                <FormField placeholder='form.placeholders.company' type='text' callback={ this.handleCompanyChange } hasError={ this.hasError('company')} />
                <FormField placeholder='form.placeholders.permission' type='text' callback={ this.handlePermissionChange } hasError={ this.hasError('permission')} />
                
                <div className='addRole-form-row'>
                  <div className='addRole-form-row-label'>
                  <T.text text='addRole.startDate' />
                  </div>
                  <div className='addRole-form-row-input'>
                    <FormField placeholder='form.placeholders.startDate' type='date' callback={ this.handleStartDateChange } hasError={ this.hasError('startDate')} />
                  </div>
                </div>

                <div className='addRole-form-row'>
                <div className='addRole-form-row-label'>
                  <T.text text='addRole.endDate' />
                  </div>
                  <div className='addRole-form-row-input'>
                    <FormField placeholder='form.placeholders.endDate' type='date' callback={ this.handleEndDateChange } hasError={ this.hasError('endDate')} />
                  </div>
                </div>  
                    
                <button type='submit' className='btn btn-default'> 
                  <T.text tag='span' text='addRole.submit' />
                </button>

              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AddRole;
