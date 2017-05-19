import React from 'react';
import { mount, shallow } from 'enzyme';
import FormField from '../../javascripts/components/forms/FormField.tsx';
import FormChoice from '../../javascripts/components/forms/FormChoice.tsx';
import DateForm from '../../javascripts/components/forms/DateForm.tsx';

import Checkbox from '../../javascripts/components/user_form/form/Checkbox.tsx';

const user = { id: '1', firstName: 'Pol', lastName: 'lebel', email: 'aa', password: 'nope' };

test('Render userCheckbox correctly', () => {
  const checkbox = shallow(<Checkbox label='aa'/>);
  expect(checkbox.find('label').text()).toEqual(' aa');
});

import Info from '../../javascripts/components/user_form/form/Info.tsx';

test('Render userform Info correctly', () => {
  const info = shallow(<Info user={ user } hasError={ jest.fn() }/>);
  expect(info.containsMatchingElement(<FormField value={ user.firstName } placeholder='user.firstName'/>));
  expect(info.containsMatchingElement(<FormField value={ user.lastName } placeholder='user.lastName'/>));
  expect(info.containsMatchingElement(<FormField value={ user.email } placeholder='user.email'/>));
  expect(info.containsMatchingElement(<FormField value={ user.password } placeholder='user.password'/>));
});

import UserForm from '../../javascripts/components/user_form/UserForm.tsx';

test('Render UserForm correctly', () => {
  const form = mount(<UserForm user={ user } errors={ [{ field:'error' }] } hasError={ jest.fn() }/>);
  expect(form.find('Actions').prop('submitLabel')).toEqual('form.update');
  expect(form.find('Actions').prop('cancelUrl')).toEqual('/users/1');
  expect(form.find('Errors').prop('errors')).toEqual([{ field:'error' }]);
  expect(form.find('Info').prop('user')).toEqual(user);

  form.setProps({ user: {} });
  expect(form.find('Actions').prop('submitLabel')).toEqual('form.create');
});

jest.mock('../../javascripts/actions/user_actions.ts');

function doChange(component: React.Component) {
  component.instance().handleChange('id', { target:{ value:user.id }});
  component.instance().handleChange('firstName', { target:{ value:user.firstName }});
  component.instance().handleChange('lastName', { target:{ value:user.lastName }});
  component.instance().handleChange('email', { target:{ value:user.email }});
  component.instance().handleChange('password', { target:{ value:user.password }});
}

import AddUser from '../../javascripts/components/user_form/AddUser.tsx';
test('Render AddUser correctly', () => {
  const actions = require('../../javascripts/actions/user_actions.ts');

  const form = mount(<AddUser />);
  doChange(form);

  form.instance().onSubmit({ preventDefault:jest.fn() });
  expect(actions.postUser.mock.calls.length).toBe(1);
  expect(actions.postUser.mock.calls[0][0]).toEqual(user);
  expect(form.find('UserForm').prop('user')).toEqual(user);
  expect(form.find('h2').text()).toEqual('Add A New User');
})

import EditUser from '../../javascripts/components/user_form/EditUser.tsx';

test('Render EditUser correctly', () => {
  const actions = require('../../javascripts/actions/user_actions.ts');

  const form = mount(<EditUser params={ { id: 2 } }/>);
  doChange(form);

  form.instance().onSubmit({ preventDefault:jest.fn() });
  expect(actions.putUser.mock.calls.length).toBe(1);
  expect(actions.putUser.mock.calls[0][1]).toEqual(user);
  expect(form.find('UserForm').prop('user')).toEqual(user);
  expect(form.find('h2').text()).toEqual('user.edit');
})
