import React from 'react';
import { mount, shallow } from 'enzyme';
import FormField from '../../javascripts/components/forms/FormField.tsx';
import FormChoice from '../../javascripts/components/forms/FormChoice.tsx';
import DateForm from '../../javascripts/components/forms/DateForm.tsx';
import CompanyInputfield from '../../javascripts/components/client/CompanyInputfield.tsx'

import Info from '../../javascripts/components/vehicle_form/form/Info.tsx';

var vehicle = { id: "1", licensePlate: "AA", brand: "volvo", vin: "EE", model: "R", type: "T", mileage: 1000, year: 1990, leasingCompany: 1, fleet: 2, value: 3000 };

test('Info of vehicleform renders correctly', () => {
  var info = shallow(<Info vehicle={ vehicle } hasError={ jest.fn() }/>);

  expect(info.containsMatchingElement(<FormField value={ vehicle.licensePlate } placeholder='vehicle.licensePlate'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ vehicle.vin } placeholder='vehicle.vin'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ vehicle.model } placeholder='vehicle.model'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ vehicle.brand } placeholder='vehicle.brand'/>)).toBeTruthy();
  expect(info.containsMatchingElement(
    <FormChoice value={ vehicle.type } placeholder='vehicle.type' choices =
    {
      [
        { key: "PersonalVehicle", label: "vehicle.options.PersonalVehicle" },
        { key: "Van", label: "vehicle.options.Van" },
        { key: "SemiHeavyTruck", label: "vehicle.options.SemiHeavyTruck" },
        { key: "Truck+12", label: "vehicle.options.Truck+12" },
        { key: "Truck", label: "vehicle.options.Truck" }
      ]
    }/>)).toBeTruthy();

  expect(info.containsMatchingElement(<FormField value={ vehicle.mileage } placeholder='vehicle.mileage'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ vehicle.year } placeholder='vehicle.year'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<CompanyInputfield value={ vehicle.leasingCompany } placeholder='vehicle.leasingCompany'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ vehicle.fleet } placeholder='vehicle.fleet'/>)).toBeTruthy();
  expect(info.containsMatchingElement(<FormField value={ vehicle.value } placeholder='vehicle.value'/>)).toBeTruthy();
});

import VehicleForm from '../../javascripts/components/vehicle_form/VehicleForm.tsx';


test('VehicleForm renders correctly', () => {
  var form = shallow(<VehicleForm vehicle={ vehicle } hasError={ jest.fn() } errors={ [{ field: "a" }] }/>);

  expect(form.find('Actions').prop('submitLabel')).toEqual('form.update');
  expect(form.find('Info').prop('vehicle')).toEqual(vehicle);
  expect(form.find('Errors').prop('errors')).toEqual([{ field: "a" }]);
  expect(form.find('Actions').prop('cancelUrl')).toEqual('/vehicles/1');

  form.setProps({ vehicle:{} });
  expect(form.find('Actions').prop('submitLabel')).toEqual('form.create');
});


import AddVehicle from '../../javascripts/components/vehicle_form/AddVehicle.tsx';
global.requestAnimationFrame = function(){};

jest.mock('../../javascripts/actions/vehicle_actions.ts');
jest.mock('../../javascripts/actions/client_actions.ts');

function doChange(component: React.component) {
  component.instance().handleChange('id', { target: { value: vehicle.id } });
  component.instance().handleChange('licensePlate', { target: { value: vehicle.licensePlate } });
  component.instance().handleChange('vin', { target: { value: vehicle.vin } });
  component.instance().handleChange('brand', { target: { value: vehicle.brand } });
  component.instance().handleChange('type', { target: { value: vehicle.type } });
  component.instance().handleChange('mileage', { target: { value: vehicle.mileage } });
  component.instance().handleChange('year', { target: { value: vehicle.year } });
  component.instance().handleChange('leasingCompany', { target: { value: vehicle.leasingCompany } });
  component.instance().handleChange('value', { target: { value: vehicle.value } });
  component.instance().handleChange('fleet', { target: { value: vehicle.fleet } });
  component.instance().handleChange('model', { target: { value: vehicle.model } });
}

test('Render AddVehicle correctly', () => {
  const actions =require('../../javascripts/actions/vehicle_actions.ts');

  const form = mount(<AddVehicle />);
  doChange(form);

  form.instance().onSubmit({ preventDefault:jest.fn() });
  expect(actions.postVehicle.mock.calls.length).toBe(1);
  expect(actions.postVehicle.mock.calls[0][0]).toEqual(vehicle);
  expect(form.find('VehicleForm').prop('vehicle')).toEqual(vehicle);
  expect(form.find('h2').text()).toEqual('vehicle.addNew');
});

import EditVehicle from '../../javascripts/components/vehicle_form/EditVehicle.tsx';

test('Render EditVehicle correctly', () => {
  const actions =require('../../javascripts/actions/vehicle_actions.ts');

  const form = mount(<EditVehicle params={ { id: 1 } }/>);
  doChange(form);

  form.instance().onSubmit({ preventDefault: jest.fn() });
  expect(actions.putVehicle.mock.calls.length).toBe(1);
  expect(actions.putVehicle.mock.calls[0][1]).toEqual(vehicle);
  expect(form.find('VehicleForm').prop('vehicle')).toEqual(vehicle);
  expect(form.find('h2').text()).toEqual('vehicle.edit');
});
