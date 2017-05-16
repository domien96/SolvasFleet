import React from 'react';
import { mount, shallow } from 'enzyme';

import Listing from '../../javascripts/components/app/Listing.tsx';

test('Listing renders correctly', () => {
  const data = { data: [{ a: "1", b: "2" }] };
  const list = shallow(<Listing columns={ ["a", "b"] } addNewRoute="new" modelName="test" response={ data } />);
  expect(list.find('Link').prop('to')).toEqual('new');
  expect(list.find('InfoTable').prop('head')).toEqual([{ key:"a", label:"test.a" }, { key:"b", label:"test.b" }];
  expect(list.find('Pagination').prop('response')).toEqual(data);
  expect(list.find('InfoTable').prop('data')).toEqual(data.data);
})

import Pagination from '../../javascripts/components/pagination/Pagination.tsx';

test('Pagination renders correctly', () => {
  var list = [];
  for(var i=0; i<100; i++)
  {
    list.push({ id:i, name:"test"+i });
  }
  const pagination = shallow(<Pagination response={ {data:list, total:100, limit:20 }}/>);
  expect(pagination.find('PageButton').length).toBe(5);
  expect(pagination.find('MenuItem').length).toBe(3);
})
