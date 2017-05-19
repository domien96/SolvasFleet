import React from 'react';
import { mount, shallow } from 'enzyme';

import User from '../../javascripts/components/user/User.tsx';

global.user = { id: '1', firstName: 'Bob', lastName: 'b', email: 'test', password: 'nop' };

jest.mock('../../javascripts/actions/user_actions.ts', () => ({
  fetchUser: jest.fn().mockImplementation((id, success) => success(global.user)),
  fetchFunctionsByUser: jest.fn()
}));

test('User renders correctly', ()=>{
  const u = mount(<User params={ { id: 3 }}/>);
  expect(u.find('UserCard').prop('user')).toEqual(global.user);
});

import UserCard from '../../javascripts/components/user/UserCard.tsx';

test('UserCard renders correctly', () => {
  const card = shallow(<UserCard user={ global.user }/>);
  expect(card.find('h2').text()).toEqual('Bob b');
  expect(card.find('DetailTable').prop('data')).toEqual(
    [
      { key: 'user.firstName', label: global.user.firstName },
      { key: 'user.lastName', label: global.user.lastName },
      { key: 'user.email', label: global.user.email }
    ]
  );
  expect(card.find('UserFunctions').prop('user')).toEqual(global.user);
});
