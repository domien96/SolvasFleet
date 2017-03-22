import React    from 'react';
import T        from 'i18n-react';
import { Link } from 'react-router';

import Card       from '../app/Card.tsx';
import Errors     from '../app/Errors.tsx';
import Info from './form/Info.tsx';
import Permissions from './form/Permissions.tsx';

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
              <T.text tag='span' text={ 'form.' + buttonMessage } /> user
            </button>
            <Link to='/users' className='btn btn-default'>Cancel</Link>
          </div>
        </Card>
      </div>
    );
  }
}

class UserForm extends React.Component<User.UForm.Props, any> {
  render() {
    return (
      <form method='post' onSubmit={ this.props.onSubmit } >
        <div className='wrapper'>
          <div className='row'>
            <Errors errors={ this.props.errors } />
            <Info user={ this.props.user }handleChange={ this.props.handleChange } hasError={ this.props.hasError }/>
            <div className='col-xs-12 col-md-5'>
              <div className='row'>
                <Permissions />
                <Submit persisted={ this.props.user.id != null } />
              </div>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default UserForm;
