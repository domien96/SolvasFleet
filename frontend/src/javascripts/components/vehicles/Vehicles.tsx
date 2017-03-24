import React from 'react';
import { browserHistory } from'react-router';

import Layout     from './Layout.tsx'

import fetchVehicles from '../../actions/fetch_vehicles.ts';

class Vehicles extends React.Component<{}, Vehicles.State> {

  constructor(props : {}) {
    super(props);
    this.state = { vehicles: [], filter : {fleet : '', type : ''} };
    this.handleFilter = this.handleFilter.bind(this);
  }

  componentDidMount() {
    this.fetchVehicles(this.state.filter);
  }

  fetchVehicles(filter : VehicleFilter) {
    //TODO
  }

  handleFilter(newFilter: VehicleFilter){
    this.setState({ filter: newFilter });
    this.fetchVehicles(newFilter);
  }

  handleClick(id : number) {
    browserHistory.push('/vehicles/' + id);
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child : any) => React.cloneElement(child, {
        fetchVehicles: this.fetchVehicles.bind(this)
      })
    );
    return (
      <Layout vehicles={ this.state.vehicles } onVehicleSelect={ this.handleClick } onFilter={ this.handleFilter }>
        { children }
      </Layout>
    );
  }
}

export default Vehicles;
