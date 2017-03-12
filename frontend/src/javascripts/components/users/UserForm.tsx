import React    from 'react';
import T        from 'i18n-react';
import { Link } from 'react-router';

import Card       from '../app/Card.tsx';
import Errors     from '../app/Errors.tsx';
import FormField  from '../forms/FormField.tsx';

interface GeneralInfoProps {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  user : User;
}

class GeneralInfo extends React.Component<GeneralInfoProps, {}> {
  constructor() {
    super();
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(field : User.Field) : any {
    return (e : any) => {
      this.props.handleChange(field, e);
    }
  }
  render() {
    return (
      <div className='col-xs-12 col-md-7'>
        <Card>
          <div className='card-title'>
            <h5>General info</h5>
          </div>
          <div className='card-content'>
            <FormField value={this.props.user.first_name} placeholder='user.first_name' type='text'     callback={ this.handleChange('first_name') } hasError={ this.props.hasError('first_name')} />
            <FormField value={ this.props.user.last_name} placeholder='user.last_name'  type='text'     callback={ this.handleChange('last_name')  } hasError={ this.props.hasError('last_name')}  />
            <FormField value={ this.props.user.email} placeholder='user.email'      type='email'    callback={ this.handleChange('email')      } hasError={ this.props.hasError('email')}      />
            <FormField value={ this.props.user.password} placeholder='user.password'   type='password' callback={ this.handleChange('password')   } hasError={ this.props.hasError('password')}   />
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

interface UserFormProps {
  onSubmit : any;
  handleChange : any;
  errors : Form.Error[];
  hasError : any;
  user : User;
}
class UserForm extends React.Component<UserFormProps, any> {
  render() {
    return (
      <form method='post' onSubmit={ this.props.onSubmit } >
        <div className='wrapper'>
          <div className='row'>
            <Errors errors={ this.props.errors } />
            <GeneralInfo user={ this.props.user }handleChange={ this.props.handleChange } hasError={ this.props.hasError }/>
            <div className='col-xs-12 col-md-5'>
              <div className='row'>
                <Permissions />
                <Submit />
              </div>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default UserForm;
