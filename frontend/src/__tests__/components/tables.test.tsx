import React from 'react';
import {mount,shallow} from 'enzyme';

import InfoTable from '../../javascripts/components/tables/InfoTable.tsx';
import DetailTable from '../../javascripts/components/tables/DetailTable.tsx';

test('InfoTable renders correctly',()=>{
  const head = [{"key":"1","label":"label1"},{"key":"2","label":"label2"}];
  const data = [{"1":"first","2":"second"},{"1":"north","2":"south"}];
  const click = jest.fn();

  const table = shallow(<InfoTable head={head} data={data} onClick={click}/>);

  const thead = table.find('thead');
  expect(thead.containsMatchingElement(<th key="1">label1</th>)).toBeTruthy();
  expect(thead.containsMatchingElement(<th key="2">label2</th>)).toBeTruthy();

  const tbody = table.find('tbody');
  expect(tbody.containsMatchingElement(<tr key="1">)
    <td key="1">first</td>
    <td key="2">second</td>
   </tr>);
   expect(tbody.containsMatchingElement(<tr key="2">)
     <td key="1">north</td>
     <td key="2">west</td>
    </tr>);
});

test('DetailTable renders correctly',()=>{
  const data = [{"1":"first","2":"second"},{"1":"north","2":"south"}];
  const table = shallow(<DetailTable data={data}/>);

  const tbody = table.find('tbody');
  expect(tbody.containsMatchingElement(<tr key="1">)
    <td key="1">first</td>
    <td key="2">second</td>
   </tr>);
   expect(tbody.containsMatchingElement(<tr key="2">)
     <td key="1">north</td>
     <td key="2">west</td>
    </tr>);
})
