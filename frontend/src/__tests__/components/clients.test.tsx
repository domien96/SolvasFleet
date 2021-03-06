import React from 'react';
import { mount, shallow } from 'enzyme';

import Layout from '../../javascripts/components/clients/Layout.tsx';

global.data = [{ id: '1', name: 'a', vatNumber: 'b' }, { id: '2', name: 'c', vatNumber: 'd' }];

test('Layout of clients renders correctly', () => {
  const layout = shallow(<Layout response='test' />);
  expect(layout.find('Listing').prop('addNewRoute')).toEqual('/clients/new');
  expect(layout.find('Listing').prop('response')).toEqual('test');
  expect(layout.find('Listing').prop('modelName')).toEqual('company');
  expect(layout.find('h2').text()).toEqual('company.clients');
});

import Clients from '../../javascripts/components/clients/Clients.tsx';

jest.mock('../../javascripts/actions/client_actions.ts', () => ({
  fetchClients: jest.fn().mockImplementation(success => success({ data: global.data }))
}));

test('Clients renders correctly', () => {
  const clients = mount(<Clients/>);
  expect(clients.find('Layout').prop('response')).toEqual({ data: global.data });
});
