/// <reference path="../types/interfaces.d.ts"/>
import React from 'react';
import T from 'i18n-react';

import FormField from './forms/FormField.tsx';

class AddVehicle extends React.Component<VehicleProps, VehicleState> {

  constructor(props : VehicleProps) {
    super(props);
    this.state = { errors: [], licencePlate: null, chassisNumber: null, brand: null, model: null, type: null, kmCount: null, year: null, leasingCompany: null, value: null, company: null };

    this.handleLicencePlateChange    = this.handleLicencePlateChange.bind(this);
    this.handleChassisNumberChange = this.handleChassisNumberChange.bind(this);
    this.handleBrandChange = this.handleBrandChange.bind(this);
    this.handleModelChange = this.handleModelChange.bind(this);
    this.handleTypeChange    = this.handleTypeChange.bind(this);
    this.handleKmCountChange = this.handleKmCountChange.bind(this);
    this.handleYearChange = this.handleYearChange.bind(this);
    this.handleLeasingCompanyChange = this.handleLeasingCompanyChange.bind(this);
    this.handleValueChange = this.handleValueChange.bind(this);
    this.handleCompanyChange = this.handleCompanyChange.bind(this);
    this.onSubmit             = this.onSubmit.bind(this);
  }

 
  public handleLicencePlateChange(e : any) : void {
    this.setState({ licencePlate: e.target.value });
  }

  public handleChassisNumberChange(e : any) : void {
    this.setState({ chassisNumber: e.target.value });
  }

  public handleBrandChange(e : any) : void {
    this.setState({ brand: e.target.value });
  }

  public handleModelChange(e : any) : void {
    this.setState({ model: e.target.value });
  }

  public handleTypeChange(e : any) : void {
    this.setState({ type: e.target.value });
  }

  public handleKmCountChange(e : any) : void {
    this.setState({ kmCount: e.target.value });
  }

  public handleYearChange(e : any) : void {
    this.setState({ year: e.target.value });
  }

  public handleLeasingCompanyChange(e : any) : void {
    this.setState({ leasingCompany: e.target.value });
  }

  public handleValueChange(e : any) : void {
    this.setState({ value: e.target.value });
  }

  public handleCompanyChange(e : any) : void {
    this.setState({ company: e.target.value });
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
            <div className='addVehicle-form-wrapper'>
              <form method='POST' onSubmit={this.onSubmit} className='addVehicle-form' >
                <FormField placeholder='form.placeholders.licencePlate' type='text' callback={ this.handleLicencePlateChange } hasError={ this.hasError('licencePlate')}    />
                <FormField placeholder='form.placeholders.chassisNumber' type='text' callback={ this.handleChassisNumberChange } hasError={ this.hasError('chassisNumber')} />
                <FormField placeholder='form.placeholders.brand' type='text' callback={ this.handleBrandChange } hasError={ this.hasError('brand')} />
                <FormField placeholder='form.placeholders.model' type='text' callback={ this.handleModelChange } hasError={ this.hasError('model')} />
                <FormField placeholder='form.placeholders.type' type='text' callback={ this.handleTypeChange } hasError={ this.hasError('type')} />
                <FormField placeholder='form.placeholders.kmCount' type='number' callback={ this.handleKmCountChange } hasError={ this.hasError('kmCount')} />
                <FormField placeholder='form.placeholders.year' type='number' callback={ this.handleYearChange } hasError={ this.hasError('year')} />
                <FormField placeholder='form.placeholders.leasingCompany' type='text' callback={ this.handleLeasingCompanyChange } hasError={ this.hasError('leasingCompany')} />
                <FormField placeholder='form.placeholders.value' type='number' callback={ this.handleValueChange }  hasError={ this.hasError('value')} />
                <FormField placeholder='form.placeholders.company' type='text' callback={ this.handleCompanyChange } hasError={ this.hasError('company')} />
                <button type='submit' className='btn btn-default'> 
                  <T.text tag='span' text='addVehicle.submit' />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AddVehicle;
