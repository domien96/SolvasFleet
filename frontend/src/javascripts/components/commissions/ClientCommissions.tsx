import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';
import { fetchCommissionOfClient, putCommission } from '../../actions/commission_actions.ts';
import { fetchClient } from '../../actions/client_actions.ts';
import { callback } from '../../actions/fetch_json.ts';

interface Props {
  [params: string]: { companyId: number };
}

interface State {
  client: CompanyData;
}

class ClientCommissions extends React.Component<Props, State> {

  constructor(props: Props) {
    super(props);
    this.state = {
      client: { address: {} },
    };
    this.fetchCommission = this.fetchCommission.bind(this);
    this.putCommission = this.putCommission.bind(this);
    this.fetchClient = this.fetchClient.bind(this);
    this.fetchClient();
  }

  putCommission(commission: CommissionData, success?: callback, fail?: callback) {
    putCommission(commission, success, fail);
  }

  fetchCommission(vehicleType: string, insuranceType: string, success?: callback, fail?: callback) {
    fetchCommissionOfClient(vehicleType, insuranceType, this.props.params.companyId, success, fail);
  }

  fetchClient() {
    fetchClient(this.props.params.companyId, (data: CompanyData) => {
      this.setState({ client: data });
    })
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ `${T.translate('commission.client')}: ${this.state.client.name}` }</h2>
        </Header>
        <CommissionGroupForm
          fetchCommission={ this.fetchCommission }
          putCommission={ this.putCommission }
          returnTo={ `/clients/${this.props.params.companyId}` }
          companyId={ Number(this.props.params.companyId) }
          fleetId={ 0 }
          />
      </div>
    );
  }
}

export default ClientCommissions;
