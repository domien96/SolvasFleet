import React from 'react';
import { mount, shallow } from 'enzyme';

import InfoTable from '../../javascripts/components/tables/InfoTable.tsx';
import DetailTable from '../../javascripts/components/tables/DetailTable.tsx';
import SimpleTable from '../../javascripts/components/tables/SimpleTable.tsx';
import SimpleList from '../../javascripts/components/tables/SimpleList.tsx';
import ExtendedInfoTable from '../../javascripts/components/tables/ExtendedInfoTable.tsx';
import RoleTable from '../../javascripts/components/tables/RoleTable.tsx';

test('InfoTable renders correctly', () => {
  const head = [{ key: "1", label: "label1" }, { key: "2", label: "label2" }];
  const data = [{ "1": "first", "2": "second" }, { "1": "north", "2": "south" }];
  const click = jest.fn();

  const table = shallow(<InfoTable head={ head } data={ data } onClick={ click }/>);

  const thead = table.find('thead');
  expect(thead.containsMatchingElement(<th key="1">label1</th>)).toBeTruthy();
  expect(thead.containsMatchingElement(<th key="2">label2</th>)).toBeTruthy();

  const tbody = table.find('tbody');
  expect(tbody.containsMatchingElement(
    <tr key="1">
      <td key="1">first</td>
      <td key="2">second</td>
    </tr>)).toBeTruthy();

  expect(tbody.containsMatchingElement(
     <tr key="2">
       <td key="1">north</td>
       <td key="2">south</td>
    </tr>)).toBeTruthy();
});

test('DetailTable renders correctly', () => {
  const data = [{ key: "key1", label: "label1" }, { key: "key2", label: "label2" }];
  const table = shallow(<DetailTable data={ data }/>);

  const tbody = table.find('tbody');
  expect(tbody.containsMatchingElement(
    <tr key="1">
      <td key="1">key1</td>
      <td key="2">label1</td>
   </tr>)).toBeTruthy();

  expect(tbody.containsMatchingElement(
    <tr key="2">
     <td key="1">key2</td>
     <td key="2">label2</td>
    </tr>)).toBeTruthy();
});

test('SimpleTable renders correctly', () => {
  const data = [["A", "B"], ["X", "Y"]];
  const table = shallow(<SimpleTable rows={ data }/>);

  const tbody = table.find('tbody');
  expect(tbody.containsMatchingElement(
    <tr key="1">
      <td>A</td>
        <td>B</td>
    </tr>)).toBeTruthy();

  expect(tbody.containsMatchingElement(
    <tr key="2">
      <td>X</td>
      <td>Y</td>
      </tr>)).toBeTruthy();
});

test('SimpleList renders correctly', () =>{
  const data = ["Alice", "Bob"];
  const list = shallow(<SimpleList list={ data }/>);
  const tbody = list.find('tbody');
  expect(tbody.containsMatchingElement(
    <tr key="1">
      <td>Alice</td>
      </tr>));

  expect(tbody.containsMatchingElement(
    <tr key="2">
      <td>Bob</td>
      </tr>));
});

test('ExtendedInfoTable renders correctly', () => {
  const head=[{ key: "1", label: "name" }, { key: "2", label: "price" }];
  const data=[{ "1": "food", "2": "200" }, { "1": "drink", "2": "300" }];

  const table = shallow(<ExtendedInfoTable head={ head } data={ data }/>);
  const thead = table.find('thead');
  const tbody = table.find('tbody');

  expect(thead.containsMatchingElement(<th key="1">name</th>)).toBeTruthy();
  expect(thead.containsMatchingElement(<th key="2">price</th>)).toBeTruthy();

  expect(tbody.find('tr').at(0).containsMatchingElement(<td key="1">food</td>)).toBeTruthy();
  expect(tbody.find('tr').at(0).containsMatchingElement(<td key="2">200</td>)).toBeTruthy();
  expect(tbody.find('tr').at(1).containsMatchingElement(<td key="1">drink</td>)).toBeTruthy();
  expect(tbody.find('tr').at(1).containsMatchingElement(<td key="2">300</td>)).toBeTruthy();
})

test('RoleTable renders correctly', () => {
  const head = [{ key: "1", label: "id" }, { key: "2", label: "name" }];
  const data = [{ id: "1", name: "admin" }, { id: "2", name: "editor" }];
  const table = shallow(<RoleTable head={ head } roles={ data } />);
  const thead = table.find('thead');
  const tbody = table.find('tbody');
  expect(thead.containsMatchingElement(<th key="1">id</th>));
  expect(thead.containsMatchingElement(<th key="2">name</th>));

  expect(tbody.find('tr').at(0).containsMatchingElement(<td>admin</td>)).toBeTruthy();
  expect(tbody.find('tr').at(1).containsMatchingElement(<td>editor</td>)).toBeTruthy();
})
