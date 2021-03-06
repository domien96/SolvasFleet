import React from 'react';
import { mount, shallow } from 'enzyme';

import Client from '../../javascripts/components/client/Client.tsx';

global.fleet = { id:'1', name: 'flet', company: '1', facturationPeriod: '1', paymentPeriod: '30' };

jest.mock('../../javascripts/actions/client_actions.ts', () => ({
  fetchClient: jest.fn().mockImplementation((id, success) =>
    success({ id: '1', name: 'ClientName', vatNumber: 'b', address: { street: 'a', postalCode: 'b' ,city: 'r', country: 'o', houseNumber: '5' } }))
  })
);

jest.mock('../../javascripts/actions/fleet_actions.ts', () => ({
  fetchFleets: jest.fn().mockImplementation((id, success) => success({ data: [ global.fleet ] }))
}));

test('Client renders correctly',() => {
  const client = mount(<Client params={ { 'id': 1 } }/>);

  expect(client.find('Link').at(0).prop('to')).toEqual('/clients/1/edit');
  expect(client.find('Fleets').prop('fleets')).toEqual([ global.fleet ]);

  expect(client.find('DetailTable').prop('data')).toEqual(
    [
      { key:'company.vatNumber', label: 'b' },
      { key: 'company.phoneNumber', label: '' },
      { key: 'company.types', label: '' },
      { key: 'company.address.street', label: 'a' },
      { key: 'company.address.houseNumber', label: '5' },
      { key: 'company.address.postalCode', label: 'b' },
      { key: 'company.address.city', label: 'r' },
      { key: 'company.address.country', label: 'o' }
    ]
  );

  expect(client.find('h2').at(0).text()).toEqual('ClientName');
  expect(client.find('Contracts').prop('companyId')).toEqual(1);
})
