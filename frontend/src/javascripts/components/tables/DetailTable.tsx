import React from 'react';
import T     from 'i18n-react';

export class DetailTable extends React.Component<Table.Detail.Props, {}> {

render() {
    
    const tableRows = this.props.data.map((c : Table.Head.Data, i : number) =>
    (  
      <tr key={i} className='table-row-left'>
        <td>{ T.translate(c.key) }</td>
        <td>{ c.label }</td>
      </tr>
    ));

    return (
      <div>
        <div><h3>Information</h3></div>
        <div className='table-wrap'>
        <table className='table'>
          <tbody>
            {tableRows}
          </tbody>
        </table>
        </div>
      </div>
    );
  }
}

export function th(key : string, label : string | number) : Table.Head.Data {
  return { key: key, label: label };
}
