import React from 'react';
import { Route } from 'react-router';

import Contracts from '../components/contracts/Contracts.tsx';
import Contract from '../components/contract/Contract.tsx';
import AddContract from '../components/contract_form/AddContract.tsx';
import EditContract from '../components/contract_form/EditContract.tsx';


export default [
  { path: 'contracts', component: Contracts },
  { path: 'contracts/new', component: AddContract },
  { path: 'contracts/:contractId', component: Contract },
  { path: 'contracts/:contractId/edit', component: EditContract }
].map((props, i) => <Route key={ i } { ...props } />);


