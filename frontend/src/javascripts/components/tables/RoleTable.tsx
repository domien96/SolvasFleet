import React from 'react';
import T from 'i18n-react';
import Confirm from 'react-confirm-bootstrap';

interface Props {
      head: Table.Head.Data[];
      roles: RoleData[];
      onEdit: (e: any) => void;
      onDelete: (e: any) => void;
}

const InfoTable: React.StatelessComponent<Props> = props => {
  const { head, roles, onEdit, onDelete } = props;

  const tableHead = head.map((headData: Table.Head.Data) => (
    <th key={ headData.key } scope='row' className='table-row' >{ T.translate(headData.label) }</th>
  ));

  const tableRows = roles.map((role: RoleData, i: number) => {
    return(
      <tr key={ i } className='table-row'>
        <td>{ role.name }</td>
        <td>
          <div className='actions row'>
            <button onClick={ () => onEdit(role.id) } className='btn btn-default btn-xs'>
              <span className='glyphicon glyphicon-edit' /> Edit
            </button>
            <Confirm
              onConfirm={() => onDelete(role.id)}
              body="Are you sure you want to archive this?"
              confirmText="Confirm Archive"
              title="Archive">
              <button className='btn btn-danger btn-xs'>
                <span className='glyphicon glyphicon-remove' /> Archive
              </button>
            </Confirm>
          </div>
        </td>
      </tr>
    );
  });

  return (
    <div className='table-wrap'>
      <table className='table table-hover'>
        <thead className='thead-default'>
          <tr>
            {tableHead}
            <th className='table-row'>{ T.translate('app.actions') }</th>
          </tr>
        </thead>
        <tbody>
          {tableRows}
        </tbody>
      </table>
    </div>
  );
};

export default InfoTable;
