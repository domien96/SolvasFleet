import React from 'react';

import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';

import fetchUser from '../../actions/fetch_user.ts';

class User extends React.Component<UserProp, UserState> {

  constructor(){
    super();
    this.state = { first_name: null, last_name: null, email: null, password: null, id: null };
  }

  updateUser(){
    const id = this.props.params.id;

    fetchUser( id )
      .then((data : UserData) => {
        this.setState({ 
          first_name: data.first_name,
          last_name: data.last_name,
          email: data.email,
          password: data.password,
          id: data.id
        })
      }); 
  }

  componentDidMount(){
    this.updateUser();
  }

  componentWillReceiveProps(nextProps : any){
    if(nextProps.params.id != this.props.params.id){
      this.props.params.id = nextProps.params.id;
      this.updateUser();
    }
  }

  render() {

    return (
      <WrappedCol>
        <Card className='text-center' >
          <div className='card-content'>
            <button className='btn btn-default'>Edit User</button>
            <h2> {this.state.first_name} {this.state.last_name} </h2>
            <div>{this.state.email}</div>
            <div>{this.state.password}</div>
            <button className='btn btn-default'>See Fleets</button>
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default User;
