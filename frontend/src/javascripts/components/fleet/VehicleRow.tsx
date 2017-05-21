import React from 'react';
import { Link } from 'react-router';
import { fetchContracts } from '../../actions/contract_actions.ts';

interface LProps {
  id: number | string;
  span: string;
  value: string | number;
}

const VehicleLink: React.StatelessComponent<LProps> = ({ id, span, value }) => {
  return (
    <Link to={ `/vehicles/${id}` } className='td'>
      <span>{ span }:</span>
      <span>{ value }</span>
    </Link>
  );
};

interface Props {
  vehicle: VehicleData;
}

class VehicleRow extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
  }

  static contextTypes = {
    childHandleChange: React.PropTypes.func,
    childIsChecked: React.PropTypes.func,
  };

  render() {
    const { id, vin, brand, model, mileage } = this.props.vehicle;

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
        <VehicleLink id={ id } span='Chassis Nummer' value={ vin } />
        <VehicleLink id={ id } span='Model' value={ `${brand} ${model}` } />
        <VehicleLink id={ id } span='Mileage' value={ mileage } />
      </div>
    );
  }
}

export default VehicleRow;
