import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';
import { fetchCommissionOfClient } from '../../actions/commission_actions.ts';
import { callback } from '../../actions/fetch_json.ts';

interface Props {
  [params: string]: { companyId: number };
}

class ClientCommissions extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
    this.fetchCommission = this.fetchCommission.bind(this);
  }

  getClientCommissions() {
    //TODO
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
        <CommissionGroupForm fetchCommission={ this.fetchCommission } returnTo={ "/" }/>
      </div>
    );
  }
}

export default ClientCommissions;
