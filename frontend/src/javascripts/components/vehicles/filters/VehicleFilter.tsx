import React from 'react';
import T from 'i18n-react';

import VehicleFilterLayout from './VehicleFilterLayout.tsx';
import HiddenFilter from '../../filters/HiddenFilter.tsx';

interface FilterProps {
  vehicles: VehicleData[];
  onFilter: (filter: VehicleFilterData) => void;
}

interface FilterState {
  filter: VehicleFilterData;
  typeDisplay: string;
  hidden: boolean;
  licensePlateData: string[];
  vinData: string[];
}

class VehicleFilter extends React.Component<FilterProps, FilterState> {
  constructor() {
    super();
    this.state = {
      filter: { fleet: '', type: '', leasingCompany: '', licensePlate: '', vin: '', year: '' },
      hidden: false,
      licensePlateData: [],
      typeDisplay: 'All vehicles',
      vinData: [],
    };

    this.handleFilterFleet = this.handleFilterFleet.bind(this);
    this.handleFilterType = this.handleFilterType.bind(this);
    this.handleFilterLeasingCompany = this.handleFilterLeasingCompany.bind(this);
    this.handleFilterLicensePlate = this.handleFilterLicensePlate.bind(this);
    this.handleFilterVin = this.handleFilterVin.bind(this);
    this.handleFilterYear = this.handleFilterYear.bind(this);
    this.handleReset = this.handleReset.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.handleShow = this.handleShow.bind(this);
    this.setTypeaheadOptions = this.setTypeaheadOptions.bind(this);
  }

  componentDidMount() {
    if (this.props.vehicles) {
      this.setTypeaheadOptions(this.props.vehicles);
    }
  }

  componentWillReceiveProps(nextProps: any) {
    if (this.props.vehicles !== nextProps.vehicles) {
      this.setTypeaheadOptions(nextProps.vehicles);
    }
  }

  handleFilterFleet(event: any) {
    const newFilter = this.state.filter;
    newFilter.fleet = event.target.value;
    this.setState({ filter: newFilter });
    this.props.onFilter(newFilter);
  }

  handleFilterType(event: string) {
    const type = event;
    const newFilter = this.state.filter;
    if (type === 'allVehicles') {
      newFilter.type = '';
      const typeTranslation = T.translate('vehicle.options.allVehicles').toString();
      this.setState( {filter: newFilter, typeDisplay: typeTranslation} );
    } else {
      newFilter.type = type;
      const typeTranslation = T.translate(`vehicle.options.${type}`).toString();
      this.setState( { filter: newFilter, typeDisplay: typeTranslation } );
    }
    this.props.onFilter(newFilter);
  }

  handleFilterLeasingCompany(event: any) {
    const newFilter = this.state.filter;
    newFilter.leasingCompany = event.target.value;
    this.setState({ filter: newFilter });
    this.props.onFilter(newFilter);
  }

  handleFilterLicensePlate(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.licensePlate = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter(newFilter);
  }

  handleFilterVin(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.vin = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterYear(event: any) {
    const newFilter = this.state.filter;
    newFilter.year = event.target.value;
    this.setState({ filter: newFilter });
    this.props.onFilter(newFilter);
  }

  handleReset() {
    const newFilter: VehicleFilterData = {
      fleet: '',
      leasingCompany: '',
      licensePlate: '',
      type: '',
      vin: '',
      year: '',
    };
    this.setState({ filter: newFilter, typeDisplay: 'All vehicles' });
    this.props.onFilter(newFilter);
  }

  handleHide() {
    this.setState({ hidden: true });
  }

  handleShow() {
    this.setState({ hidden: false });
  }

  setTypeaheadOptions(vehicles: VehicleData[]) {
    const newLicensePlateData: string[] = [];
    const newVinData: string[] = [];

    vehicles.map((vehicle) => {
      if (vehicle.licensePlate !== null && vehicle.licensePlate !== undefined && vehicle.licensePlate !== '') {
        newLicensePlateData.push(vehicle.licensePlate);
      }

      if (vehicle.vin !== null && vehicle.vin !== undefined && vehicle.vin !== '') {
        newVinData.push(vehicle.vin);
      }
    });

    this.setState({ licensePlateData: newLicensePlateData, vinData: newVinData });
  }

  render() {
    const { filter, typeDisplay, licensePlateData, vinData } = this.state;
    console.log(this.state);
    if (this.state.hidden || this.props.vehicles === []) {
      return(
        <HiddenFilter onReset={ this.handleReset } onShow={ this.handleShow }/>
      );
    } else {
      return(
        <VehicleFilterLayout
          filter={ filter }
          typeDisplay={ typeDisplay }
          licensePlateData={ licensePlateData }
          vinData={ vinData }
          onFilterType={ this.handleFilterType }
          onFilterFleet={ this.handleFilterFleet }
          onFilterLeasingCompany={ this.handleFilterLeasingCompany }
          onFilterLicensePlate={ this.handleFilterLicensePlate }
          onFilterVin={ this.handleFilterVin }
          onFilterYear={ this.handleFilterYear }
          onReset={ this.handleReset }
          onHide={ this.handleHide }
        />
      );
    }
  }
}

export default VehicleFilter;
