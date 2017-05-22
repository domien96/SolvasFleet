import React from 'react';
import Layout from './Layout.tsx';
import { fetchVehicles, postVehiclesFile } from '../../actions/vehicle_actions.ts';
import { fetchClients } from '../../actions/client_actions.ts';
import { fetchFleets } from '../../actions/fleet_actions.ts';
import { redirect_to } from '../../routes/router.tsx';
import T from 'i18n-react';
import { createQuery } from '../../utils/utils.ts';

interface State {
    filter: VehicleFilterData;
    response: ListResponse;
    errors: any[];
    file: any;
    csvsuccess: boolean;
    tableData: any;
    companies: CompanyData[];
    fleets: FleetData[];
    init: boolean;
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
        archived: 'false',
        sort: 'id',
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
      errors: [],
      file: null,
      csvsuccess: false,
      companies: [],
      fleets: [],
      tableData: [],
      init: false
   };
    this.handleFilter = this.handleFilter.bind(this);
    this.fetchVehicles = this.fetchVehicles.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.getFleet = this.getFleet.bind(this);
    this.getCompany = this.getCompany.bind(this);
  }

  componentDidMount() {
    this.fetchVehicles(undefined, this.state.filter);
    this.fetchCompanies();
  }

  fetchVehicles(query?: any, filter?: VehicleFilterData) {
    let newQuery = createQuery(query, filter);

    fetchVehicles((data) => {
      this.setState({ response: data })
      this.setTableData(data.data, this.state.companies, this.state.fleets);
    }, undefined, newQuery);
  }

  fetchCompanies() {
    fetchClients((data: any) => {
      this.setState({ companies: data.data });
      this.fetchFleets(data.data);
    });
  }

  fetchFleets(companies: CompanyData[]){
    let allFleets: FleetData[] = []
    let i = 0;
    const l = companies.length;
    if (companies) {
      companies.map((company: CompanyData) => {
        i = i + 1;
        fetchFleets(company.id, (data: any) => {
          let fleets: FleetData[] = data.data
          fleets.map((fleet: FleetData) => {
            allFleets.push(fleet);
          })
          if (i == l) {
            this.setState({ init: true })
          }
          this.setState({ fleets: allFleets });
          this.setTableData(this.state.response.data, companies, allFleets);
        });
      })
    }
  }

  handleFilter(newFilter: VehicleFilterData) {
    this.setState({ filter: newFilter });
    this.fetchVehicles(undefined, newFilter);
  }

  handleClick(id: number) {
    redirect_to(`/vehicles/${id}`);
  }

  handleChange(e: any) {
    const file = e.target.files[0];
    this.setState({ file });
  }

  handleSubmit() {
    const setErrors = (es: any) => {
      const errors = es.errors.map((row: any) => {
        return Object.keys(row).map(index => {
          return {
            row: index,
            errors: row[index].map((er: any) => ({ field: er.field, error: er.type }))
          }
        })
      });
      this.setState({ errors: [].concat.apply([], errors) });
    }
    const success = () => {
      this.fetchVehicles(this.state.filter)
      this.setState({ csvsuccess: true, errors: [] });
    };
    postVehiclesFile(this.state.file, success, setErrors);
  }

  getFleet(inputCompanies: CompanyData[], inputFleets: FleetData[], fleetId: number, init?: boolean) {
    let fleets: FleetData[] = inputFleets;
    let companies: CompanyData[] = inputCompanies;
    if (init) {
      fleets = this.state.fleets;
      companies = this.state.companies;
    }
    if (fleets.length > 0) {
      const fleetFiltered = fleets.find((f: FleetData) => {
        return f.id === fleetId;
      });
      if (!fleetFiltered) {
        return 'No fleet';
      }
      if (companies.length > 0) {
        const companyFiltered = companies.find((c: CompanyData) => {
          return c.id === fleetFiltered.company;
        })
        if (!companyFiltered) {
          return '<empty>'
        }
        return `${companyFiltered.name}: ${fleetFiltered.name}`
      }
      return fleetFiltered.name;
    }
    else {
      return fleetId.toString();
    }
  }

  getCompanyId(inputFleets: FleetData[], fleetId: number) {
    let fleets: FleetData[] = inputFleets;

    if (fleets.length > 0) {
      const fleetFiltered = fleets.find((f: FleetData) => {
        return f.id === fleetId;
      });
      if (fleetFiltered) {
        return fleetFiltered.company;
      }
    }
    return -1;
  }

  setTableData(vehicles: VehicleData[], companies: CompanyData[], fleets: FleetData[]) {
    const data = vehicles.map((vehicle: VehicleData) => {
      return {
        id: vehicle.id,
        licensePlate: vehicle.licensePlate,
        vin: vehicle.vin,
        brand: vehicle.brand,
        model: vehicle.model,
        type: T.translate(`vehicle.options.${vehicle.type}`).toString(),
        mileage: vehicle.mileage,
        year: vehicle.year,
        leasingCompany: vehicle.leasingCompany,
        value: vehicle.value,
        fleet: this.getFleet(companies, fleets, vehicle.fleet),
        companyId: this.getCompanyId(fleets, vehicle.fleet),
        fleetId: vehicle.fleet
      }
    });

    this.setState({ tableData: data });
  }

  getCompany(id: number) {
    if (this.state.companies) {
      const companyFiltered = this.state.companies.find((c: CompanyData) => {
        return c.id === id;
      })
      if (companyFiltered) {
        return companyFiltered.name;
      }
    }
    return '';
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
        getFleet={ this.getFleet }
        getCompany={ this.getCompany }
        onFilter={ this.handleFilter }
        fetchVehicles={ this.fetchVehicles }
        errors={ this.state.errors }
        handleChange={ this.handleChange }
        handleSubmit={ this.handleSubmit }
        tableData={ this.state.tableData }
        init={ this.state.init }
        csvsuccess={ this.state.csvsuccess } >
        { children }
      </Layout>
    );
  }
}

export default Vehicles;
