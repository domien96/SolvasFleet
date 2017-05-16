import React from 'react';
import { mount, shallow } from 'enzyme';

import ContractView from '../../javascripts/components/contract/ContractView.tsx';

var contract = { id: "10", insuranceCompany: "1", vehicle: "1", type: "billing", franchise: "100", premium: "200", startDate: "01-01-2010", endDate: "01-09-2010" }

test('test rendering of contractview', () => {
  var del = jest.fn();
  var view = mount(<ContractView contract={ contract } handleDelete={ del }/>);

  expect(view.find('button').prop('onClick')).toEqual(del);
  expect(view.find('h2').text()).toEqual('Contract 10:');
  expect(view.find('Link').prop('to')).toEqual('/contracts/10/edit');
  expect(view.find('DetailTable').prop('data')).toEqual(
    [
      { key: "contract.insuranceCompany", label: "1" },
      { key: "contract.vehicle", label: "1" },
      { key: "contract.type", label: "billing" },
      { key: "contract.franchise", label: "100" },
      { key: "contract.premium", label: "200" },
      { key: "contract.startDate", label: "01-01-2010" },
      { key: "contract.endDate", label: "01-09-2010" }
    ]);
});

import Contract from '../../javascripts/components/contract/Contract.tsx';

jest.mock('../../javascripts/actions/contract_actions.ts', () => ({
  fetchContract: jest.fn().mockImplementation((id, success) =>
    success({ id: "10", insuranceCompany: "1", vehicle: "1", type: "billing", franchise: "100", premium: "200", startDate: "01-01-2010", endDate: "01-09-2010" }
}));

test('Rendering of Contract', ()=>{
  var comp = mount(<Contract params={ {vehicleId: 10, contractId: 2 }}/>);
  var mock = require('../../javascripts/actions/contract_actions.ts');

  expect(comp.find('ContractView').prop('contract')).toEqual(contract);
});
