import React from 'react';
import { Link } from'react-router';
import { Collapse } from 'react-bootstrap';

import FleetsCard from './FleetsCard.tsx';
import { postFleet } from '../../actions/fleet_actions.ts';

import { redirect_to } from '../../router.tsx';

class Fleets extends React.Component<Fleets.Props, Fleets.State> {
  constructor(props : Fleets.Props) {
    super(props);
    this.state = {
      formVisible: false,
      fleet: { company: this.props.company },
      errors: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onClick = this.onClick.bind(this);
  }

  handleChange(field : Fleet.Field, e : any) : any {
    var fleet : Fleet = this.state.fleet;
    fleet[field] = e.target.value;
    this.setState({ fleet });
  }

  onClick() {
    this.setState({ formVisible: true })
  }

  onSubmit(e : any) {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = (data : any) => redirect_to(`/fleets/${data.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e.field, error: 'null' };
      }));
    }

    postFleet(this.state.fleet, success, fail);
  }

  render() {
    return (
      <FleetsCard
        fleets={ this.props.fleets }
        onSubmit={ this.onSubmit }
        handleChange={ this.handleChange }
        formIsVisible={ this.state.formVisible }
        onClick={ this.onClick }
      />
    );
  }
}

export default Fleets;
