/// <reference path="../types/interfaces.d.ts"/>
import React from 'react';
import T from 'i18n-react';

import FormField from './forms/FormField.tsx';

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

  public onSubmit() : void {

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
              <div className='addRole-form-padding'>
                <form method='POST' onSubmit={this.onSubmit} className='form-horizontal' >

                  <div className='form-group'>
                    <label className='col-sm-4 roleLabel'>
                      <T.text text='form.placeholders.name' />
                    </label>
                    <div className='col-sm-8'>
                      <FormField placeholder='form.placeholders.name'    type='text'     callback={ this.handleUserChange    } hasError={ this.hasError('user')}    />
                    </div>
                  </div>

                  <div className='form-group'>
                    <label className='col-sm-4 roleLabel'>
                      <T.text text='form.placeholders.company' />
                    </label>
                    <div className='col-sm-8'>
                      <FormField placeholder='form.placeholders.company' type='text' callback={ this.handleCompanyChange } hasError={ this.hasError('company')} />
                    </div>
                  </div>

                  <div className='form-group'>
                    <label className='col-sm-4 roleLabel'>
                      <T.text text='form.placeholders.permissionShort' />
                    </label>
                    <div className='col-sm-8'>
                      <FormField placeholder='form.placeholders.permission' type='text' callback={ this.handlePermissionChange } hasError={ this.hasError('permission')} />
                    </div>
                  </div>

                  <div className='form-group'>
                    <label className='col-sm-4 roleLabel'>
                    <T.text text='addRole.startDate' />
                    </label>
                    <div className='col-sm-8'>
                      <FormField placeholder='form.placeholders.startDate' type='date' callback={ this.handleStartDateChange } hasError={ this.hasError('startDate')} />
                    </div>
                  </div>

                  <div className='form-group'>
                    <label className='col-sm-4 roleLabel'>
                    <T.text text='addRole.endDate' />
                    </label>
                    <div className='col-sm-8'>
                    <FormField placeholder='form.placeholders.endDate' type='date' callback={ this.handleEndDateChange } hasError={ this.hasError('endDate')} />
                    </div>
                  </div>
        
                  <div className='form-group'>
                      
                      <button type='submit' className='btn btn-default'> 
                        <T.text tag='span' text='addRole.submit' />
                      </button>
                    
                  </div>

                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AddRole;
