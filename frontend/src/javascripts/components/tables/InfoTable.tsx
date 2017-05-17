import React from 'react';
import T from 'i18n-react';

interface Props {
  head: Table.Head.Data[];
  data: any;
  onClick: (e: any) => void;
}

const InfoTable: React.StatelessComponent<Props> = props => {
  const { head, data, onClick } = props;

  const tableHead = head.map((headData: Table.Head.Data) => (
    <th key={ headData.key } scope='row' className='table-row' >{ T.translate(headData.label) }</th>
  ));

  const tableRows = data.map((item: any, i: number) => {
    const cells = head.map((headData: Table.Head.Data, j: number) => {
      return (
        <td key={ j }>{ (item[headData.key]).toString() }</td>
      );
    });
    return (
      <tr key={ i } onClick={ () => onClick(item.id) } className='table-row'>{cells}</tr>
    );
  });

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
};

export default InfoTable;
