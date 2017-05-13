import React from 'react';
import Layout from './Layout.tsx';
import { fetchLog } from '../../actions/audit_actions.ts';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface State {
  response: ListResponse;
  users: UserData[];
  tableData: any;
}

class Log extends React.Component<{}, State> {
  constructor(props: {}) {
    super(props);
    this.state = { 
      response: {
        data: [],
        first: null,
        last: null,
        limit: 0,
        next: null,
        offset: 0,
        previous: null,
        total: 0,
      }, 
      users: [],
      tableData: []
    };
    this.fetchLog = this.fetchLog.bind(this);
    this.getUser = this.getUser.bind(this);
    this.fetchUsers = this.fetchUsers.bind(this);
    this.handleClick = this.handleClick.bind(this);
  }

  componentDidMount() {
    this.fetchLog();
    this.fetchUsers();
  }

  fetchLog(query?: any) {
    fetchLog((data: any) => {
      this.setState({ response: data });
      this.setTableData(data.data);
    }, undefined, query);
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

  setTableData(entries: LogEntryData[]){
    const data = entries.map((entry: LogEntryData) => {
      const entityTypeSplit = entry.entityType.split('.');
      const newEntityType = entityTypeSplit[entityTypeSplit.length - 1];
      const logDateSplit = entry.logDate.split('T');
      const newLogDate = `${logDateSplit[0]} ${logDateSplit[1]}`;

      return {
        id: entry.id,
        entity: entry.entity,
        entityType: newEntityType,
        logDate: newLogDate,
        method: entry.method,
        user: this.getUser(entry.user)
      }
    }); 

    this.setState({ tableData: data });
  }

  render() {
    return (
      <Layout 
        response={this.state.response} 
        onLogSelect={ this.handleClick } 
        fetchLog={this.fetchLog} 
        tableData={ this.state.tableData } />
    );
  }
}

export default Log;
