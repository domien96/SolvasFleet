import React from 'react';
import Card from '../app/Card.tsx';
import T from 'i18n-react';

class NoVehicle extends React.Component<{}, {}> {
  render() {
    return (
    <Card>
      <div className='card-content'>
        <h2>{ T.translate('vehicle.noVehicle') }</h2>
      </div>
    </Card>
    );
  }
}

export default NoVehicle;
