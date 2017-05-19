import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';

interface State {
  commissionGroup: CommissionGroupData;
}

class FleetCommissions extends React.Component<{}, State> {

  constructor(props: {}) {
    super(props);
    this.state = { commissionGroup: { burgerlijkeAansprakelijkheid: { id: 1, value: 13.4, vehicleType: 'PersonalVehicle', insuranceType: 'Omnium'  }, omnium: {}, rechtsbijstand: {}, reisbijstand: {}, veiligheid: {} } };
  }

  getGlobalCommission() {
    //TODO
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('commissions.fleet') }</h2>
        </Header>
        <CommissionGroupForm personalVehicle={ this.state.commissionGroup }/>
      </div>
    );
  }
}

export default FleetCommissions;
