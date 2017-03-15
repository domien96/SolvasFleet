import React    from 'react';
import T        from 'i18n-react';
import { Link } from 'react-router';

import Card       from '../app/Card.tsx';
import Errors     from '../app/Errors.tsx';
import FormField  from '../forms/FormField.tsx';
import FormChoice from '../forms/FormChoice.tsx';

import { th } from '../../utils/utils.ts';

interface GeneralInfoProps {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  vehicle : Vehicle;
}

class GeneralInfo extends React.Component<GeneralInfoProps, {}> {

  constructor() {
    super();
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(field : Vehicle.Field) : any {
    return (e : any) => {
      this.props.handleChange(field, e);
    }
  }

  render() {
    var { licensePlate, vin, fleet, leasingCompany, brand, model, type, mileage, year, value } = this.props.vehicle;
    const choices = [
      th('personalCar', 'vehicle.options.personalCar'),
      th('van', 'vehicle.options.van'),
      th('semiTrailer', 'vehicle.options.semiTrailer'),
      th('trailer', 'vehicle.options.trailer'),
      th('truck', 'vehicle.options.truck')
    ];
    return (
      <div className='col-xs-12 col-md-7'>
        <Card>
          <div className='card-title'>
            <h5>General info</h5>
          </div>
          <div className='card-content'>
            <FormField value={ licensePlate }    placeholder='vehicle.licensePlate'     type='text'     callback={ this.handleChange('licensePlate') }    hasError={ this.props.hasError('licensePlate')} />
            <FormField value={ vin }             placeholder='vehicle.vin'              type='text'     callback={ this.handleChange('vin') }             hasError={ this.props.hasError('vin')}      />
            <FormField value={ fleet }           placeholder='vehicle.fleet'            type='text'     callback={ this.handleChange('fleet') }           hasError={ this.props.hasError('fleet')}   />
            <FormField value={ leasingCompany }  placeholder='vehicle.leasingCompany'   type='text'     callback={ this.handleChange('leasingCompany') }  hasError={ this.props.hasError('leasingCompany')}  />
            <FormField value={ brand }           placeholder='vehicle.brand'            type='text'     callback={ this.handleChange('brand') }           hasError={ this.props.hasError('brand')}      />
            <FormField value={ model }           placeholder='vehicle.model'            type='text'     callback={ this.handleChange('model') }           hasError={ this.props.hasError('model')}   />
            <FormChoice value={ type }           placeholder='vehicle.type'             choices={ choices } callback={ this.handleChange('type') } />
            <FormField value={ mileage }         placeholder='vehicle.mileage'          type='text'     callback={ this.handleChange('mileage') }         hasError={ this.props.hasError('mileage')}  />
            <FormField value={ year }            placeholder='vehicle.year'             type='number'   callback={ this.handleChange('year') }            hasError={ this.props.hasError('year')}      />
            <FormField value={ value }           placeholder='vehicle.value'            type='number'   callback={ this.handleChange('value') }           hasError={ this.props.hasError('value')}   />
          </div>
        </Card>
      </div>
    );
  }
}

interface SubmitProps {
  persisted : boolean
}
class Submit extends React.Component<SubmitProps, {}> {
  render() {
    var buttonMessage;
    if (this.props.persisted) {
      buttonMessage = "update";
    } else {
      buttonMessage = "create";
    }

    return (
      <div className='col-xs-12'>
        <Card>
          <div className='card-title'>
            <h5>Actions</h5>
          </div>
          <div className='card-content actions'>
            <button type='submit' className='btn btn-success'>
              <T.text tag='span' text={ 'form.' + buttonMessage } /> vehicle
            </button>
            <Link to='/vehicles' className='btn btn-default'>Cancel</Link>
          </div>
        </Card>
      </div>
    );
  }
}

class VehicleForm extends React.Component<Vehicle.VForm.Props, any> {
  render() {
    return (
      <form method='post' onSubmit={ this.props.onSubmit } >
        <div className='wrapper'>
          <div className='row'>
            <Errors errors={ this.props.errors } />
            <GeneralInfo vehicle={ this.props.vehicle } handleChange={ this.props.handleChange } hasError={ this.props.hasError }/>
            <div className='col-xs-12 col-md-5'>
              <div className='row'>
                <Submit persisted={ this.props.vehicle.id != null } />
              </div>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default VehicleForm;
