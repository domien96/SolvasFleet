import React from 'react';

import Header from '../app/Header.tsx';
import Actions from '../forms/Actions.tsx';
import T from 'i18n-react';
import CommissionGroup from './CommissionGroup.tsx';
import { callback } from '../../actions/fetch_json.ts';
import { redirect_to } from '../../routes/router.tsx';


interface Props {
  companyId: number;
  fleetId: number;
  returnTo?: string;
  fetchCommission: (vehicleType: string, insuranceType: string, success?: callback, fail?: callback) => void;
  putCommission: (vehicleType: string, insuranceType: string, commission: CommissionData, success?: callback, fail?: callback) => void;
}

interface Commissions {
  personalVehicle: CommissionGroupData;
  van: CommissionGroupData;
  truck: CommissionGroupData;
  truck12: CommissionGroupData;
  semiHeavyTruck: CommissionGroupData;
  [key: string]: CommissionGroupData;
}

interface CommissionToggles {
  showPersonalVehicle: boolean;
  showVan: boolean;
  showTruck: boolean;
  showSemiHeavyTruck: boolean;
  showTruck12: boolean;
  [key: string]: boolean;
}

interface State {
  toggles: CommissionToggles;
  commissions: Commissions;
  initialCommissions: Commissions;
}

class CommissionGroupForm extends React.Component<Props, State> {

  constructor(props: Props) {
    super(props);
    const emptycommission = { id: 0, fleet: 0, vehicle: 0, company: 0, insuranceType: "", vehicleType: "", value: 0 };
    const emptyCommissionGroup = { civilLiability: emptycommission, omnium: emptycommission, legalAid: emptycommission, driverInsurance: emptycommission, travelInsurance: emptycommission };
    const commissions = {
      personalVehicle: { ...emptyCommissionGroup },
      van: emptyCommissionGroup,
      truck: emptyCommissionGroup,
      truck12: emptyCommissionGroup,
      semiHeavyTruck: emptyCommissionGroup
    };

    this.state = {
      toggles: {
        showVan: false,
        showTruck: false,
        showSemiHeavyTruck: false,
        showTruck12: false,
        showPersonalVehicle: false
      },
      commissions: commissions,
      initialCommissions: JSON.parse(JSON.stringify(commissions));
    }

    this.handleChange = this.handleChange.bind(this);
    this.toggleCommission = this.toggleCommission.bind(this);
    this.putCommissions = this.putCommissions.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.fetchCommissions();
  }

  handleChange(vehicleType: string) {
    return (s: string) => {
      return (e: any) => {
        const com = this.state.commissions[vehicleType];
        com[s].value = Number(e.target.value);
        const newstate = this.state;
        newstate.commissions[vehicleType] = com;
        this.setState({ commissions: newstate.commissions  });
      }
    }
  }

  toggleCommission(vehicleType: string) {
    return () => {
      const obj: State = { ...this.state };
      obj.toggles[vehicleType] = !this.state.toggles[vehicleType];
      this.setState({ ...obj });
    }
  }

  setCommission(vehicleType: string, insuranceType: string, commission: CommissionData) {
    let state: State = { ...this.state };
    commission.vehicleType = vehicleType.charAt(0).toUpperCase() + vehicleType.slice(1); //Vuil idd
    commission.fleet = this.props.fleetId;
    commission.company = this.props.companyId;
    commission.id = 0;
    state.initialCommissions[vehicleType][insuranceType] = commission;
    state.commissions[vehicleType][insuranceType] = { ...commission };
    this.setState({ ...state });
  }

  fetchCommission(vehicleType: string, insuranceType: string) {
    this.props.fetchCommission(vehicleType, insuranceType, (data) => {
      this.setCommission(vehicleType, insuranceType, data.data[0]);
    });
  }

  putCommissionIfChanged(vehicleType: string, insuranceType: string, success?: callback) {
    console.log(this.state.commissions[vehicleType][insuranceType]);
    console.log(this.state.initialCommissions[vehicleType][insuranceType]);
    if(this.state.initialCommissions[vehicleType][insuranceType].value !== this.state.commissions[vehicleType][insuranceType].value) {
      const success = (data: any) => redirect_to(this.props.returnTo);
      console.log("this.state.commission[vehicleType][insuranceType]");

      this.props.putCommission(vehicleType, insuranceType, this.state.commissions[vehicleType][insuranceType], success);
    }
  }

  putCommissions() {
    this.putCommissionsOfAllInsuranceTypes('personalVehicle');
    this.putCommissionsOfAllInsuranceTypes('truck');
    this.putCommissionsOfAllInsuranceTypes('truck12');
    this.putCommissionsOfAllInsuranceTypes('semiHeavyTruck');
    const success = (data: any) => redirect_to(this.props.returnTo);
    this.putCommissionsOfAllInsuranceTypes('van', success)
  }

  putCommissionsOfAllInsuranceTypes(vehicleType: string, success?: callback) {
    this.putCommissionIfChanged(vehicleType, 'civilLiability');
    this.putCommissionIfChanged(vehicleType, 'omnium');
    this.putCommissionIfChanged(vehicleType, 'driverInsurance');
    this.putCommissionIfChanged(vehicleType, 'travelInsurance');
    this.putCommissionIfChanged(vehicleType, 'legalAid', success);
  }

  fetchCommissions() {
    this.fetchCommissionsOfAllInsuranceTypes('personalVehicle');
    this.fetchCommissionsOfAllInsuranceTypes('truck');
    this.fetchCommissionsOfAllInsuranceTypes('truck12');
    this.fetchCommissionsOfAllInsuranceTypes('semiHeavyTruck');
    this.fetchCommissionsOfAllInsuranceTypes('van');
  }

  fetchCommissionsOfAllInsuranceTypes(vehicleType: string) {
    this.fetchCommission(vehicleType, 'civilLiability');
    this.fetchCommission(vehicleType, 'omnium');
    this.fetchCommission(vehicleType, 'driverInsurance');
    this.fetchCommission(vehicleType, 'travelInsurance');
    this.fetchCommission(vehicleType, 'legalAid');
  }

  onSubmit(e: any): void {
    e.preventDefault();
    this.putCommissions();
  }

  render() {
    return (
      <form method='put' onSubmit={ this.onSubmit } >
      dadaz
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-6'>
              <CommissionGroup toggleForm={ this.toggleCommission('showPersonalVehicle') } showForm={ this.state.toggles.showPersonalVehicle } commission={ this.state.commissions.personalVehicle } handleChange={ this.handleChange('personalVehicle') } vehicleType='PersonalVehicle'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showTruck') } showForm={ this.state.toggles.showTruck } commission={ this.state.commissions.truck } handleChange={ this.handleChange('truck') } vehicleType='Truck'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showTruck12') } showForm={ this.state.toggles.showTruck12 } commission={ this.state.commissions.truck12 } handleChange={ this.handleChange('truck12') } vehicleType='Truck+12'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showSemiHeavyTruck') } showForm={ this.state.toggles.showSemiHeavyTruck } commission={ this.state.commissions.semiHeavyTruck } handleChange={ this.handleChange('semiHeavyTruck') } vehicleType='SemiHeavyTruck'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showVan') } showForm={ this.state.toggles.showVan } commission={ this.state.commissions.van } handleChange={ this.handleChange('van') } vehicleType='Van'/>
            </div>
            <div className='col-xs-12 col-md-5'>
              <div className='row'>
                <Actions submitLabel={ 'Submit' } cancelUrl={ this.props.returnTo } model='commissions' />
              </div>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default CommissionGroupForm;
