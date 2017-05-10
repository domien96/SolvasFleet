import React from 'react';
import T     from 'i18n-react';

import { humanize } from '../../utils/utils.ts';

interface Props {
  errors : Form.Error[];
}

const Errors : React.StatelessComponent<Props> = props => {
  if (props.errors.length == 0) {
    return null;
  }

  const errors = props.errors.map((e, i) => {
    return (
      <li key={ i }>
        { humanize(e.field) } { T.translate('errors.' + e.message) }
      </li>
    );
  });

  return (
    <div className='col-xs-12'>
      <div className='panel panel-danger'>
        <div className='panel-heading'>
          Errors
        </div>
        <div className='panel-body'>
          <ul>
            { errors }
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Errors;
