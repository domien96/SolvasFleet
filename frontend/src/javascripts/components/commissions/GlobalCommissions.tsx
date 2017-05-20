import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';
import { fetchGlobalCommission, putCommission } from '../../actions/commission_actions.ts';
import { callback } from '../../actions/fetch_json.ts';

interface Props {
  [params: string]: { fleetId: number };
}

class GlobalCommissions extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
    this.fetchCommission = this.fetchCommission.bind(this);
    this.putCommission = this.putCommission.bind(this);
  }


  putCommission(vehicleType: string, insuranceType: string, commission: CommissionData, success?: callback, fail?: callback) {
    putCommission(vehicleType, insuranceType, commission, success, fail);
  }

  fetchCommission(vehicleType: string, insuranceType: string, success?: callback, fail?: callback) {
    fetchGlobalCommission(vehicleType, insuranceType, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('commissions.global') }</h2>
        </Header>
        <CommissionGroupForm fetchCommission={ this.fetchCommission } putCommission={ this.putCommission } returnTo={ "/" }/>
      </div>
    );
  }
}

export default GlobalCommissions;
