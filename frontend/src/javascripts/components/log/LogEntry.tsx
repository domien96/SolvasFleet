import React from 'react';
import LogEntryLayout from './LogEntryLayout.tsx';
import { fetchLogEntry } from '../../actions/audit_actions.ts';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface Props {
  params: { id: number };
}

interface State {
  entry: LogEntryData;
  users: UserData[];
}

class Log extends React.Component<Props, State> {
  constructor(props: any) {
    super(props);
    this.state = { entry: null, users: [] }
    this.fetchLogEntry = this.fetchLogEntry.bind(this);
    this.getUser = this.getUser.bind(this);
  }

  componentDidMount() {
    this.fetchLogEntry(this.props.params.id);
    this.fetchUsers();
  }

  componentWillReceiveProps(nextProps: Props) {
    if (nextProps.params.id !== this.props.params.id) {
      this.fetchLogEntry(nextProps.params.id);
    }
  }

  fetchLogEntry(id: number) {
    fetchLogEntry(id, (data: any) => {
      console.log(data);
      this.setState({ entry: data })
    });
  }

  fetchUsers() {
    fetchUsers((data: any) => this.setState({ users: data.data }));
  }

  handleClick(id: number) {
    redirect_to(`/log/${id}`);
  }

  getUser(id: number) {
    if (this.state.users.length > 0) {
      const usersFiltered = this.state.users.filter((r: UserData) => {
        return r.id === id;
      });
      return `${usersFiltered[0].firstName} ${usersFiltered[0].lastName}`;
    }
    else {
      return id.toString();
    }
  }

  render() {
    if (this.state.entry) {
      return (
        <LogEntryLayout 
          entry={ this.state.entry }
          getUser={ this.getUser } />
      );
    }
    return null;
  }
}

export default Log;
