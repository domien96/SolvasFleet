import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';
import { fetchCommissionOfFleet, putCommission } from '../../actions/commission_actions.ts';
import { fetchClient } from '../../actions/client_actions.ts';
import { fetchFleet } from '../../actions/'
import { callback } from '../../actions/fetch_json.ts';

interface State {
}

interface Props {
  [params: string]: { fleetId: number, companyId: number };
}

class FleetCommissions extends React.Component<Props, {}> {

  constructor(props: {}) {
    super(props);
    this.fetchCommission = this.fetchCommission.bind(this);
    this.putCommission = this.putCommission.bind(this);
  }

  putCommission(commission: CommissionData, success?: callback, fail?: callback) {
    putCommission(commission, success, fail);
  }

  fetchCommission(vehicleType: string, insuranceType: string, success?: callback, fail?: callback) {
    fetchCommissionOfFleet(vehicleType, insuranceType, this.props.params.fleetId, this.props.params.companyId, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('commission.fleet') }</h2>
        </Header>
        <CommissionGroupForm
          fetchCommission={ this.fetchCommission }
          putCommission={ this.putCommission }
          returnTo={ `/clients/${this.props.params.companyId}/fleets/${this.props.params.fleetId}` }
          companyId={ Number(this.props.params.companyId) }
          fleetId={ Number(this.props.params.fleetId) }/>
      </div>
    );
  }
}

export default FleetCommissions;
