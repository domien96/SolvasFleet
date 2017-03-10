import React from 'react';

import fetchUser from '../../actions/fetch_user.ts';

class User extends React.Component<UserProp, UserState> {

  constructor(){
    super();
    this.state = {
      id: null,
      first_name: null,
      last_name: null,
      email: null,
      password: null
    };
  }

  fetchUser(){
    const id = this.props.params.id;

    fetchUser(id)
      .then((data : any) => {
        this.setState(data)
      });
  }

  componentDidMount(){
    this.fetchUser();
  }

  componentWillReceiveProps(nextProps : UserProp){
    if(nextProps.params.id != this.props.params.id){
      this.props.params.id = nextProps.params.id;
      this.fetchUser();
    }
  }

  render() {
    var { first_name, last_name, email, password } = this.state;

    return (
      <div className='card-content'>
        <span className='pull-right'>
          <span className='glyphicon glyphicon-edit' />
        </span>
        <h2>{first_name} {last_name}</h2>
        <h5>Information</h5>
        <div>{ email }</div>
        <div>{ password }</div>
        <h5>Fleets</h5>
        <button className='btn btn-default'>See Fleets</button>
      </div>
    );
  }
}

export default User;
