import React from 'react';
import { mount, shallow } from 'enzyme';

import User from '../../javascripts/components/user/User.tsx';

jest.mock('../../javascripts/actions/user_actions.ts', () => ({
  fetchUser: jest.fn().mockImplementation((id, success) => success({ id: "1", firstName: "Bob", lastName: "b", email: "test", password: "nop" })),
  fetchFunctionsByUser: jest.fn()
}));

var user = { id: "1", firstName: "Bob", lastName: "b", email: "test", password: "nop" };

test('User renders correctly', ()=>{
  var u = mount(<User params={ { id: 3 }}/>);
  expect(u.find('UserCard').prop('user')).toEqual(user);
});

import UserCard from '../../javascripts/components/user/UserCard.tsx';

test('UserCard renders correctly', () => {
  var card = shallow(<UserCard user={ user }/>);
  expect(card.find('h2').text()).toEqual('Bob b');
  expect(card.find('DetailTable').prop('data')).toEqual(
    [
      { key: "user.firstName", label: user.firstName },
      { key: "user.lastName", label: user.lastName },
      { key: "user.email", label: user.email }
    ]
  );
  expect(card.find('UserFunctions').prop('user')).toEqual(user);
});
