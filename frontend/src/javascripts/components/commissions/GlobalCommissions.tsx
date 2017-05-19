import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';

interface Props {
  [params: string]: { fleetId: number };
}

class GlobalCommissions extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
  }

  getFleetCommissions() {
    //TODO
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('commissions.global') }</h2>
        </Header>
      </div>
    );
  }
}

export default GlobalCommissions;
