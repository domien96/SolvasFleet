/// <reference path="../types/interfaces.d.ts"/>
import React from 'react';
import T from 'i18n-react';

import FormField from './FormField.tsx';

class AddCompany extends React.Component<CompanyProps, CompanyState> {

  constructor(props : CompanyProps) {
    super(props);
    this.state = { errors: [], name: null, vatNumber: null, phoneNumber: null, address: null };

    this.handleNameChange    = this.handleNameChange.bind(this);
    this.handleVatNumberChange = this.handleVatNumberChange.bind(this);
    this.handlePhoneNumberChange = this.handlePhoneNumberChange.bind(this);
    this.handleAddressChange = this.handleAddressChange.bind(this);
    this.onSubmit             = this.onSubmit.bind(this);
  }

  public getErrors() : FormError[] {
    var errors : FormError[] = [];

    if (this.state.name == null || this.state.name == '') {
      errors.push({ field: 'name', error: 'is empty'});
    }

    if (this.state.vatNumber == null || this.state.vatNumber == '') {
      errors.push({ field: 'vatNumber', error: 'is empty'});
    }

    if (this.state.phoneNumber == null || this.state.phoneNumber == '') {
      errors.push({ field: 'phoneNumber', error: 'is empty'});
    }

    if (this.state.address == null || this.state.address == '') {
      errors.push({ field: 'address', error: 'is empty'});
    }

    //TODO: check correct format of VAT number
    //TODO: might be needed to change phone number to string or add a '+' sign to the form

    return errors;
  }

  public handleNameChange(e : any) : void {
    this.setState({ name: e.target.value });
  }

  public handleVatNumberChange(e : any) : void {
    this.setState({ vatNumber: e.target.value });
  }

  public handlePhoneNumberChange(e : any) : void {
    this.setState({ phoneNumber: e.target.value });
  }

  public handleAddressChange(e : any) : void {
    this.setState({ address: e.target.value });
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

  //TODO: might be more elegant to divide address into street, number, city, country

  render() {
    return (
      <div className='container'>
        <div className='row'>
          <div className='col-xs-12 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4'>
            <div className='addCompany-form-wrapper'>
              <form method='POST' onSubmit={this.onSubmit} className='addCompany-form' >
                <FormField placeholder='form.placeholders.name'    type='text'     callback={ this.handleNameChange    } hasError={ this.hasError('name')}    />
                <FormField placeholder='form.placeholders.vatNumber' type='text' callback={ this.handleVatNumberChange } hasError={ this.hasError('vatNumber')} />
                <FormField placeholder='form.placeholders.phoneNumber' type='number' callback={ this.handlePhoneNumberChange } hasError={ this.hasError('phoneNumber')} />
                <FormField placeholder='form.placeholders.address' type='text' callback={ this.handleAddressChange } hasError={ this.hasError('address')} />
                <button type='submit' className='btn btn-default'> 
                  <T.text tag='span' text='addCompany.submit' />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AddCompany;
