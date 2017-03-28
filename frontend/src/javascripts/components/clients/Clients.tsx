import React from 'react';

import Layout from './Layout.tsx';

import { fetchClients } from '../../actions/client_actions.ts';
import { redirect_to } from'../../router.tsx';

class Clients extends React.Component<{}, Companies.State> {

  constructor(props : {}) {
    super(props);
    this.state = { clients: [] };
  }

  componentDidMount() {
    fetchClients((data : Companies.Data) => {
      this.setState({ clients: data.data })
    });
  }

  handleClick(id : number) {
    redirect_to(`/clients/${id}`);
  }

  render() {
    return (
      <Layout clients={ this.state.clients } onClientSelect={ this.handleClick } />
    );
  }
}

export default Clients;
