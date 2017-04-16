import React from 'react';

import Layout from './Layout.tsx';

import { fetchClients } from '../../actions/client_actions.ts';
import { redirect_to } from'../../router.tsx';

interface State {
  clients : Company[];
}

class Clients extends React.Component<{}, State> {

  constructor(props : {}) {
    super(props);
    this.state = { clients: [] };
    this.fetchClients=this.fetchClients.bind(this);
  }

  componentDidMount() {
    this.fetchClients();
  }

  fetchClients(query?:any) {
    fetchClients((data : Companies.Data) => {
      this.setState({ clients: data.data })
    },undefined,query);
  }

  handleClick(id : number) {
    redirect_to(`/clients/${id}`);
  }

  render() {
    return (
      <Layout clients={ this.state.clients } onClientSelect={ this.handleClick } fetchClients={this.fetchClients}/>
    );
  }
}

export default Clients;
