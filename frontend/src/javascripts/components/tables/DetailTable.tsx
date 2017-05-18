import React from 'react';
import T from 'i18n-react';

interface Props {
  data: Table.Head.Data[];
}

const DetailTable: React.StatelessComponent<Props> = props => {
  const tableRows = props.data.map((c: Table.Head.Data, i: number) => (
    <tr key={ i } className='table-row-left'>
      <td className='first-column'>{ T.translate(c.key) }</td>
      <td className='second-column'>{ c.label }</td>
    </tr>
  ));

  return (
    <div className='table-wrap table-responsive'>
      <table className='table'>
        <tbody>
          { tableRows }
        </tbody>
      </table>
    </div>
  );
};

export default DetailTable;
