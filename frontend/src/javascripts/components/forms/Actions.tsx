import React from 'react';
import T from 'i18n-react';
import { Link } from 'react-router';

import Card from '../app/Card.tsx';

interface SProps {
  label : string;
  model : string;
}

const Submit : React.StatelessComponent<SProps> = ({ label, model }) => {
  return (
    <button type='submit' className='btn btn-success'>
      <T.text tag='span' text={ label || 'form.submit' } /> { model }
    </button>
  )
}

interface Props {
  submitLabel? : string;
  cancelUrl : string;
  model : string;
}

const Actions : React.StatelessComponent<Props> = ({ submitLabel, cancelUrl, model }) => {
  return (
    <div className='col-xs-12'>
      <Card>
        <div className='card-title'>
          <h5>Actions</h5>
        </div>
        <div className='card-content actions'>
          <Submit label={ submitLabel } model={ model } />
          <Link to={ cancelUrl } className='btn btn-default'>Cancel</Link>
        </div>
      </Card>
    </div>
  );
}

export default Actions;
