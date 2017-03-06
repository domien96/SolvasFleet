import React      from 'react';
import T          from 'i18n-react';
//import classNames from 'classnames';


export default class InfoTable extends React.Component<InfoTableProps, {}> {
  
  
  public getRows() : any {
    const head = this.props.head;
    const data = this.props.data;

    return data.map((item : any) => {
      const cells = head.map((headData : any) => {
        return (
          <td> {item[headData.key]} </td>
        );
      });
      return (
        <tr key={item.id} className='table-row'> {cells} </tr>
      );
    });
  }

  render() {

    const tableHead = this.props.head.map((headData : any) => 
    (
      <th key={headData.key} scope='row' className='table-row' > {T.translate(headData.label)} </th>
    ));

    const tableRows = this.getRows();

    return (
      <div className='table-wrap'>
      <table className='table table-striped'>
        <thead className='thead-default'>
          {tableHead}
        </thead>
        <tbody>
          {tableRows}
        </tbody>
      </table>
      </div>
    );
  }
}
