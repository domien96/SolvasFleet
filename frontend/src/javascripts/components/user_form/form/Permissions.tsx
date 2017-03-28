import React from 'react';

import Card from '../../app/Card.tsx';
import Checkbox from './Checkbox.tsx';

const Permissions : React.StatelessComponent<{}> = () => {
  return (
    <div className='col-xs-12'>
      <Card>
        <div className='card-title'>
          <h5>
            Permissions
            <small> (Not implemented yet)</small>
          </h5>
        </div>
        <div className='card-content'>
          <Checkbox label='Premies aanmaken' />
          <Checkbox label='Premies wijzigen' />
          <Checkbox label='Premies verwijderen' />
        </div>
      </Card>
    </div>
  );
}

export default Permissions;
