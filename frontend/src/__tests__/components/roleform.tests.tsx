import React from 'react';
import {shallow,mount} from 'enzyme';

import Checkbox from '../../javascripts/components/auth/role/form/Checkbox.tsx';
import Info from '../../javascripts/components/auth/role/form/Info.tsx';
import FormField from '../../javascripts/components/forms/FormField.tsx';
import RoleForm from '../../javascripts/components/auth/role/form/RoleForm.tsx';

test('Checkbox renders correctly',()=>{
  const label = "aaa";
  const active = true;

  const checkbox = shallow(<Checkbox label={label} active={true}/>);

  const input = checkbox.find('input');
  expect(input.prop('checked')).toBeTruthy();
  expect(input.prop('value')).toEqual(label);
  expect(checkbox.find('label').text()).toEqual(' '+label);

  checkbox.setProps({active:false});
  expect(checkbox.find('input').prop('checked')).toBeFalsy();
})

const permissions = ["WCompanies","RCompanies","RRole"];
const role = {"id":"1","name":"editor","permissions":["WCompanies","RCompanies"]};

test('Info of roleform renders correctly',()=>{
    const info = shallow(<Info role={role} permissions={permissions} hasError={jest.fn()}/> );

  expect(info.containsMatchingElement(<Checkbox label="RCompanies" active={true}/>)).toBeTruthy();
  expect(info.containsMatchingElement(<Checkbox label="WCompanies" active={true}/>)).toBeTruthy();
  expect(info.containsMatchingElement(<Checkbox label="RRole" active={false}/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value="editor"/>)).toBeTruthy();
})

test('RoleForm renders correctly',()=>{
  const roleform = shallow(<RoleForm role={role} permissions={permissions}/>);
  expect(roleform.find('Actions').prop('submitLabel')).toEqual('form.update');


  roleform.setProps({role:{"name":"admin","permission":[]}});
  expect(roleform.find('Actions').prop('submitLabel')).toEqual('form.create');

  //Permission have to be sorted
  expect(roleform.find('Info').prop('permissions')).toEqual(["RCompanies","RRole","WCompanies"]);
})
