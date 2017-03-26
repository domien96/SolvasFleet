import React from 'react';
import { browserHistory, Link } from'react-router';
import { Collapse } from 'react-bootstrap';

import Card       from '../app/Card.tsx';
import FleetForm    from '../fleets/FleetForm.tsx';
import { postFleet } from '../../actions/fleet_actions.ts';

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
    let success = (data : any) => browserHistory.push('/fleets/' + data.id);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e.field, error: 'null' };
      }));
    }

    postFleet(this.state.fleet, success, fail);
  }

  render() {
    let fleets = this.props.fleets.map((f, i) => {
      return (
        <Link to={ '/fleets/' + f.id } key={ i } className='fleet'>
          <h3>{ f.name }</h3>
          <div className='actions pull-right'>
            <h3>
              <span className='glyphicon glyphicon-menu-right' />
            </h3>
          </div>
        </Link>
      )
    });

    return (
      <Card>
        <div className='card-title'>
          <h2>Fleets</h2>
          <span className='click' onClick={ this.onClick.bind(this) }>Add a new fleet</span>
        </div>
        <div className='card-content fleets'>
          <div className='fleet-form-wrapper'>
            <Collapse in={ this.state.formVisible }>
              <div>
                <FleetForm handleChange={ this.handleChange } onSubmit={ this.onSubmit } />
              </div>
            </Collapse>
          </div>
          { fleets }
        </div>
      </Card>
    )};
}

export default Fleets;
