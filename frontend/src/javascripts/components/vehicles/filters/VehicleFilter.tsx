import React from 'react';
import T     from 'i18n-react';

import VehicleFilterLayout from './VehicleFilterLayout.tsx'

interface FilterProps {
  onFilter : (filter : VehicleFilterData) => void;
}

interface FilterState {
  filter : VehicleFilterData;
  typeDisplay: string;
}

class VehicleFilter extends React.Component<FilterProps, FilterState>{

	constructor(){
		super();
		this.state = { filter: {fleet : '', type : ''}, typeDisplay: 'All vehicles' }
		this.handleFilterFleet = this.handleFilterFleet.bind(this);
		this.handleFilterType = this.handleFilterType.bind(this);
		this.handleReset = this.handleReset.bind(this);
	}

	handleFilterFleet(event : any){
		var newFilter = this.state.filter;
		newFilter.fleet = event.target.value;
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleFilterType(event : any){	
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

	handleReset(){
		var newFilter : VehicleFilterData = {fleet : '', type : ''};
		this.setState( {filter: newFilter, typeDisplay: 'All vehicles'} );
		this.props.onFilter(newFilter);
	}

	render(){
		return(
			<VehicleFilterLayout filter={ this.state.filter } typeDisplay={ this.state.typeDisplay } onFilterType={ this.handleFilterType } onFilterFleet={ this.handleFilterFleet } onReset={ this.handleReset }/>
		);
	}
}

export default VehicleFilter;