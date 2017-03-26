import React from 'react';
import T     from 'i18n-react';

class DetailTable extends React.Component<Table.Detail.Props, {}> {

  render() {
    const tableRows = this.props.data.map((c : Table.Head.Data, i : number) =>
      (
        <tr key={ i } className='table-row-left'>
          <td className='first-column'>{ T.translate(c.key) }</td>
          <td className='second-column'>{ c.label }</td>
        </tr>
      ));

    return (
      <div>
        <div>
          <h3>Information</h3>
        </div>
        <div className='table-wrap table-responsive'>
          <table className='table'>
            <tbody>
              { tableRows }
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default DetailTable;
