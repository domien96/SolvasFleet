import React from 'react';

import Card from './app/Card.tsx';

class Home extends React.Component<{}, {}> {
  render() {
    return (
      <div className='wrapper'>
        <div className='row'>
          <div className='col-lg-12'>
            <Card className='text-center padding-md' >
              <h2>Welcome to Solvas Fleet</h2>
            </Card>
          </div>
        </div>
      </div>
    );
  }
}

export default Home;
