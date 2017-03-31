import React from 'react';
import { Route } from 'react-router';

import Clients from '../components/clients/Clients.tsx';
import AddClient from '../components/client_form/AddClient.tsx';
import EditClient from '../components/client_form/EditClient.tsx';
import Client from '../components/client/Client.tsx';

import Fleets from '../components/fleets/Fleets.tsx';

export default [
  <Route path="clients" component={ Clients } />,
  <Route path="clients/new" component={ AddClient } />,
  <Route path="clients/:id" component={ Client } />,
  <Route path="clients/:id/edit" component={ EditClient } />,
  <Route path="clients/:id/fleets" component={ Fleets } />
]
