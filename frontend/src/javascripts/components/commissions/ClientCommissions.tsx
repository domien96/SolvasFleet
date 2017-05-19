import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';

interface Props {
  [params: string]: { companyId: number };
}

interface State {
  commissionGroup: CommissionGroupData;
}

class ClientCommissions extends React.Component<Props, State> {

  constructor(props: Props) {
    super(props);
    this.state = { commissionGroup: { burgerlijkeAansprakelijkheid: { id: 1, value: 13.4, vehicleType: 'PersonalVehicle', insuranceType: 'Omnium'  }, omnium: {}, rechtsbijstand: {}, reisbijstand: {}, veiligheid: {} } };
  }

  getClientCommissions() {
    //TODO
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('commissions.client') }</h2>
        </Header>
        <CommissionGroupForm personalVehicle={ this.state.commissionGroup }/>
      </div>
    );
  }
}

export default ClientCommissions;
