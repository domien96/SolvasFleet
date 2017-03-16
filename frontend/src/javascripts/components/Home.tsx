import React from 'react';

import Card       from './app/Card.tsx';

class Home extends React.Component<{}, {}> {
  render() {
    return (
      <div className='wrapper'>
        <div className='row'>
          <Card className='text-center' >
            <div className='card-content'>
              <h2>Welcome to Solvas Fleet</h2>
            </div>
          </Card>
        </div>
      </div>
    );
  }
}

export default Home;
