import React from 'react';

interface Props {
  rows: Permission.Data[];
}

const SimpleTable: React.StatelessComponent<Props> = props => {

  const tableRows = props.rows.map((c: Permission.Data, i: number) => (
    <tr key={ i }>
      <td className='first-column'>{ c[0] }</td>
      <td className='second-column'>{ c[1] }</td>
    </tr>
  ));

  return (
    <div>
      <div className='table-wrap table-responsive table-striped table-bordered table-hover table-condensed'>
        <table className='table'>
          <tbody>
            { tableRows }
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default SimpleTable;
