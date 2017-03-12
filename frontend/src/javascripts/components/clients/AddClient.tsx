import React from 'react';
import T from 'i18n-react';
import { browserHistory, Link } from 'react-router';

import Card       from '../app/Card.tsx';
import FormField  from '../forms/FormField.tsx';
import Header     from '../app/Header.tsx';
import Errors     from '../app/Errors.tsx';

import createCompany from '../../actions/create_company.ts';

interface GeneralInfoProps {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  company : Company;
}

class GeneralInfo extends React.Component<GeneralInfoProps, {}> {
  render() {
    var { name, vat_number, phone_number, address } = this.props.company;
    return (
      <div className='col-xs-12 col-md-7'>
        <Card>
          <div className='card-title'>
            <h5>General info</h5>
          </div>
          <div className='card-content'>
            <FormField value={ name         } placeholder='company.name'         type='text' callback={ this.props.handleChange.bind(this, 'name')         } hasError={ this.props.hasError('name')}         />
            <FormField value={ vat_number   } placeholder='company.vat_number'   type='text' callback={ this.props.handleChange.bind(this, 'vat_number')   } hasError={ this.props.hasError('vat_number')}   />
            <FormField value={ phone_number } placeholder='company.phone_number' type='tel'  callback={ this.props.handleChange.bind(this, 'phone_number') } hasError={ this.props.hasError('phone_number')} />
            <FormField value={ address      } placeholder='company.address'      type='text' callback={ this.props.handleChange.bind(this, 'address')      } hasError={ this.props.hasError('address')}      />
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

class AddClient extends React.Component<Company.Props, Company.New.State> {

  constructor() {
    super();
    this.state = {
      errors: [ { field: 'name', error: 'null' }],
      company: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
    this.hasError     = this.hasError.bind(this);
  }

  public handleChange(field : Company.Field, e : any) : void {
    var newClient : Company = this.state.company;
    newClient[field] = e.target.value;
    this.setState({ company: newClient });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();

    createCompany(this.state.company)
    .then(function(response) {
      return response.json()
    })
    .then(() => {
      browserHistory.push('/clients');
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
          <h2>Add A New Client</h2>
        </Header>
        <form method='post' onSubmit={ this.onSubmit } >
          <div className='wrapper'>
            <div className='row'>
              <Errors errors={ this.state.errors } />
              <GeneralInfo company={ this.state.company } handleChange={ this.handleChange } hasError={ this.hasError.bind(this) }/>
              <div className='col-xs-12 col-md-5'>
                  <Submit />
              </div>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default AddClient;
