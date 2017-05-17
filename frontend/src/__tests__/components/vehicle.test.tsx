import React from 'react';
import { mount, shallow } from 'enzyme';

import Vehicle from '../../javascripts/components/vehicle/Vehicle.tsx';


jest.mock('../../javascripts/actions/vehicle_actions.ts', () => ({
  fetchVehicle: jest.fn().mockImplementation((id, success) =>
    success({ id: "1", licensePlate: "AA", brand: "volvo", vin: "EE", model: "R", type: "T", mileage: "1000", year: "1990", leasingCompany: "1", fleet: "2", value: "3000" }))
}));

var veh = { id: "1", licensePlate: "AA", brand: "volvo", vin: "EE", model: "R", type: "T", mileage: "1000", year: "1990", leasingCompany: "1", fleet: "2", value: "3000" };

test('Vehicle renders correctly', () => {
  var vehicle = mount(<Vehicle params={ {id:3 }}/>);
  expect(vehicle.find('VehicleView').prop('vehicle')).toEqual(veh);
})

import VehicleView from '../../javascripts/components/vehicle/VehicleView.tsx';

test('VehicleView renders correctly', () => {
  var view = shallow(<VehicleView vehicle={ veh } onGetFleetName={ () => "fleetName" } onGetCompanyName={ () => "companyName" }/>);
  expect(view.find('h2').text()).toEqual(veh.vin+" ");

  veh.leasingCompany="1: companyName";
  veh.fleet="2: fleetName";

  expect(view.find('DetailTable').prop('data')).toEqual(
    ["fleet", "vin", "licensePlate", "brand", "model", "type", "mileage", "year", "value", "leasingCompany"]
      .map(d => { return { key:"vehicle."+d, label:veh[d] }; }));
});

import NoVehicle from '../../javascripts/components/vehicle/NoVehicle.tsx';

test('NoVehicle renders correctly', () => {
  var novehicle = shallow(<NoVehicle/>);
  expect(novehicle.find('h2').text()).toEqual('vehicle.none.title');
});
