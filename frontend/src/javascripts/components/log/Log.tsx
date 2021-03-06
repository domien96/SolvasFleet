import React from 'react';
import Layout from './Layout.tsx';
import { fetchLog } from '../../actions/audit_actions.ts';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface Props {
  location: any;
}

interface State {
  response: ListResponse;
  users: UserData[];
  tableData: any;
  filter: LogFilterData;
}

class Log extends React.Component<Props, State> {
  constructor(props: Props) {
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
        total: 0
      },
      filter: {
        after: '',
        before: '',
        method: '',
        entityType: '',
        entity: '',
        user: '',
      },
      users: [],
      tableData: []
    };
    this.fetchLog = this.fetchLog.bind(this);
    this.getUser = this.getUser.bind(this);
    this.fetchUsers = this.fetchUsers.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleFilter = this.handleFilter.bind(this);
  }

  componentDidMount() {
    let newfilter = this.state.filter;
    if (this.props.location.query.entity) {
      newfilter = { ...this.state.filter, entity: this.props.location.query.entity, entityType: this.props.location.query.entityType };
      this.setState({ filter: newfilter });
    }
    this.fetchLog(undefined, newfilter);
    this.fetchUsers();
  }

  fetchLog(query?: any, filter?: LogFilterData) {
    const queryFilter = filter;
    let newQuery: any;
    if (query) {
      newQuery = query;
      if (filter) {
        for (const key in queryFilter) {
          if (queryFilter[key] === null || queryFilter[key] === undefined || queryFilter[key] === '') {
            delete queryFilter[key];
          }
        }
        for (const key in queryFilter) {
          newQuery[key] = queryFilter[key];
        }
      }
    } else {
      for (const key in queryFilter) {
        if (queryFilter[key] === null || queryFilter[key] === undefined || queryFilter[key] === '') {
          delete queryFilter[key];
        }
      }
      newQuery = queryFilter;
    }

    fetchLog((data: any) => {
      this.setState({ response: data });
      this.setTableData(data.data, this.state.users);
    }, undefined, newQuery);
  }

  fetchUsers() {
    fetchUsers((data: any) => {
      this.setState({ users: data.data });
      this.setTableData(this.state.response.data, data.data);
    });
  }

  handleClick(id: number) {
    redirect_to(`/log/${id}`);
  }

  handleFilter(newFilter: LogFilterData) {
    this.setState({ filter: newFilter });
    this.fetchLog(undefined, newFilter);
  }

  getUser(inputUsers: UserData[], id: number, init?: boolean) {
    let users: UserData[] = inputUsers;
    if (init) {
      users = this.state.users;
    }
    if (users.length > 0) {
      const userFiltered = users.find((r: UserData) => {
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

  setTableData(entries: LogEntryData[], users: UserData[]) {
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
        user: this.getUser(users, entry.user)
      }
    });

    this.setState({ tableData: data.reverse() });
  }

  render() {
    return (
      <Layout
        response={this.state.response}
        onLogSelect={ this.handleClick }
        fetchLog={this.fetchLog}
        tableData={ this.state.tableData }
        onFilter={ this.handleFilter }
        getUser={ this.getUser } />
    );
  }
}

export default Log;
