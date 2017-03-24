import React from 'react';

import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';

interface Props {
  vehicles: Vehicle[];
  onVehicleSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<Props> = props => {
  const tableHead = [
    th('fleet', 'vehicle.fleet'),
    th('vin', 'vehicle.vin') ,
    th('licensePlate', 'vehicle.licensePlate') ,
    th('type', 'vehicle.type')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.vehicles } onClick={ props.onVehicleSelect } />
  );
}

export default Overview;
