import React from 'react';

import Header from '../app/Header.tsx';
import Actions from '../forms/Actions.tsx';
import T from 'i18n-react';
import CommissionGroup from './CommissionGroup.tsx';
import { callback } from '../../actions/fetch_json.ts';
import { redirect_to } from '../../routes/router.tsx';
import _ from 'lodash';

interface Props {
  companyId: number;
  fleetId: number;
  returnTo?: string;
  fetchCommission: (vehicleType: string, insuranceType: string, success?: callback, fail?: callback) => void;
  putCommission: (commission: CommissionData, success?: callback, fail?: callback) => void;
}

interface Commissions {
  PersonalVehicle: CommissionGroupData;
  Van: CommissionGroupData;
  Truck: CommissionGroupData;
  Truck12: CommissionGroupData;
  SemiHeavyTruck: CommissionGroupData;
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
    const emptyCommissionGroup = {
      CivilLiability: emptycommission,
      Omnium: emptycommission,
      LegalAid: emptycommission,
      DriverInsurance: emptycommission,
      TravelInsurance: emptycommission
    };
    const commissions = {
      PersonalVehicle: { ...emptyCommissionGroup },
      Van: { ...emptyCommissionGroup },
      Truck: {...emptyCommissionGroup},
      Truck12: {...emptyCommissionGroup},
      SemiHeavyTruck: {...emptyCommissionGroup}
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
      initialCommissions: JSON.parse(JSON.stringify(commissions)),
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
    commission.vehicleType = vehicleType; //Vuil idd
    commission.fleet = this.props.fleetId;
    commission.company = this.props.companyId;
    state.initialCommissions[vehicleType][insuranceType] = commission;
    state.commissions[vehicleType][insuranceType] = { ...commission };
    this.setState({ ...state });
  }

  fetchCommission(vehicleType: string, insuranceType: string) {
    this.props.fetchCommission(insuranceType, vehicleType, (data) => { //Dirty
      this.setCommission(vehicleType, insuranceType, data.data[0]);
    });
  }

  getChanges(): CommissionData[] {
    const insuranceTypes = ['LivilLiability','Omnium', 'DriverInsurance', 'TravelInsurance', 'LegalAid'];
    const vehicleTypes = ['PersonalVehicle', 'Truck', 'Truck12', 'SemiHeavyTruck', 'Van'];
    const initialCommissionGroups = vehicleTypes.map((d) => this.state.initialCommissions[d]);
    const updatedCommissionGroups = vehicleTypes.map((d) => this.state.commissions[d]);
    const initialCommissions = _.flatten(initialCommissionGroups.map( group => insuranceTypes.map((i) => group[i])));
    const updatedCommissions = _.flatten(updatedCommissionGroups.map( group => insuranceTypes.map((i) => group[i])));
    const diff = _.differenceWith(updatedCommissions, initialCommissions, (a,b)=>{ return a.value === b.value && a.insuranceType === b.insuranceType && a.vehicleType === b.vehicleType });
    return diff;
  }

  putCommissions() {
    const diff = this.getChanges();
    if(diff.length !== 0) {
      let success = (data: any) => {
        for(let i=0;i<(diff.length-1);i++) {
          this.props.putCommission(diff[i]);
        }
        redirect_to(this.props.returnTo);
      }
      this.props.putCommission(diff[diff.length-1], success);
    }
  }

  fetchCommissions() {
    this.fetchCommissionsOfAllInsuranceTypes('PersonalVehicle');
    this.fetchCommissionsOfAllInsuranceTypes('Truck');
    this.fetchCommissionsOfAllInsuranceTypes('Truck12');
    this.fetchCommissionsOfAllInsuranceTypes('SemiHeavyTruck');
    this.fetchCommissionsOfAllInsuranceTypes('Van');
  }

  fetchCommissionsOfAllInsuranceTypes(vehicleType: string) {
    this.fetchCommission(vehicleType, 'CivilLiability');
    this.fetchCommission(vehicleType, 'Omnium');
    this.fetchCommission(vehicleType, 'DriverInsurance');
    this.fetchCommission(vehicleType, 'TravelInsurance');
    this.fetchCommission(vehicleType, 'LegalAid');
  }

  onSubmit(e: any): void {
    e.preventDefault();
    this.putCommissions();
  }

  render() {
    return (
      <form method='put' onSubmit={ this.onSubmit } >
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-6'>
              <CommissionGroup toggleForm={ this.toggleCommission('showPersonalVehicle') } showForm={ this.state.toggles.showPersonalVehicle } commission={ this.state.commissions.personalVehicle } handleChange={ this.handleChange('PersonalVehicle') } vehicleType='PersonalVehicle'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showTruck') } showForm={ this.state.toggles.showTruck } commission={ this.state.commissions.truck } handleChange={ this.handleChange('Truck') } vehicleType='Truck'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showTruck12') } showForm={ this.state.toggles.showTruck12 } commission={ this.state.commissions.truck12 } handleChange={ this.handleChange('Truck12') } vehicleType='Truck+12'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showSemiHeavyTruck') } showForm={ this.state.toggles.showSemiHeavyTruck } commission={ this.state.commissions.semiHeavyTruck } handleChange={ this.handleChange('SemiHeavyTruck') } vehicleType='SemiHeavyTruck'/>
              <CommissionGroup toggleForm={ this.toggleCommission('showVan') } showForm={ this.state.toggles.showVan } commission={ this.state.commissions.van } handleChange={ this.handleChange('Van') } vehicleType='Van'/>
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
