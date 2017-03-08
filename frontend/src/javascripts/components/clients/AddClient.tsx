import React from 'react';

import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';

class AddClient extends React.Component<{}, {}> {
  render() {
    return (
      <WrappedCol>
        <Card className='text-center' >
          <div className='card-content'>
            <h2>Add New Client</h2>
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default AddClient;
