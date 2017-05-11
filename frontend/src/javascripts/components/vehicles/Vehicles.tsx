import React from 'react';
import Layout from './Layout.tsx';
import { fetchVehicles } from '../../actions/vehicle_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

interface State {
    filter: VehicleFilterData;
    response: ListResponse;
  }

class Vehicles extends React.Component<{}, State> {

  constructor(props: {}) {
    super(props);
    this.state = {
      filter: {
        fleet: '',
        leasingCompany: '',
        licensePlate: '',
        type: '',
        vin: '',
        year: '',
      }, response: {
        data: [],
        first: '',
        last: '',
        limit: 0,
        next: '',
        offset: 0,
        previous: '',
        total: 0,
      },
   };
    this.handleFilter = this.handleFilter.bind(this);
    this.fetchVehicles = this.fetchVehicles.bind(this);
  }

  componentDidMount() {
    this.fetchVehicles(this.state.filter);
  }

  fetchVehicles(filter: VehicleFilterData) {
    const query = filter;
    for (const key in query) {
      if (query[key] === null || query[key] === undefined || query[key] === '') {
        delete query[key];
      }
    }
    fetchVehicles((data) => this.setState({ response: data }), undefined, query);
  }

  handleFilter(newFilter: VehicleFilterData) {
    this.setState({ filter: newFilter });
    this.fetchVehicles(newFilter);
  }

  handleClick(id: number) {
    redirect_to(`/vehicles/${id}`);
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child: any) => React.cloneElement(child, {
        fetchVehicles: this.fetchVehicles.bind(this),
      }),
    );
    return (
      <Layout
        response={this.state.response}
        onVehicleSelect={ this.handleClick }
        onFilter={ this.handleFilter }
        fetchVehicles={this.fetchVehicles} >
        { children }
      </Layout>
    );
  }
}

export default Vehicles;
