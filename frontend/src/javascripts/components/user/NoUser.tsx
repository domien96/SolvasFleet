import React from 'react';
import Card from '../app/Card.tsx';


const NoUser : React.StatelessComponent<{}> = () => {
  return (
  	<Card>
	    <div className='card-content'>
	      <h2>No User</h2>
	    </div>
    </Card>
  );
}

export default NoUser;
