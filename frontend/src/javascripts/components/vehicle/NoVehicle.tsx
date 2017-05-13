import React from 'react';
import Card from '../app/Card.tsx';
import T from 'i18n-react';

class NoVehicle extends React.Component<{}, {}> {
  render() {
    return (
    <Card>
      <div className='card-content'>
        <h2>{ T.translate('vehicle.none.title') }</h2>
        <p>{ T.translate('table.none.information', { subject: 'vehicle' }) }</p>
      </div>
    </Card>
    );
  }
}

export default NoVehicle;
