import React from 'react';
import { mount, shallow } from 'enzyme';

import HiddenFilter from '../../javascripts/components/filters/HiddenFilter.tsx';

test('HiddenFilter renders correctly', () => {
  const reset = jest.fn();
  const show = jest.fn();

  const filter = shallow(<HiddenFilter onReset={ reset } onShow={ show }/>);

  filter.find('button').first().simulate('click');
  expect(reset.mock.calls.length).toBe(1);
  filter.find('button').at(1).simulate('click');
  expect(show.mock.calls.length).toBe(1);
});

import Filter from '../../javascripts/components/filters/Filter.tsx';
describe('Filter renders correctly', () => {
  const choices=[{ name: "a", eventKey: "1", callback: jest.fn() }];
  const selections = [{ name: "name1", title: "o", choices: choices }];
  const inputs = [{ name: "inputname1", value: "RR", type: "st", callback: jest.fn() }];
  const typeaheads = [{ name: "typeahead1", data: ["a", "b", "c"], selected: ["a"], callback: jest.fn() }];
  const filter = shallow(<Filter selections={ selections } inputfields={ inputs } typeaheadfields={ typeaheads }/>);

  test('SelectionFields', () => {
    expect(filter.find('MenuItem').prop('eventKey')).toEqual("1");
    expect(filter.find('DropdownButton').prop('id')).toEqual('o');
    expect(filter.find('DropdownButton').parent().parent().find('label').text()).toEqual("name1");
  });

  test('InputFields', () => {
    expect(filter.find('input').parent().find('label').text()).toEqual('inputname1');
    expect(filter.find('input').prop('placeholder')).toEqual('inputname1');
    expect(filter.find('input').prop('value')).toEqual("RR");
  });

  test('TypeaheadFields', ()=>{
    //Lukt niet om Typeahead te vinden ...
  });
});
