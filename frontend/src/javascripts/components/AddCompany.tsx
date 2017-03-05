import React from 'react';

import FormField from './forms/FormField.tsx';

class AddCompany extends React.Component<CompanyProps, CompanyState> {

  constructor(props : CompanyProps) {
    super(props);
    this.state = { errors: [], name: null, vat_number: null, phone_number: null, address: null };

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

    if (this.state.vat_number == null || this.state.vat_number == '') {
      errors.push({ field: 'vat_number', error: 'is empty'});
    }

    if (this.state.phone_number == null || this.state.phone_number == '') {
      errors.push({ field: 'phone_number', error: 'is empty'});
    }

    if (this.state.address == null || this.state.address == '') {
      errors.push({ field: 'address', error: 'is empty'});
    }

    //TODO: check correct format of VAT_number
    //TODO: might be needed to change phone_number to string or add a '+' sign to the form

    return errors;
  }

  public handleNameChange(e : any) : void {
    this.setState({ name: e.target.value });
  }

  public handleVatNumberChange(e : any) : void {
    this.setState({ vat_number: e.target.value });
  }

  public handlePhoneNumberChange(e : any) : void {
    this.setState({ phone_number: e.target.value });
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
    //TODO : submit

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
                <FormField placeholder='name'    type='text'     callback={ this.handleNameChange    } hasError={ this.hasError('name')}    />
                <FormField placeholder='VAT number' type='text' callback={ this.handleVatNumberChange } hasError={ this.hasError('vat_number')} />
                <FormField placeholder='phone number' type='number' callback={ this.handlePhoneNumberChange } hasError={ this.hasError('phone_number')} />
                <FormField placeholder='address' type='text' callback={ this.handleAddressChange } hasError={ this.hasError('address')} />
                <input type='submit' value='Add' className='btn btn-default' />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AddCompany;
