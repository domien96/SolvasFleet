import React from 'react';
import { mount, shallow } from 'enzyme';

import FormChoice from '../../javascripts/components/forms/FormChoice.tsx';
import Fleets from '../../javascripts/components/fleets/Fleets.tsx';

const fleetsData: FleetData[] = [ { id: 1, name: 'Fleet1', company: 1, facturationPeriod: 3, paymentPeriod: 1 } ];

test('render Fleets correctly', () => {
  const fleets = mount(<Fleets fleets={ fleetsData } company={ 1 }/>);

  expect(fleets.find('FleetsCard').prop('fleets')).toBe(fleetsData);
  expect(fleets.find('FleetsCard').prop('formIsVisible')).toBe(false);
});

import FleetsCard from '../../javascripts/components/fleets/FleetsCard.tsx';

test('render FleetsCard correctly', () => {
  const onSubmit = jest.fn();

  const fleetsCard = mount(<FleetsCard onSubmit={ onSubmit } formIsVisible={ false } fleets={ fleetsData } fleet={ fleetsData[0] } errors={ [] }/>);
  expect(fleetsCard.find('Collapse').prop('in')).toBeFalsy();

  fleetsCard.setProps({ formIsVisible: true });
  expect(fleetsCard.find('Collapse').prop('in')).toBe(true);
  expect(fleetsCard.find('Link').prop('to')).toEqual('/fleets/1');
  expect(fleetsCard.find('Link').find('h3').at(0).text()).toEqual('Fleet1');
});

import FleetForm from '../../javascripts/components/fleets/FleetForm.tsx';

test('render FleetForm correctly', () => {
  const form = mount(<FleetForm fleet={ fleetsData[0] } onSubmit={ jest.fn() } handleChange={ jest.fn() }/>);

  expect(form.containsMatchingElement(<label>fleet.name: </label>))
  expect(form.containsMatchingElement(<FormChoice value={ '3' } placeholder='Facturation Period'/>)).toBeTruthy();
  expect(form.containsMatchingElement(<FormChoice value={ '1' } placeholder='Payment Period'/>)).toBeTruthy();
  // (for fleet-view PR) expect(form.containsMatchingElement(<input value={ 'Fleet1' }/>)).toBeTruthy();
});
