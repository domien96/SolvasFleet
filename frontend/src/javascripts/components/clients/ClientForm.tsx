import React    from 'react';
import T        from 'i18n-react';
import { Link } from 'react-router';

import Card       from '../app/Card.tsx';
import Errors     from '../app/Errors.tsx';
import FormField  from '../forms/FormField.tsx';

interface GeneralInfoProps {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  company : Company;
}

class GeneralInfo extends React.Component<GeneralInfoProps, {}> {

  constructor() {
    super();
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(field : Company.Field) : any {
    return (e : any) => {
      this.props.handleChange(field, e);
    }
  }

  render() {
    var { name, vatNumber, phoneNumber, address } = this.props.company;
    return (
      <div className='col-xs-12 col-md-7'>
        <Card>
          <div className='card-title'>
            <h5>General info</h5>
          </div>
          <div className='card-content'>
            <FormField value={ name }         placeholder='company.name'          type='text' callback={ this.handleChange('name') }         hasError={ this.props.hasError('name')}         />
            <FormField value={ vatNumber }    placeholder='company.vatNumber'     type='text' callback={ this.handleChange('vatNumber') }    hasError={ this.props.hasError('vatNumber')}   />
            <FormField value={ phoneNumber }  placeholder='company.phoneNumber'   type='tel'  callback={ this.handleChange('phoneNumber') }  hasError={ this.props.hasError('phoneNumber')} />
            <FormField value={ address }      placeholder='company.address'       type='text' callback={ this.handleChange('address') }      hasError={ this.props.hasError('address')}      />
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
