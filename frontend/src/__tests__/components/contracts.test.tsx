import React from 'react';
import { mount, shallow } from 'enzyme';

import Contracts from '../../javascripts/components/contracts/Contracts.tsx';

const contract = {
  id: '10',
  insuranceCompany: '1',
  vehicle: '1',
  type: 'billing',
  franchise: '100',
  premium: '200',
  startDate: '01-01-2010',
  endDate: '01-09-2010',
}

test('Render contracts correctly',()=>{
  const fetchMethod = (params: ContractParams, success) => {
    success({ data: [contract] });
  };
  const contracts = mount(<Contracts vehicleId={ 1 } fetchMethod={ fetchMethod }/>);
  //expect(contracts.find('Listing').prop('response')).toEqual({ data: [contract] });
})
