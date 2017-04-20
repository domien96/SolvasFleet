import React from 'react';

interface Props {
    list : string[];
}

const SimpleList : React.StatelessComponent<Props> = props => {

    const tableRows = props.list.map((c : string, i : number) =>
      (
        <tr key={ i }>
          <td>{ c }</td>
        </tr>
      ));

    return (
      <div>
        <div className='table-wrap table-responsive table-condensed'>
          <table className='table'>
            <tbody>
              { tableRows }
            </tbody>
          </table>
        </div>
      </div>
    );
}

export default SimpleList;
