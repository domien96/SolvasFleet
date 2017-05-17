import React from 'react';
import { mount, shallow } from 'enzyme';
import FormField from '../../javascripts/components/forms/FormField.tsx';
import FormChoice from '../../javascripts/components/forms/FormChoice.tsx';
import DateForm from '../../javascripts/components/forms/DateForm.tsx';
import Info from '../../javascripts/components/contract_form/form/Info.tsx';
import CompanyInputfield from '../../javascripts/components/client/CompanyInputfield.tsx';
import VehicleInputfield from '../..//javascripts/components/vehicle/VehicleInputfield.tsx';

var contract = { id: "10", insuranceCompany: "1", vehicle: "1", type: "billing", franchise: "100", premium: "200", startDate: "2016-05-19T12:00:00.000Z", endDate: "2017-05-19T12:00:00.000Z" }

test('Info of ContractForm', () => {
  const info = mount(<Info contract={ contract } types={ ['billing', 'payment'] } hasError={ jest.fn() }/>);

  expect(info.containsMatchingElement(<FormField value={ contract.franchise } placeholder='contract.franchise'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<CompanyInputfield value={ [contract.insuranceCompany] } placeholder='contract.insuranceCompany'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ contract.premium } placeholder='contract.premium'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormChoice value={ contract.type } placeholder='contract.type'
    choices={ [{ key: "billing", label: "contract.types.billing" }, { key: "payment", label: "contract.types.payment" }] }/>)).toBeTruthy();
  expect(info.containsMatchingElement(<VehicleInputfield value={ contract.vehicle } placeholder='contract.vehicle'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<DateForm value={ contract.startDate } label='contract.startDate'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<DateForm value={ contract.endDate } label='contract.endDate'/>)).toBeTruthy();
});

import ContractForm from '../../javascripts/components/contract_form/ContractForm.tsx';

test('Rendering contractform', () => {
  const form = mount(<ContractForm contract={ contract } types={ ['billing', 'payment'] } errors={ [{ field: "a" }] } hasError={ jest.fn() }/>);
  expect(form.find('Actions').prop('submitLabel')).toEqual('form.update');
  expect(form.find('Info').prop('contract')).toEqual(contract);
  expect(form.find('Errors').prop('errors')).toEqual([{ field: "a" }]);

  form.setProps({ contract: {} });
  expect(form.find('Actions').prop('submitLabel')).toEqual('form.create');
});

import AddContract from '../../javascripts/components/contract_form/AddContract.tsx';
jest.mock('../../javascripts/actions/contract_actions.ts');


function doChange(component: React.Component) {
  component.instance().handleChange("id", { target: { value: contract.id } }, null);
  component.instance().handleChange("insuranceCompany", { target: { value: contract.insuranceCompany } }, null);
  component.instance().handleChange("vehicle", { target: { value: contract.vehicle } }, null);
  component.instance().handleChange("type", { target: { value: contract.type } }, null);
  component.instance().handleChange("franchise", { target: { value: contract.franchise } }, null);
  component.instance().handleChange("premium", { target: { value: contract.premium } }, null);
  component.instance().handleChange("startDate", contract.startDate, "date");
  component.instance().handleChange("endDate", contract.endDate, "date");
}

test('Rendering addcontract', () => {
  var form = mount(<AddContract/>);
  const post =require('../../javascripts/actions/contract_actions.ts');

  expect(form.find('h2').text()).toEqual('contract.addNew');
  doChange(form);

  form.instance().onSubmit({ preventDefault: jest.fn() });
  expect(post.postContract.mock.calls.length).toBe(1);
  expect(post.postContract.mock.calls[0][0]).toEqual(contract);
  expect(form.find('ContractForm').prop('contract')).toEqual(contract);
});

import EditContract from '../../javascripts/components/contract_form/EditContract.tsx';

test('Rendering of editcontract', ()=>{
  var form = mount(<EditContract params={ { contractId: 1 } }/>);
  const put =require('../../javascripts/actions/contract_actions.ts');

  expect(form.find('h2').text()).toEqual('contract.edit');
  doChange(form);

  form.instance().onSubmit({ preventDefault: jest.fn() });
  expect(put.putContract.mock.calls.length).toBe(1);
  expect(put.putContract.mock.calls[0][1]).toEqual(contract);

  expect(form.find('ContractForm').prop('contract')).toEqual(contract);
});
