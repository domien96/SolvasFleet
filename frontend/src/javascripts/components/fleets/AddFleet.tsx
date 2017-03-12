import React from 'react';
import T from 'i18n-react';
import { browserHistory, Link } from 'react-router';

import Card       from '../app/Card.tsx';
import FormField  from '../forms/FormField.tsx';
import Header     from '../app/Header.tsx';
import Errors     from '../app/Errors.tsx';

import createFleet from '../../actions/create_fleet.ts';

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
            <FormField placeholder='fleet.name' type='text'     callback={ this.props.handleChange.bind(this, 'name') } hasError={ this.props.hasError('name')} />
          </div>
        </Card>
      </div>
    );
  }
}

interface SubmitProps {
  id : number
}

class Submit extends React.Component<SubmitProps, {}> {
  render() {
    return (
      <div className='col-xs-12'>
        <Card>
          <div className='card-title'>
            <h5>Actions</h5>
          </div>
          <div className='card-content'>
            <button type='submit' className='btn btn-default'>
              <T.text tag='span' text='addFleet.submit' />
            </button>
            <Link to='/clients/${this.props.id}' className='btn btn-default'>Cancel</Link>
          </div>
        </Card>
      </div>
    );
  }
}

class AddFleet extends React.Component<Fleet.Props, Fleet.New.State> {

  constructor() {
    super();
    this.state = {
      errors: [ { field: 'name', error: 'null' }],
      fleet: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
    this.hasError     = this.hasError.bind(this);
  }

  public handleChange(field : Fleet.Field, e : any) : void {
    var newFleet : Fleet = this.state.fleet;
    newFleet[field] = e.target.value;
    this.setState({ fleet: newFleet });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();

    createFleet(this.props.params.id, this.state.fleet)
    .then(function(response) {
      return response.json()
    })
    .then(() => {
      browserHistory.push('/clients/' + this.props.params.id);
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
          <h2>Add A New Fleet</h2>
        </Header>
        <form method='post' onSubmit={ this.onSubmit } >
          <div className='wrapper'>
            <div className='row'>
              <Errors errors={ this.state.errors } />
              <GeneralInfo handleChange={ this.handleChange } hasError={ this.hasError.bind(this) }/>
              <div className='col-xs-12 col-md-5'>
                <div className='row'>
                  <Submit id={this.props.params.id} />
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default AddFleet;
