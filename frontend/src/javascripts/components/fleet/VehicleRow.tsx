import React from 'react';
import { Link } from 'react-router';

interface LProps {
  id : number | string;
  key : string;
  value : string | number;
}

const VehicleLink : React.StatelessComponent<LProps> = ({ id, key, value }) => {
  return (
    <Link to={ `vehicles/${id}` } className='td'>
      <span>{ key }:</span>
      <span>{ value }</span>
    </Link>
  );
}

interface Props {
  vehicle : Vehicle;
}

class VehicleRow extends React.Component<Props, {}> {
  static contextTypes = {
    childIsChecked:    React.PropTypes.func,
    childHandleChange: React.PropTypes.func
  }

  render () {
    var { id, vin, brand, model, mileage } = this.props.vehicle;

    return (
      <div className='tr'>
        <div className='td'>
          <input
            type='checkbox'
            className='checkbox'
            checked={ this.context.childIsChecked(id) }
            onChange={ () => this.context.childHandleChange(id) }
            />
        </div>
        <VehicleLink id={ id } key='Chassis Nummer' value={ vin } />
        <VehicleLink id={ id } key='Model' value={ `${brand} ${model}` } />
        <VehicleLink id={ id } key='Mileage' value={ mileage } />
      </div>
    );
  }
}

export default VehicleRow;
