import React from 'react';
import T from 'i18n-react';
import Card from './app/Card.tsx';

const Home: React.StatelessComponent<{}> = () => {
  return (
    <div className='wrapper'>
      <div className='row'>
        <Card className='text-center' >
          <div className='card-content'>
            <h2>{ T.translate('app.welcomeMessage') }</h2>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default Home;
