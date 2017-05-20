import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';
import { fetchCommissionOfClient, putCommission } from '../../actions/commission_actions.ts';
import { callback } from '../../actions/fetch_json.ts';

interface Props {
  [params: string]: { companyId: number };
}

class ClientCommissions extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
    this.fetchCommission = this.fetchCommission.bind(this);
    this.putCommission = this.putCommission.bind(this);
  }

  putCommission(vehicleType: string, insuranceType: string, commission: CommissionData, success?: callback, fail?: callback) {
    putCommission(vehicleType, insuranceType, commission, success, fail);
  }

  fetchCommission(vehicleType: string, insuranceType: string, success?: callback, fail?: callback) {
    fetchCommissionOfClient(vehicleType, insuranceType, this.props.params.companyId, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('commissions.client') }</h2>
        </Header>
        <CommissionGroupForm fetchCommission={ this.fetchCommission } putCommission={ this.putCommission } returnTo={ "/" } companyId={ Number(this.props.params.companyId) } fleetId={ 0 }/>
      </div>
    );
  }
}

export default ClientCommissions;
