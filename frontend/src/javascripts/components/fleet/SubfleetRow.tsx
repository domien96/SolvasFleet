import React from 'react';

import Checkbox from '../app/CheckBox.tsx';
import VehicleRow from './VehicleRow.tsx';

interface VProps {
  vehicles : Vehicle[];
}

const Vehicles : React.StatelessComponent<VProps> = props => {
  const vehicles = props.vehicles.map((v : Vehicle, i : number) => {
    return (<VehicleRow key={ i } vehicle={ v } />);
  })

  return (
    <div className='vehicles table'>
      { vehicles }
    </div>
  );
}

interface Props {
  type : string;
  isChecked : (key : string) => boolean;
  isIndeterminate : (key : string) => boolean;
  handleChange : (key : string) => void;
  onClick : (key : string) => void;
  vehicles : Vehicle[]
  showVehicles : boolean;
}

const Subfleet : React.StatelessComponent<Props> = props => {
  var { type: k } = props;

  var vehicles = null;
  if(props.showVehicles) {
    vehicles = (<Vehicles vehicles={ props.vehicles } />);
  }

  return (
    <div className='subfleet-wrapper'>
      <div className='table'>
        <div className='subfleet-row tr'>
          <Checkbox
            className='checkbox td'
            checked={ props.isChecked(k) }
            indeterminate={ props.isIndeterminate(k) }
            onChange={ () => props.handleChange(k) }
            />
            <h3 className='td' onClick={ () => props.onClick(k) }>
              { k } ({ props.vehicles.length })
            </h3>
          </div>
        </div>
        { vehicles }
      </div>
  );
}

export default Subfleet;
