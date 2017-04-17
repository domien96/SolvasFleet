import React from 'react';
import UserFunctionsView from './UserFunctionsView.tsx';
import { fetchFunctionsByUser } from '../../actions/user_actions.ts';
import { redirect_to } from'../../routes/router.tsx';

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
    this.handleFunctionSelect = this.handleFunctionSelect.bind(this);
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

  handleFunctionSelect(id : number){
    redirect_to(`/auth/functions/${id}`);
  }

  render(){
    return (
      <UserFunctionsView userId={ this.props.user.id } Sfunctions={ this.state.Sfunctions } onFunctionSelect={ this.handleFunctionSelect }/>
    );
  }
}

export default UserFunctions;
