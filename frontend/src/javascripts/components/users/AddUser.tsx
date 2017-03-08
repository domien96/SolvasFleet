import React from 'react';
import T from 'i18n-react';
import { Link } from 'react-router';

import Card       from '../app/Card.tsx';
import FormField  from '../forms/FormField.tsx';
import Header     from '../app/Header.tsx';

import createUser from '../../actions/create_user.ts';

import { pluck } from '../../utils/utils.ts';

interface GeneralInfoProps {
  handleChange: any;
  hasError: any;
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
            <FormField placeholder='form.placeholders.firstName' type='text'     callback={ this.props.handleChange.bind(this, 'firstName') } hasError={ this.props.hasError('firstName')} />
            <FormField placeholder='form.placeholders.lastName'  type='text'     callback={ this.props.handleChange.bind(this, 'lastName')  } hasError={ this.props.hasError('lastName')}  />
            <FormField placeholder='form.placeholders.email'     type='email'    callback={ this.props.handleChange.bind(this, 'email')     } hasError={ this.props.hasError('email')}     />
            <FormField placeholder='form.placeholders.password'  type='password' callback={ this.props.handleChange.bind(this, 'password')  } hasError={ this.props.hasError('password')}  />
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
              <T.text tag='span' text='addUser.submit' />
            </button>
            <Link to='/users' className='btn btn-default'>Cancel</Link>
          </div>
        </Card>
      </div>
    );
  }
}

class AddUser extends React.Component<UserProps, UserState> {

  constructor(props : UserProps) {
    super(props);
    this.state = {
      errors: [],
      firstName: null,
      lastName: null,
      email: null,
      password: null
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  public handleChange(field : string, e : any) : void {
    var state : any = {};
    state[field] = e.target.value;
    this.setState(state);
  }

  public onSubmit(e : any) : void {
    e.preventDefault();

    createUser(pluck(this.state, ['firstName', 'lastName', 'email', 'password']))
    .then(function(response) {
      return response.json()
    })
    .then((data : any) => {
      console.log(data);
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
          <h2>Add A New User</h2>
        </Header>
        <form method='post' onSubmit={ this.onSubmit } >
          <div className='wrapper'>
            <div className='row'>
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

export default AddUser;
