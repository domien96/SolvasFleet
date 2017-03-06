import React      from 'react';
//import classNames from 'classnames';


export default class InfoTable extends React.Component<TableField, {}> {

  constructor(){
    super();
  }
  
  public getRows() : any {
    const head = this.props.head;
    const data = this.props.data;

    return data.map((item) => {
      const cells = head.map((headData) => {
        return (
          <td> {item[headData.key]} </td>
        );
      });
      return (
        <tr key={item.id}> {cells} </tr>
      );
    });
  }

  render() {

    const tableHead = this.props.head.map((headData) => {
      return (
        <th key={headData.key}> {headData.label} </th>
        );
    });

    const tableRows = this.getRows();

    return (
      <table className='table'>
        <thead className='thead-default'>
          {tableHead}
        </thead>
        <tbody>
          {tableRows}
        </tbody>
      </table>
    );
  }

}
