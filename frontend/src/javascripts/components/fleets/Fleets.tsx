import React from 'react';

import FleetsCard from './FleetsCard.tsx';
import { postFleet } from '../../actions/fleet_actions.ts';
import Errors from '../../modules/Errors.ts';
import { redirect_to } from '../../routes/router.tsx';

interface Props {
    fleets: FleetData[];
    company: number;
}

interface State {
    formVisible: boolean;
    fleet: FleetData;
    errors: Form.Error[];
}

class Fleets extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      errors: [],
      fleet: { company: this.props.company, facturationPeriod: 3, paymentPeriod: 1 },
      formVisible: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onClick = this.onClick.bind(this);
  }

  handleChange(field: Fleet.Field, e: any): any {
    const fleet: FleetData = this.state.fleet;
    fleet[field] = e.target.value;
    this.setState({ fleet });
  }

  onClick() {
    this.setState({ formVisible: !this.state.formVisible })
  }

  onSubmit(e: any) {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = (data: any) => redirect_to(`/fleets/${data.id}`);

    postFleet(this.props.company, this.state.fleet, success, Errors.handle(setErrors));
}

  render() {
    return (
      <FleetsCard
        fleets={ this.props.fleets }
        errors={ this.state.errors }
        onSubmit={ this.onSubmit }
        handleChange={ this.handleChange }
        formIsVisible={ this.state.formVisible }
        onClick={ this.onClick }
        fleet={ this.state.fleet }
      />
    );
  }
}

export default Fleets;
