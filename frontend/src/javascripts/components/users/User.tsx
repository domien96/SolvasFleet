import React from 'react';

import fetchUser from '../../actions/fetch_user.ts';

class User extends React.Component<User.Props, User.State> {

  constructor() {
    super();
    this.state = { user : {} };
  }

  fetchUser(id : number) {
    fetchUser(id)
      .then((data : any) => {
        this.setState({ user: data })
      });
  }

  componentDidMount() {
    this.fetchUser(this.props.params.id);
  }

  componentWillReceiveProps(nextProps : User.Props) {
    if (nextProps.params.id != this.props.params.id) {
      this.fetchUser(nextProps.params.id);
    }
  }

  render() {
    var { first_name, last_name, email, password } = this.state.user;

    return (
      <div className='card-content user'>
        <span className='pull-right'>
          <span className='glyphicon glyphicon-edit' />
        </span>
        <h2>{ first_name } { last_name }</h2>
        <h5>Information</h5>
        <div>email: { email }</div>
        <div>password: { password }</div>
        <h5>Fleets</h5>
        <button className='btn btn-default'>See Fleets</button>
      </div>
    );
  }
}

export default User;
