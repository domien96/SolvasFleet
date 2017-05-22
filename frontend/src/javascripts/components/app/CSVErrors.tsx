import React from 'react';
import T from 'i18n-react';

import { humanize } from '../../utils/utils.ts';

interface Props {
  errors: any;
}

interface EProps {
  field: string;
  error: string;
}

const Error: React.StatelessComponent<EProps> = props => {
  return (
    <span>{ humanize(props.field) } { <T.text text={ `errors.${props.error}` } /> }</span>
  );
}

const Errors: React.StatelessComponent<Props> = props => {
  if (props.errors.length === 0) {
    return null;
  }

  const errors = props.errors.map((e: any, i: number) => {
    const er = e.errors.map((ee: any, ii: number) => {
      return (
        <div key={ ii } >
          <p>
            <Error field={ ee.field } error={ ee.error } />
          </p>
        </div>  
      );
    });

    return (
      <li key={ i }>
        Row { e.row }: { er }
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
};

export default Errors;
