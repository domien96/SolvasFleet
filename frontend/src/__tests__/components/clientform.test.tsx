import React from 'react';
import { mount, shallow } from 'enzyme';

import Info from '../../javascripts/components/client_form/form/Info.tsx';
import ClientForm from '../../javascripts/components/client_form/ClientForm.tsx';

import FormField from '../../javascripts/components/forms/FormField.tsx';
import FormChoice from '../../javascripts/components/forms/FormChoice.tsx';

const address = { city: "c", country: "b", street: "s", postalCode: "9000", houseNumber: "30" };
var company = { id: "10", name: "coca-cola", vatNumber: "AA", phoneNumber: "093919089", type: "InsuranceCompany", address: address };

describe('Info of clientform', () => {
  const hasError = jest.fn();
  const info = shallow(<Info company={ company } hasError={ hasError }/>);

  test('basic fields', () => {
    expect(info.containsMatchingElement(<FormField value="coca-cola" placeholder="company.name"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormField value="AA" placeholder="company.vatNumber"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormField value="093919089" placeholder="company.phoneNumber"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormChoice value="InsuranceCompany" placeholder="company.types"/>)).toBeTruthy();
  });

  test('addresses', () => {
    expect(info.containsMatchingElement(<FormField value="c" placeholder="company.address.city"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormField value="b" placeholder="company.address.country"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormField value="s" placeholder="company.address.street"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormField value="9000" placeholder="company.address.postalCode"/>)).toBeTruthy();
    expect(info.containsMatchingElement(<FormField value="30" placeholder="company.address.houseNumber"/>)).toBeTruthy();
  });

  test('correct choices for types', () => {
    expect(info.containsMatchingElement(
      <FormChoice choices =
      { [
          { key: "Customer", label: "company.type.Customer" },
          { key: "LeasingCompany", label: "company.type.LeasingCompany" },
          { key: "InsuranceCompany", label: "company.type.InsuranceCompany" }
        ] }/>)).toBeTruthy();
  });
});

describe('Rendering clientform', () => {
  const clientForm = shallow(<ClientForm company={ company }/>);

  expect(clientForm.find('Actions').prop('submitLabel')).toEqual('form.update');
  company.id = null;
  expect(clientForm.find('Actions').prop('cancelUrl')).toEqual('/clients/10');
  clientForm.setProps({ company: company });
  expect(clientForm.find('Actions').prop('submitLabel')).toEqual('form.create');
  expect(clientForm.find('Actions').prop('cancelUrl')).toEqual('/clients/');
  expect(clientForm.find('Info').prop('company')).toEqual(company);
});


function dohandleChange(component: React.Component) {
  component.instance().handleChange('id', false, { target: { value: company.id }});
  component.instance().handleChange('name', false, { target: { value: company.name }});
  component.instance().handleChange('vatNumber', false, { target: { value: company.vatNumber }});
  component.instance().handleChange('type', false, { target: { value: company.type }});
  component.instance().handleChange('phoneNumber', false, { target: { value: company.phoneNumber }});
  component.instance().handleChange('city', true, { target: { value: company.address.city }});
  component.instance().handleChange('country', true, { target: { value: company.address.country }});
  component.instance().handleChange('street', true, { target: { value: company.address.street }});
  component.instance().handleChange('postalCode', true, { target: { value: company.address.postalCode }});
  component.instance().handleChange('houseNumber', true, { target: { value: company.address.houseNumber }});
}

import AddClient from '../../javascripts/components/client_form/AddClient.tsx';
import postClient from '../../javascripts/actions/client_actions.ts';

jest.mock('../../javascripts/actions/client_actions.ts');

describe('Rendering the addclient component', () => {
  const post = require('../../javascripts/actions/client_actions.ts');

  var add = mount(<AddClient/>);
  dohandleChange(add);

  add.instance().onSubmit({ preventDefault: jest.fn() });
  expect(post.postClient.mock.calls.length).toBe(1);
  expect(post.postClient.mock.calls[0][0]).toEqual(company);
  expect(add.find('ClientForm').prop('company')).toEqual(company);
});

import EditClient from '../../javascripts/components/client_form/EditClient.tsx';

describe('Rendering the editclient component', ()=>{
  const put =require('../../javascripts/actions/client_actions.ts');

  var edit = mount(<EditClient params={ { id: "2" }}/>);
  edit.setState({ company: { address: {} }});
  dohandleChange(edit);

  edit.instance().onSubmit({ preventDefault:jest.fn() });
  expect(put.putClient.mock.calls.length).toBe(1);
  expect(put.putClient.mock.calls[0][1]).toEqual(company);
  expect(edit.find('ClientForm').prop('company')).toEqual(company);
});
