import React from 'react';
import Header from '../app/Header.tsx';
import VehicleCommissionForm from '../commission/VehicleCommissionForm.tsx';
import T from 'i18n-react';
import { fetchCommissionOfVehicle, putCommission } from '../../actions/commission_actions.ts';
import { fetchVehicle } from '../../actions/vehicle_actions.ts';
import { callback } from '../../actions/fetch_json.ts';

interface Props {
  [params: string]: { fleetId: number, companyId: number, vehicleId: number, vehicleType: string };
}

interface State {
  vehicle: VehicleData;
}

class GlobalCommissions extends React.Component<Props, State> {

  constructor(props: Props) {
    super(props);
    this.state = {
      vehicle: { vin: "" }
    };
    this.fetchCommission = this.fetchCommission.bind(this);
    this.putCommission = this.putCommission.bind(this);
    this.fetchVehicle(props.params.vehicleId);
  }

  fetchVehicle(vehicleId: number) {
    fetchVehicle(vehicleId, (data: VehicleData) => {
      this.setState({ vehicle: data });
    })
  }


  putCommission(commission: CommissionData, success?: callback, fail?: callback) {
    putCommission(commission, success, fail);
  }

  fetchCommission(vehicleType: string, insuranceType: string, success?: callback, fail?: callback) {
    fetchCommissionOfVehicle(vehicleType, insuranceType, this.props.params.vehicleId, this.props.params.companyId, this.props.params.fleetId, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ `${T.translate('commission.vehicle')}: ${this.state.vehicle.vin}` }</h2>
        </Header>
        <VehicleCommissionForm fetchCommission={ this.fetchCommission }
          putCommission={ this.putCommission }
          returnTo={ "/" }
          fleetId={ Number(this.props.params.fleetId) }
          companyId={ Number(this.props.params.companyId) }
          vehicleId={ Number(this.props.params.vehicleId) }
          vehicleType={ this.props.params.vehicleType }/>
      </div>
    );
  }
}

export default GlobalCommissions;
