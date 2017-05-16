import React from 'react';
import { mount, shallow } from 'enzyme';

import Layout from '../../javascripts/components/users/Layout.tsx';

test('Render users layout correctly', () => {
  var layout = shallow(<Layout response="aa"><h1>TEST</h1></Layout>);
  expect(layout.find('h2').text()).toEqual('user.users');
  expect(layout.find('Listing').prop('addNewRoute')).toEqual('/users/new');
  expect(layout.find('Listing').prop('modelName')).toEqual('user');
  expect(layout.find('Listing').prop('response')).toEqual("aa");
  //Check if child components reside in the layout
  expect(layout.containsMatchingElement(<h1>TEST</h1>)).toBeTruthy();
});

import Users from '../../javascripts/components/users/Users.tsx';

jest.mock('../../javascripts/actions/user_actions.ts', () => ({
  fetchUsers: jest.fn().mockImplementation(success => success({ data: [{ id: "1", firstName: "bob", lastName: "b", email: "a", password: "bb" }] }))
}));

test('Users renders correctly', () => {
  var users = mount(<Users/>);
  expect(users.find('Layout').prop('response')).toEqual({ data: [{ id: "1", firstName: "bob", lastName: "b", email: "a", password: "bb" }] });
});
