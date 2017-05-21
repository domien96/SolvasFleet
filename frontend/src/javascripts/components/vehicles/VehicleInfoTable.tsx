import React from 'react';
import T from 'i18n-react';

interface Props {
  head: Table.Head.Data[];
  data: any;
  onClick: (e: any) => void;
}

const VehicleInfoTable: React.StatelessComponent<Props> = props => {
  const { head, data, onClick } = props;

  const tableHead = head.map((headData: Table.Head.Data) => (
    <th key={ headData.key } scope='row' className='table-row' >{ T.translate(headData.label) }</th>
  ));

  const tableRows = data.map((item: any, i: number) => {
    const cells = head.map((headData: Table.Head.Data, j: number) => {
      let cell = '';
      if (item[headData.key]) {
        cell = (item[headData.key]).toString();
      }
      if (j == 0) {
        return (
          <td key={ j }><a className='cell-link' href={`/clients/${item.companyId}/fleets/${item.fleetId}`}>{ cell }</a></td>
        );
      }
      return (
        <td key={ j }>{ cell }</td>
      );
    });
    return (
      <tr key={ i } onClick={ () => onClick(item.id) } className='table-row'>{ cells }</tr>
    );
  });

  return (
    <div className='table-wrap'>
      <table className='table table-striped table-hover table-responsive'>
        <thead className='thead-default'>
          <tr>{ tableHead }</tr>
        </thead>
        <tbody>
          { tableRows }
        </tbody>
      </table>
    </div>
  );
};

export default VehicleInfoTable;
