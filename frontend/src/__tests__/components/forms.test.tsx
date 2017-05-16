import React from 'react';
import { shallow, mount } from 'enzyme';
import DateForm from '../../javascripts/components/forms/DateForm.tsx';

test('Dateform has correct label + haserror', () => {
  const callBackMock = jest.fn();
  const dateform = mount(<DateForm label="test" hasError={ false } callBack={ callBackMock } />);

  expect(dateform.find('label').text()).toEqual('test');

  expect(dateform.hasClass('has-error')).toBeFalsy();
  dateform.setProps({ hasError: true });
  expect(dateform.hasClass('has-error')).toBeTruthy();
  );
})

import FormField from '../../javascripts/components/forms/FormField.tsx';

test('FormField has correct label + hasError classes', () =>
{
  const placeholder = "HOLD THIS";
  const call = jest.fn();
  const formfield = shallow(<FormField placeholder={ placeholder } type="a" hasError={ true } callback={ call }/>);

  expect(formfield.find('label').text()).toEqual(placeholder);
  expect(formfield.hasClass('has-error')).toBeTruthy();
  formfield.setProps({ hasError: false });
  expect(formfield.hasClass('has-error')).toBeFalsy();
})

import FormChoice from '../../javascripts/components/forms/FormChoice.tsx';

test('FormChoice renders all choices + placeholder', () =>
{
  const ph = "PH";
  const cb = jest.fn();
  const value = "1";
  const choices = [{ key: "1", label: "a" }, { key: "2", label: "b" }, { key: "3", label: "c" }];
  const formchoice = shallow(<FormChoice placeholder={ ph } callBack={ cb } value = { value } choices={ choices }/>);

  expect(formchoice.containsMatchingElement(<option value="1">a</option>)).toBeTruthy();
  expect(formchoice.containsMatchingElement(<option value="2">b</option>)).toBeTruthy();
  expect(formchoice.containsMatchingElement(<option value="3">c</option>)).toBeTruthy();
  expect(formchoice.find('label').text().startsWith(ph)).toBeTruthy();
});
