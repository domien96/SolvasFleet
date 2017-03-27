import React from 'react';
import { browserHistory } from'react-router';

import Layout from './Layout.tsx';

import { fetchClients } from '../../actions/client_actions.ts';

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
    browserHistory.push('/clients/' + id);
  }

  render() {
    return (
      <Layout clients={ this.state.clients } onClientSelect={ this.handleClick } />
    );
  }
}

export default Clients;
