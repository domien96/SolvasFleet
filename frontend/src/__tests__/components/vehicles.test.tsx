import React from 'react';
import { mount, shallow } from 'enzyme';

import Layout from '../../javascripts/components/vehicles/Layout.tsx';
global.requestAnimationFrame = function(){ };

test('Layout of vehicles renders correctly', () => {
  var layout = shallow(<Layout response="test" />);
  expect(layout.find('Listing').prop('addNewRoute')).toEqual('/vehicles/new');
  expect(layout.find('Listing').prop('response')).toEqual("test");
  expect(layout.find('Listing').prop('modelName')).toEqual('vehicle');
  expect(layout.find('h2').text()).toEqual('vehicle.vehicles');
});

import Vehicles from '../../javascripts/components/vehicles/Vehicles.tsx';

jest.mock('../../javascripts/actions/vehicle_actions.ts', () => ({
  fetchVehicles: jest.fn().mockImplementation(success =>
    success({ data: [{ id: "1", licensePlate: "AA", brand: "volvo", vin: "EE", model: "R", type: "T", mileage: 1000, year: 1990, leasingCompany: 1, fleet: 2, value: 3000 }] }))
}));

test('Vehicles renders correctly', () => {
  var vehicles = mount(<Vehicles/>);
  expect(vehicles.find('Layout').prop('response')).toEqual(
    { data: [{ id: "1", licensePlate: "AA", brand: "volvo", vin: "EE", model: "R", type: "T", mileage: 1000, year: 1990, leasingCompany: 1, fleet: 2, value: 3000 }] });
})
