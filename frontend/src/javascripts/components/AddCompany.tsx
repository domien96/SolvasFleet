import React from 'react';
import T from 'i18n-react';

import Card       from './app/Card.tsx';
import FormField  from './forms/FormField.tsx';
import WrappedCol from './app/WrappedCol.tsx';

class AddCompany extends React.Component<CompanyProps, CompanyState> {

  constructor(props : CompanyProps) {
    super(props);
    this.state = {
      errors: [],
      name: null,
      vatNumber: null,
      phoneNumber: null,
      address: null
    };
    this.onSubmit = this.onSubmit.bind(this);
  }

  public handleChange(field : string, e : any) : void {
    var state : any = {};
    state[field] = e.target.value;
    this.setState(state);
  }

  public onSubmit() : void {
    // TODO
  }

  public hasError(k : string) : boolean {
    const errors = this.state.errors.filter(function(el) { return el.field == k; });
    return (errors.length != 0);
  }

  //TODO: might be more elegant to divide address into street, number, city, country

  render() {
    return (
      <WrappedCol cols={ 7 } >
        <Card>
          <div className='card-title'><h5>Add a new company</h5></div>
          <div className='card-content'>
            <form method='POST' onSubmit={this.onSubmit} className='addCompany-form' >
              <FormField placeholder='form.placeholders.name'        type='text'   callback={ this.handleChange.bind(this, 'name')        } hasError={ this.hasError('name')}        />
              <FormField placeholder='form.placeholders.vatNumber'   type='text'   callback={ this.handleChange.bind(this, 'vatNumber')   } hasError={ this.hasError('vatNumber')}   />
              <FormField placeholder='form.placeholders.phoneNumber' type='number' callback={ this.handleChange.bind(this, 'phoneNumber') } hasError={ this.hasError('phoneNumber')} />
              <FormField placeholder='form.placeholders.address'     type='text'   callback={ this.handleChange.bind(this, 'address')     } hasError={ this.hasError('address')}     />
              <button type='submit' className='btn btn-default'> 
                <T.text tag='span' text='addCompany.submit' />
              </button>
            </form>
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default AddCompany;
