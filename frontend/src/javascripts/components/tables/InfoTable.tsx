import React from 'react';
import T     from 'i18n-react';

class InfoTable extends React.Component<Table.Info.Props, {}> {

  public getRows() : any {
    var { head, data } = this.props;

    return data.map((item : any, i : number) => {
      const cells = head.map((headData : Table.Head.Data, j : number) => {
        return (
          <td key={ j }>{ item[headData.key] }</td>
        );
      });
      return (
        <tr key={ i } onClick={ () => this.props.onClick(item.id) } className='table-row'>{cells}</tr>
      );
    });
  }

  render() {
    const tableHead = this.props.head.map((headData : Table.Head.Data) =>
    (
      <th key={ headData.key } scope='row' className='table-row' >{ T.translate(headData.label) }</th>
    ));

    const tableRows = this.getRows();

    return (
      <div className='table-wrap'>
      <table className='table table-striped table-hover'>
        <thead className='thead-default'>
          <tr>{tableHead}</tr>
        </thead>
        <tbody>
          {tableRows}
        </tbody>
      </table>
      </div>
    );
  }
}

export default InfoTable;
