import React from 'react';
import T     from 'i18n-react';

import { humanize } from '../../utils/utils.ts';

class Errors extends React.Component<Form.Errors.Props, {}> {
  render() {
    if (this.props.errors.length == 0) {
      return null;
    }

    const errors = this.props.errors.map((e, i) => {
      return (
        <li key={ i }>
          { humanize(e.field) } { T.translate('errors.' + e.error) }
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
}

export default Errors;
