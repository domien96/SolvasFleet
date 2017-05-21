import React from 'react';
import LogEntryLayout from './LogEntryLayout.tsx';
import { fetchLogEntry, fetchLog } from '../../actions/audit_actions.ts';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface Props {
  params: { id: number };
}

interface State {
  entry: LogEntryData;
  oldEntry: LogEntryData;
  users: UserData[];
}

class Log extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { entry: undefined, oldEntry: undefined, users: [] };
    this.getUser = this.getUser.bind(this);
  }

  componentDidMount() {
    this.fetchUsers();
    this.fetchLogEntry(this.props.params.id);
  }

  fetchLogEntry(id: number) {
    fetchLogEntry(id, (data: any) => {
      if (data) {
        this.fetchOldLogEntry(data);
        this.setState({ entry: data });
      }
    });
  }

  fetchOldLogEntry(entry: any) {
    const query = {
      entityType: entry.entityType,
      entity: entry.entity,
      before: entry.logDate
    }
    fetchLog((data: any) => {
      if (data) {
        let oldEntry: LogEntryData;
        let max = -1;
        data.data.map((e: LogEntryData) => {
          if (e.id < entry.id && e.id > max) {
            max = e.id;
            oldEntry = e;
          }
        });
        if (!oldEntry) { // If there is no old entry of this entity
          oldEntry = null;
        }
        this.setState({ oldEntry: oldEntry });
      }
    }, undefined, query);
  }

  fetchUsers() {
    fetchUsers((data: any) => {
      if (data) {
        this.setState({ users: data.data });
      }
    });
  }

  handleClick(id: number) {
    redirect_to(`/log/${id}`);
  }

  getUser(id: number) {
    if (this.state.users.length > 0) {
      const userFiltered = this.state.users.find((r: UserData) => {
        return r.id === id;
      });

      if (!userFiltered) {
        return '<empty>';
      }

      return `${userFiltered.firstName} ${userFiltered.lastName}`;
    }
    else {
      return id.toString();
    }
  }

  render() {
    if (!this.state.entry) {
      return null;
    }

    return (
      <LogEntryLayout
        entry={ this.state.entry }
        oldEntry={ this.state.oldEntry }
        getUser={ this.getUser } />
    );
  }
}

export default Log;
