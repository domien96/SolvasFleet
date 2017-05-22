import React from 'react';
import { mount, shallow } from 'enzyme';

import Vehicle from '../../javascripts/components/vehicle/Vehicle.tsx';

global.veh = {
  id: '1',
  licensePlate: 'AA',
  brand: 'volvo',
  vin: 'EE',
  model: 'R',
  type: 'T',
  mileage: '1000',
  year: '1990',
  leasingCompany: '1',
  fleet: '2',
  value: '3000'
};

jest.mock('../../javascripts/actions/vehicle_actions.ts', () => ({
  fetchVehicle: jest.fn().mockImplementation((id, success) => {
    success(global.veh);
  });
}));

test('Vehicle renders correctly', () => {
  const vehicle = mount(<Vehicle params={ {id:3 }}/>);
  expect(vehicle.find('VehicleView').prop('vehicle')).toEqual(global.veh);
})

import VehicleView from '../../javascripts/components/vehicle/VehicleView.tsx';

test('VehicleView renders correctly', () => {
  const view = shallow(<VehicleView vehicle={ veh } onGetFleetName={ () => 'fleetName' } onGetCompanyName={ () => 'companyName' }/>);
  expect(view.find('h2').text()).toEqual(`${global.veh} `);

  global.veh.leasingCompany = '1: companyName';
  global.veh.fleet = '2: fleetName';

  expect(view.find('DetailTable').prop('data')).toEqual(
    ['fleet', 'vin', 'licensePlate', 'brand', 'model', 'type', 'mileage', 'year', 'value', 'leasingCompany']
      .map(d => { return { key: `vehicle.${d}`, label: global.veh[d] }; }));
});

import NoVehicle from '../../javascripts/components/vehicle/NoVehicle.tsx';

test('NoVehicle renders correctly', () => {
  const novehicle = shallow(<NoVehicle/>);
  expect(novehicle.find('h2').text()).toEqual('vehicle.none.title');
});
