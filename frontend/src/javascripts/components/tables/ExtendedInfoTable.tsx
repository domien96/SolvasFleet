import React from 'react';
import T     from 'i18n-react';
import Confirm from 'react-confirm-bootstrap';

interface Props {
      head    : Table.Head.Data[];
      data    : any;
      onDelete : (e : any) => void;
}

const ExtendedInfoTable : React.StatelessComponent<Props> = props => {

    var { head, data, onDelete } = props;
 
    const tableHead = head.map((headData : Table.Head.Data) =>
    (
      <th key={ headData.key } scope='row' className='table-row' >{ T.translate(headData.label) }</th>
    ));

    const tableRows = data.map((item : any, i : number) => {
      const cells = head.map((headData : Table.Head.Data, j : number) => {
        return (
          <td key={ j }>{ item[headData.key] }</td>
        );
      });
      return (
        <tr key={ i } className='table-row'>
          {cells}
          <td>
            <Confirm
              onConfirm={ onDelete }
              body="Are you sure you want to archive this?"
              confirmText="Confirm Archive"
              title="Archive">
              <button className='btn btn-danger btn-xs'>
                <span className='glyphicon glyphicon-remove' /> Archive
              </button>
            </Confirm>
          </td>
        </tr>
      );
    });

    return (
      <div className='table-wrap'>
      <table className='table table-striped'>
        <thead className='thead-default'>
          <tr>
            {tableHead}
            <th className='table-row'>Actions</th>
          </tr>
        </thead>
        <tbody>
          {tableRows}
        </tbody>
      </table>
      </div>
    );
}

export default ExtendedInfoTable;
