import React from 'react';
import { Route } from 'react-router';

import Clients from '../components/clients/Clients.tsx';
import AddClient from '../components/client_form/AddClient.tsx';
import EditClient from '../components/client_form/EditClient.tsx';
import Client from '../components/client/Client.tsx';

import Fleets from '../components/fleets/Fleets.tsx';

export default [
  { path: 'clients', component: Clients },
  { path: 'clients/new', component: AddClient },
  { path: 'clients/:id', component: Client },
  { path: 'clients/:id/edit', component: EditClient },
  { path: 'clients/:id/fleets', component: Fleets }
].map((props, i) => <Route key={ i} { ...props } />);
