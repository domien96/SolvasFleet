import React from 'react';
import UserFunctionsView from './UserFunctionsView.tsx';
import { fetchFunctionsByUser, deleteFunction } from '../../../actions/user_actions.ts';

interface Props{
  user: UserData;
}

interface State{
  Sfunctions: SFunctionData[];
}

class UserFunctions extends React.Component<Props, State> {
  constructor(props : any){
    super(props);
    this.state = { Sfunctions: [] };
    this.handleFunctionDelete = this.handleFunctionDelete.bind(this);
  }

  componentDidMount() {
    if(this.props.user.id){
      this.fetchFunctions(this.props.user.id);
    }
  }

  componentWillReceiveProps(nextProps: any){
    if(nextProps.user.id != this.props.user.id){
      this.fetchFunctions(nextProps.user.id);
    }
  }

  fetchFunctions(id : number) {
    fetchFunctionsByUser(id, ((data : SFunctions.Data) => {
      this.setState({ Sfunctions: data.data })
    }));
  }

  handleFunctionDelete(functionId : number){
    let success = () => this.fetchFunctions;
    deleteFunction(this.props.user.id, functionId, success);
  }

  render(){
    return (
      <UserFunctionsView userId={ this.props.user.id } Sfunctions={ this.state.Sfunctions } onFunctionDelete={ this.handleFunctionDelete }/>
    );
  }
}

export default UserFunctions;
