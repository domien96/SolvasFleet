import React from 'react';

import Layout from './Layout.tsx';

import { fetchClients } from '../../actions/client_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface State {
  response: ListResponse;
}

class Clients extends React.Component<{}, State> {

  constructor(props: {}) {
    super(props);
    this.state = { response: { total: 0, first: '', last: '', limit: 0, offset: 0, previous: '', next: '', data: [] } };
    this.fetchClients = this.fetchClients.bind(this);
  }

  componentDidMount() {
    this.fetchClients();
  }

  fetchClients(query?: any) {
    fetchClients((data: any) => {
      this.setState({ response: data });
    }, undefined, query);
  }

  handleClick(id: number) {
    redirect_to(`/clients/${id}`);
  }

  render() {
    return (
      <Layout response={ this.state.response } onClientSelect={ this.handleClick } fetchClients={this.fetchClients}/>
    );
  }
}

export default Clients;
