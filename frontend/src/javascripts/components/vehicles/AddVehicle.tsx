import React from 'react';
import T from 'i18n-react';
import { browserHistory, Link } from 'react-router';

import Card       from '../app/Card.tsx';
import FormField  from '../forms/FormField.tsx';
import Header     from '../app/Header.tsx';
import Errors     from '../app/Errors.tsx';

import createVehicle from '../../actions/create_vehicle.ts';

interface GeneralInfoProps {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
}

class GeneralInfo extends React.Component<GeneralInfoProps, {}> {
  render() {
    return (
      <div className='col-xs-12 col-md-7'>
        <Card>
          <div className='card-title'>
            <h5>General info</h5>
          </div>
          <div className='card-content'>
            <FormField placeholder='vehicle.license_plate' type='text'     callback={ this.props.handleChange.bind(this, 'license_plate') } hasError={ this.props.hasError('license_plate')} />
            <FormField placeholder='vehicle.chassis_number'      type='text'    callback={ this.props.handleChange.bind(this, 'chassis_number')      } hasError={ this.props.hasError('chassis_number')}      />
            <FormField placeholder='vehicle.company'   type='text' callback={ this.props.handleChange.bind(this, 'company')   } hasError={ this.props.hasError('company')}   />
            <FormField placeholder='vehicle.leasing_company'  type='text'     callback={ this.props.handleChange.bind(this, 'leasing_company')  } hasError={ this.props.hasError('leasing_company')}  />
            <FormField placeholder='vehicle.brand'      type='text'    callback={ this.props.handleChange.bind(this, 'brand')      } hasError={ this.props.hasError('brand')}      />
            <FormField placeholder='vehicle.model'   type='text' callback={ this.props.handleChange.bind(this, 'model')   } hasError={ this.props.hasError('model')}   />
            <FormField placeholder='vehicle.type'  type='text'     callback={ this.props.handleChange.bind(this, 'type')  } hasError={ this.props.hasError('type')}  />
            <FormField placeholder='vehicle.kilometer_count'  type='text'     callback={ this.props.handleChange.bind(this, 'kilometer_count')  } hasError={ this.props.hasError('kilometer_count')}  />
            <FormField placeholder='vehicle.year'      type='number'    callback={ this.props.handleChange.bind(this, 'year')      } hasError={ this.props.hasError('year')}      />
            <FormField placeholder='vehicle.value'   type='number' callback={ this.props.handleChange.bind(this, 'value')   } hasError={ this.props.hasError('value')}   />
          </div>
        </Card>
      </div>
    );
  }
}

class Permissions extends React.Component<{}, {}> {
  render() {
    return (
      <div className='col-xs-12'>
        <Card>
          <div className='card-title'>
            <h5>
              Permissions
              <small> (Not implemented yet)</small>
            </h5>
          </div>
          <div className='card-content'>
            <div className='checkbox'>
              <label>
                <input type="checkbox" value='' />
                Premies aanmaken
              </label>
            </div>
            <div className='checkbox'>
              <label>
                <input type="checkbox" value='' />
                Premies wijzigen
              </label>
            </div>
            <div className='checkbox'>
              <label>
                <input type="checkbox" value='' />
                Premies verwijderen
              </label>
            </div>
          </div>
        </Card>
      </div>
    );
  }
}

class Submit extends React.Component<{}, {}> {
  render() {
    return (
      <div className='col-xs-12'>
        <Card>
          <div className='card-title'>
            <h5>Actions</h5>
          </div>
          <div className='card-content'>
            <button type='submit' className='btn btn-default'>
              <T.text tag='span' text='addVehicle.submit' />
            </button>
            <Link to='/vehicles' className='btn btn-default'>Cancel</Link>
          </div>
        </Card>
      </div>
    );
  }
}

class AddVehicle extends React.Component<Vehicle.Props, Vehicle.New.State> {

  constructor() {
    super();
    this.state = {
      errors: [ { field: 'first_name', error: 'null' }],
      vehicle: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
    this.hasError     = this.hasError.bind(this);
  }

  public handleChange(field : Vehicle.Field, e : any) : void {
    var newVehicle : Vehicle = this.state.vehicle;
    newVehicle[field] = e.target.value;
    this.setState({ vehicle: newVehicle });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();

    createVehicle(this.state.vehicle)
    .then(function(response) {
      return response.json()
    })
    .then(() => {
      browserHistory.push('/vehicles');
    });
  }

  public hasError(k : string) : boolean {
    const errors = this.state.errors.filter(function(el) {return el.field == k; });
    return (errors.length != 0);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Add A New Vehicle</h2>
        </Header>
        <form method='post' onSubmit={ this.onSubmit } >
          <div className='wrapper'>
            <div className='row'>
              <Errors errors={ this.state.errors } />
              <GeneralInfo handleChange={ this.handleChange } hasError={ this.hasError.bind(this) }/>
              <div className='col-xs-12 col-md-5'>
                <div className='row'>
                  <Permissions />
                  <Submit />
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default AddVehicle;
