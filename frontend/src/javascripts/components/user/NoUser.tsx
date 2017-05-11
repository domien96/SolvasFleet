import React from 'react';
import Card from '../app/Card.tsx';
import T from 'i18n-react';

const NoUser: React.StatelessComponent<{}> = () => {
  return (
    <Card>
      <div className='card-content'>
        <h2>{ T.translate('user.none.title') }</h2>
        <p>{ T.translate('table.none.information', { subject: 'user' }) }</p>
        <p>{ T.translate('table.users.extra_information') }</p>
      </div>
    </Card>
  );
};

export default NoUser;
