import React    from 'react';
import T        from 'i18n-react';
import { Link } from 'react-router';

import Card       from '../app/Card.tsx';
import Errors     from '../app/Errors.tsx';
import FormField  from '../forms/FormField.tsx';

interface GeneralInfoProps {
  handleChange: (field : string, isAddress : boolean, e : any) => void;
  hasError: (e : any) => boolean;
  company : Company;
}

class GeneralInfo extends React.Component<GeneralInfoProps, {}> {

  constructor() {
    super();
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(field : Company.Field, isAddress : boolean) : any {
    return (e : any) => {
      this.props.handleChange(field, isAddress, e);
    }
  }

  render() {
    var { name, vatNumber, phoneNumber, address } = this.props.company;
    var { street, houseNumber, city, postalCode, country } = address;
    return (
      <div className='col-xs-12 col-md-7'>
        <Card>
          <div className='card-title'>
            <h5>General info</h5>
          </div>
          <div className='card-content'>
            <FormField value={ name }         placeholder='company.name'                type='text' callback={ this.handleChange('name', false) }         hasError={ this.props.hasError('name')}         />
            <FormField value={ vatNumber }    placeholder='company.vatNumber'           type='text' callback={ this.handleChange('vatNumber', false) }    hasError={ this.props.hasError('vatNumber')}   />
            <FormField value={ phoneNumber }  placeholder='company.phoneNumber'         type='tel'  callback={ this.handleChange('phoneNumber', false) }  hasError={ this.props.hasError('phoneNumber')} />
            <FormField value={ street }       placeholder='company.address.street'      type='text' callback={ this.handleChange('street', true) }       hasError={ this.props.hasError('street')}      />
            <FormField value={ houseNumber }  placeholder='company.address.houseNumber' type='text' callback={ this.handleChange('houseNumber', true) }  hasError={ this.props.hasError('houseNumber')}      />
            <FormField value={ city }         placeholder='company.address.city'        type='text' callback={ this.handleChange('city', true) }         hasError={ this.props.hasError('city')}      />
            <FormField value={ postalCode }   placeholder='company.address.postalCode'  type='text' callback={ this.handleChange('postalCode', true) }   hasError={ this.props.hasError('postalCode')}      />
            <FormField value={ country }      placeholder='company.address.country'     type='text' callback={ this.handleChange('country', true) }      hasError={ this.props.hasError('country')}      />
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
              <T.text tag='span' text='addClient.submit' />
            </button>
            <Link to='/clients' className='btn btn-default'>Cancel</Link>
          </div>
        </Card>
      </div>
    );
  }
}

class ClientForm extends React.Component<Company.CForm.Props, any> {
  render() {
    return (
      <form method='post' onSubmit={ this.props.onSubmit } >
        <div className='wrapper'>
          <div className='row'>
            <Errors errors={ this.props.errors } />
            <GeneralInfo company={ this.props.company } handleChange={ this.props.handleChange } hasError={ this.props.hasError }/>
            <div className='col-xs-12 col-md-5'>
              <div className='row'>
                <Submit />
              </div>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default ClientForm;
