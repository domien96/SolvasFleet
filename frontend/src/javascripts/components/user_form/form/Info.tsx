import React from 'react';

import FormField from './UFormField.tsx';
import Card from '../../app/Card.tsx';

export interface Props {
  handleChange : (field : string, e : any) => void;
  hasError : (s : string) => boolean;
  user : MUser;
}

const Info : React.StatelessComponent<Props> = (props) => {
  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>General info</h5>
        </div>
        <div className='card-content'>
          <FormField field='firstName' type='text' { ...props } />
          <FormField field='lastName' type='text' { ...props } />
          <FormField field='email' type='email' { ...props } />
          <FormField field='password' type='password' { ...props } />
        </div>
      </Card>
    </div>
  );
}

export default Info;
