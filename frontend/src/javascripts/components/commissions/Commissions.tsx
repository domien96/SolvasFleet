import React from 'react';
import Header from '../app/Header.tsx';
import CommissionGroupForm from '../commission/CommissionGroupForm.tsx';
import T from 'i18n-react';

interface State {
  commissionGroup: CommissionGroupData;
}

class Clients extends React.Component<{}, State> {

  constructor(props: {}) {
    super(props);
    this.state = { commissionGroup: { burgerlijkeAansprakelijkheid: { id: 1, value: 13.4, vehicleType: 'PersonalVehicle', insuranceType: 'Omnium'  }, omnium: {}, rechtsbijstand: {}, reisbijstand: {}, veiligheid: {} } };
    this.handleChange = this.handleChange.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  handleChange(s: string, e: any) {
    const newCommission: CommissionGroupData = this.state.commissionGroup;
    newCommission[s].value = e.target.value;
    this.setState({ commissionGroup: newCommission });
  }

  onChange(s: string) {
    return (e: any) => {
      this.handleChange(s,e);
    }
  }

  render() {
    return (
      <CommissionGroupForm personalVehicle={this.state.commissionGroup}/>
    );
  }
}

export default Clients;
