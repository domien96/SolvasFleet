import React from 'react';

import Layout from './Layout.tsx';

import { fetchClients } from '../../actions/client_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface State {
  response: ListResponse;
  filter: CompanyFilterData;
}

class Clients extends React.Component<{}, State> {

  constructor(props: {}) {
    super(props);
    this.state = { 
      response: { 
        total: 0, 
        first: '', 
        last: '', 
        limit: 0, 
        offset: 0, 
        previous: '', 
        next: '', 
        data: [] 
      },
      filter: {
        nameContains: '',
        vatNumber: '',
        country: '',
        type: ''
      } 
    };
    this.fetchClients = this.fetchClients.bind(this);
    this.handleFilter = this.handleFilter.bind(this);
  }

  componentDidMount() {
    this.fetchClients(undefined, this.state.filter);
  }

  fetchClients(query?: any, filter?: CompanyFilterData) {
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
    fetchClients((data: any) => {
      this.setState({ response: data });
    }, undefined, newQuery);
  }

  handleClick(id: number) {
    redirect_to(`/clients/${id}`);
  }

  handleFilter(newFilter: CompanyFilterData) {
    this.setState({ filter: newFilter });
    this.fetchClients(undefined, newFilter);
  }

  render() {
    return (
      <Layout 
        response={ this.state.response } 
        onClientSelect={ this.handleClick } 
        fetchClients={ this.fetchClients }
        onFilter={ this.handleFilter } />
    );
  }
}

export default Clients;
