import React from 'react';

import Header from '../app/Header.tsx';
import Actions from '../forms/Actions.tsx';
import T from 'i18n-react';
import CommissionGroup from './CommissionGroup.tsx';


interface Props {
  personalVehicle: CommissionGroupData;
  van?: CommissionGroupData;
  truck?: CommissionGroupData;
  semiHeavyTruck?: CommissionGroupData;
  truck12?: CommissionGroupData;
  returnTo?: string;
}

interface State {
  showPersonalVehicle: boolean;
  showVan: boolean;
  showTruck: boolean;
  showSemiHeavyTruck: boolean;
  showTruck12: boolean;
  [key: string]: boolean;
}

const emptyState: State = {
  showPersonalVehicle: false,
  showVan: false,
  showTruck: false,
  showSemiHeavyTruck: false,
  showTruck12: false
};

class CommissionGroupForm extends React.Component<Props, State> {



  constructor(props: Props) {
    super(props);
    this.state = {
      showPersonalVehicle: false,
      showVan: false,
      showTruck: false,
      showSemiHeavyTruck: false,
      showTruck12: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.toggleCommission = this.toggleCommission.bind(this);
  }

  handleChange(vehicleType: string) {
    return (s: string) => {
      return (e: any) => {
      /*  const com = this.state[vehicleType];
        com[s].value = e.target.value;
        let newstate = this.state;
        newstate[vehicleType] = com;
        this.setState({ ..newstate  });*/
      }
    }
  }

  toggleCommission(vehicleType: string) {
    return (e: any) => {
      let obj: State = { ...emptyState };
      obj[vehicleType] = !this.state[vehicleType];
      this.setState({ ...obj });
    }
  }

  render() {
  return (
    <form method='post' onSubmit={ null } >
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-6'>
            <CommissionGroup toggleForm={ this.toggleCommission('showPersonalVehicle') } showForm={ this.state.showPersonalVehicle } commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='PersonalVehicle'/>
            <CommissionGroup toggleForm={ this.toggleCommission('showTruck') } showForm={ this.state.showTruck } commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='Truck'/>
            <CommissionGroup toggleForm={ this.toggleCommission('showTruck12') } showForm={ this.state.showTruck12 } commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='Truck+12'/>
            <CommissionGroup toggleForm={ this.toggleCommission('showSemiHeavyTruck') } showForm={ this.state.showSemiHeavyTruck } commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='SemiHeavyTruck'/>
            <CommissionGroup toggleForm={ this.toggleCommission('showVan') } showForm={ this.state.showVan } commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='Van'/>
          </div>
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ 'Submit' } cancelUrl={ this.props.returnTo } model='commission' />
            </div>
          </div>
        </div>
        </div>
    </form>
  );}
};

export default CommissionGroupForm;
