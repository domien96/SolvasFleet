import React from 'react';
import T     from 'i18n-react';

import VehicleFilterLayout from './VehicleFilterLayout.tsx'
import HiddenFilter from '../../filters/HiddenFilter.tsx'

interface FilterProps {
  vehicles : Vehicle[];
  onFilter : (filter : VehicleFilterData) => void;
}

interface FilterState {
  filter : VehicleFilterData;
  typeDisplay: string;
  hidden: boolean;
  licensePlateData: string[];
}

class VehicleFilter extends React.Component<FilterProps, FilterState>{

	constructor(){
		super();
		this.state = { filter: {fleet : '', type : '', leasingCompany: '', licensePlate: '', vin: '', year: ''}, typeDisplay: 'All vehicles', hidden:false, licensePlateData:[] };
		this.handleFilterFleet = this.handleFilterFleet.bind(this);
		this.handleFilterType = this.handleFilterType.bind(this);
		this.handleFilterLeasingCompany = this.handleFilterLeasingCompany.bind(this);
		this.handleFilterLicensePlate = this.handleFilterLicensePlate.bind(this);
		this.handleFilterVin = this.handleFilterVin.bind(this);
		this.handleFilterYear = this.handleFilterYear.bind(this);
		this.handleReset = this.handleReset.bind(this);
		this.handleHide = this.handleHide.bind(this);
		this.handleShow = this.handleShow.bind(this);
		this.setLicensePlates = this.setLicensePlates.bind(this);
	}

	componentDidMount(){
		this.setLicensePlates(this.props.vehicles);
	}

	componentWillReceiveProps(nextProps : any){
		if(this.props.vehicles != nextProps.vehicles){
			this.setLicensePlates(nextProps.vehicles);
		}
	}

	handleFilterFleet(event : any){
		var newFilter = this.state.filter;
		newFilter.fleet = event.target.value;
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleFilterType(event : string){	
		var type = event;
		var newFilter = this.state.filter;
		if(type == 'allVehicles'){
			newFilter.type = '';
			let typeTranslation = T.translate('vehicle.options.allVehicles').toString();
			this.setState( {filter: newFilter, typeDisplay: typeTranslation} );
		}
		else{
			newFilter.type = type;
			let typeTranslation = T.translate('vehicle.options.'+type).toString();
			this.setState( {filter: newFilter, typeDisplay: typeTranslation} );
		}
		this.props.onFilter( newFilter );
	}

	handleFilterLeasingCompany(event : any){
		var newFilter = this.state.filter;
		newFilter.leasingCompany = event.target.value;
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleFilterLicensePlate(selected : string[]){
		var newFilter = this.state.filter;
		newFilter.licensePlate = selected[0];
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleFilterVin(event : any){
		var newFilter = this.state.filter;
		newFilter.vin = event.target.value;
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleFilterYear(event : any){
		var newFilter = this.state.filter;
		newFilter.year = event.target.value;
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleReset(){
		var newFilter : VehicleFilterData = {fleet : '', type : '', leasingCompany: '', licensePlate: '', vin: '', year: ''};
		this.setState( {filter: newFilter, typeDisplay: 'All vehicles'} );
		this.props.onFilter(newFilter);
	}

	handleHide(){
		this.setState({ hidden: true });
	}

	handleShow(){
		this.setState({ hidden: false });
	}

	setLicensePlates(vehicles : Vehicle[]){
		var newLicensePlateData = vehicles.map((vehicle) =>{
			if(vehicle.licensePlate != null && vehicle.licensePlate != undefined && vehicle.licensePlate != '') return vehicle.licensePlate;
			else return "";
		});
		var newLicensePlateDataFiltered = newLicensePlateData.filter((licensePlate) => {
			if(licensePlate != "") return true;
			else return false;
		});
		this.setState( {licensePlateData: newLicensePlateDataFiltered} );
	}

	render(){

		var { filter, typeDisplay, licensePlateData } = this.state;

		if(this.state.hidden || licensePlateData == []){
			return(
				<HiddenFilter onReset={ this.handleReset } onShow={ this.handleShow }/>
			);
		}
		else{
			return(
				<VehicleFilterLayout 
					filter={ filter } 
					typeDisplay={ typeDisplay } 
					licensePlateData={ licensePlateData }
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