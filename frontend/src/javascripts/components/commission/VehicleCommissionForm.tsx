import React from 'react';

import Actions from '../forms/Actions.tsx';
import CommissionGroup from './CommissionGroup.tsx';
import { callback } from '../../actions/fetch_json.ts';
import { redirect_to } from '../../routes/router.tsx';
import _ from 'lodash';


interface Props {
  companyId: number;
  fleetId: number;
  vehicleId: number;
  vehicleType: string;
  returnTo?: string;
  fetchCommission: (vehicleType: string, insuranceType: string, success?: callback, fail?: callback) => void;
  putCommission: (commission: CommissionData, success?: callback, fail?: callback) => void;
}

interface State {
  showForm: boolean;
  commissions: CommissionGroupData;
  initialCommissions: CommissionGroupData;
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
    this.state = {
      showForm: true,
      commissions: emptyCommissionGroup,
      initialCommissions: JSON.parse(JSON.stringify(emptyCommissionGroup))
    }

    this.handleChange = this.handleChange.bind(this);
    this.toggleCommission = this.toggleCommission.bind(this);
    this.putCommissions = this.putCommissions.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.fetchCommissions();
  }

  handleChange(insuranceType: string) {
      return (e: any) => {
        const com = this.state.commissions[insuranceType];
        com.value = Number(e.target.value);
        const newstate = this.state;
        newstate.commissions[insuranceType] = com;
        this.setState({ commissions: newstate.commissions  });
      }
  }

  toggleCommission() {
    return () => {
      this.setState({ showForm: !this.state.showForm });
    }
  }

  setCommission(insuranceType: string, commission: CommissionData) {
    let state: State = { ...this.state };
    commission.fleet = this.props.fleetId;
    commission.company = this.props.companyId;
    commission.vehicle = this.props.vehicleId;
    commission.vehicleType = this.props.vehicleType;
    state.initialCommissions[insuranceType] = commission;
    state.commissions[insuranceType] = { ...commission };
    this.setState({ ...state });
  }

  fetchCommission(insuranceType: string) {
    this.props.fetchCommission(this.props.vehicleType, insuranceType, (data) => { //Dirty
      this.setCommission(insuranceType, data.data[0]);
    });
  }

  getChanges() : CommissionData[] {
    const insuranceTypes = ['CivilLiability','Omnium', 'DriverInsurance', 'TravelInsurance', 'LegalAid']
    const initial = insuranceTypes.map((d) => this.state.initialCommissions[d]);
    const changed = insuranceTypes.map((d) => this.state.commissions[d]);
    return _.differenceWith(changed, initial, (a: CommissionData, b: CommissionData) =>
      ( a.value === b.value && a.insuranceType === b.insuranceType && a.vehicleType === b.vehicleType ));
  }

  putCommissions() {
    const diff = this.getChanges();
    let success = () => {
      for(let i=0; i < (diff.length-1); i++) {
        this.props.putCommission(diff[i]);
      }
      redirect_to(this.props.returnTo);
    }
    this.props.putCommission(diff[diff.length-1], success);
  }

  fetchCommissions() {
    this.fetchCommission('CivilLiability');
    this.fetchCommission('Omnium');
    this.fetchCommission('DriverInsurance');
    this.fetchCommission('TravelInsurance');
    this.fetchCommission('LegalAid');
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
              <CommissionGroup
                toggleForm={ this.toggleCommission }
                showForm={ this.state.showForm }
                commission={ this.state.commissions }
                handleChange={ this.handleChange }
                vehicleType={ this.props.vehicleType }/>
            </div>
            <div className='col-xs-12 col-md-5'>
              <div className='row'>
                <Actions submitLabel={ 'Submit' } cancelUrl={ this.props.returnTo } model={ `Vehicle: ${this.props.vehicleId}` } />
              </div>
            </div>
          </div>
        </div>
      </form>
    );
  }
}

export default CommissionGroupForm;
