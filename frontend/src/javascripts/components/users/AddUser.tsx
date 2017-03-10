import React from 'react';
import T from 'i18n-react';
import { browserHistory, Link } from 'react-router';

import Card       from '../app/Card.tsx';
import FormField  from '../forms/FormField.tsx';
import Header     from '../app/Header.tsx';
import Errors     from '../app/Errors.tsx';

import createUser from '../../actions/create_user.ts';

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
            <FormField placeholder='form.placeholders.first_name' type='text'     callback={ this.props.handleChange.bind(this, 'first_name') } hasError={ this.props.hasError('first_name')} />
            <FormField placeholder='form.placeholders.last_name'  type='text'     callback={ this.props.handleChange.bind(this, 'last_name')  } hasError={ this.props.hasError('last_name')}  />
            <FormField placeholder='form.placeholders.email'      type='email'    callback={ this.props.handleChange.bind(this, 'email')      } hasError={ this.props.hasError('email')}      />
            <FormField placeholder='form.placeholders.password'   type='password' callback={ this.props.handleChange.bind(this, 'password')   } hasError={ this.props.hasError('password')}   />
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

class AddUser extends React.Component<User.Props, User.New.State> {

  constructor() {
    super();
    this.state = {
      errors: [ { field: 'first_name', error: 'null' }],
      user: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
    this.hasError     = this.hasError.bind(this);
  }

  public handleChange(field : User.Field, e : any) : void {
    var newUser : User = this.state.user;
    newUser[field] = e.target.value;
    this.setState({ user: newUser });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();

    createUser(this.state.user)
    .then(function(response) {
      return response.json()
    })
    .then(() => {
      browserHistory.push('/users');
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

export default AddUser;
