import React from 'react';
import Card      from '../app/Card.tsx';

class NoVehicle extends React.Component<{}, {}> {
  render() {
    return (
    <Card>
      <div className='card-content'>
        <h2>No vehicle selected</h2>
      </div>
    </Card>
    );
  }
}

export default NoVehicle;
