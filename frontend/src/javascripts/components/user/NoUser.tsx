import React from 'react';
import Card from '../app/Card.tsx';
import T from 'i18n-react';

const NoUser : React.StatelessComponent<{}> = () => {
  return (
  	<Card>
	    <div className='card-content'>
	      <h2>{ T.translate('user.noUser') }</h2>
	    </div>
    </Card>
  );
}

export default NoUser;
