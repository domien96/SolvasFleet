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

class CommissionGroupForm extends React.Component<Props, {}> {

  constructor(props: Props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(vehicleType: string) {
    return (s: string) => {
      return (e: any) => {
        const com = this.state[vehicleType];
        com[s].value = e.target.value;
        let newstate = this.state;
        newstate[vehicleType] = com;
        this.setState({ ..newstate  });
      }
    }
  }

  render() {
  return (
    <form method='post' onSubmit={ null } >
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-6'>
            <CommissionGroup commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='PersonalVehicle'/>
            <CommissionGroup commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='Truck'/>
            <CommissionGroup commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='Truck+12'/>
            <CommissionGroup commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='SemiHeavyTruck'/>
            <CommissionGroup commission={ this.props.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='Van'/>
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
