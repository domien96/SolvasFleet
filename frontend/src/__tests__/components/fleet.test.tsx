import React from 'react';
import { mount, shallow } from 'enzyme';

import Fleet from '../../javascripts/components/fleet/Fleet.tsx';

jest.mock('../../javascripts/actions/vehicle_actions.ts', () => ({
  fetchVehicles: jest.fn().mockImplementation(success => success({ data: [{ id: "1", type: "A" }, { id: "2", type: "B" }] }));
}));


test('Fleet renders correctly', () => {
  var fleet = mount(<Fleet params={ { id: 1, companyId: 2 } }/>);

  expect(fleet.find('.subfleets').children().at(0).prop('type')).toBe('A');
  expect(fleet.find('.subfleets').children().at(0).prop('vehicles')).toEqual([{ id: "1", type: "A" }]);
  expect(fleet.find('.subfleets').children().at(1).prop('type')).toBe('B');
  expect(fleet.find('.subfleets').children().at(1).prop('vehicles')).toEqual([{ id: "2", type: "B" }]);
});

import Subfleet from '../../javascripts/components/fleet/SubfleetRow.tsx';

jest.mock('../../javascripts/components/fleet/VehicleRow.tsx');

test('Subfleet renders correctly', () => {
  var subfleet = mount(<Subfleet type='A' vehicles={ [{ id: "1", type: "A" }] } isChecked={ jest.fn() } isIndeterminate={ jest.fn() } showVehicles={ true }/>);

  expect(subfleet.find('Vehicles').children().prop('vehicle')).toEqual({ id: "1", type: "A" });
});
