import React from 'react';
import T from 'i18n-react';

interface Props {
  data: Table.Head.Data[];
  changed: number[];
}

const classNames = (i: number, changedList: number[]) => {
  if (i in changedList) {
    return 'table-row-left changed'
  }
  else {
    return 'table-row-left'
  }
}

const LogEntryTable: React.StatelessComponent<Props> = props => {
  const tableRows = props.data.map((c: Table.Head.Data, i: number) => (
    <tr key={ i } className={ classNames(i, props.changed) }>
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

export default LogEntryTable;
