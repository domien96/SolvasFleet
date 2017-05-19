import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';

interface State {
}

class FleetCommissions extends React.Component<{}, {}> {

  constructor(props: {}) {
    super(props);
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
      </div>
    );
  }
}

export default FleetCommissions;
