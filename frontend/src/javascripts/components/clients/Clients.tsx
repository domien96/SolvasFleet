import React from 'react';

import Layout from './Layout.tsx';

import { fetchClients } from '../../actions/client_actions.ts';
import { redirect_to } from '../../routes/router.tsx';
import { createQuery } from '../../utils/utils.ts';

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
        type: '',
        archived: 'false',
      }
    };
    this.fetchClients = this.fetchClients.bind(this);
    this.handleFilter = this.handleFilter.bind(this);
  }

  componentDidMount() {
    this.fetchClients(undefined, this.state.filter);
  }

  fetchClients(query?: any, filter?: CompanyFilterData) {
    let newQuery = createQuery(query, filter);
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
