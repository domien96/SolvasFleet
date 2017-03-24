import React from 'react';
import T     from 'i18n-react';


import FilterLayout from './FilterLayout.tsx'

interface FilterProps {
  onFilter : (filter : VehicleFilter) => void;
}

interface FilterState {
  filter : VehicleFilter;
  typeDisplay: string;
}

class Filter extends React.Component<FilterProps, FilterState>{

	constructor(){
		super();
		this.state = { filter: {fleet : '', type : ''}, typeDisplay: 'All vehicles' }
		this.handleFilterFleet = this.handleFilterFleet.bind(this);
		this.handleFilterType = this.handleFilterType.bind(this);
	}

	handleFilterFleet(event : any){
		var newFilter = this.state.filter;
		newFilter.fleet = event.target.value;
		this.setState( {filter: newFilter} )
		this.props.onFilter( newFilter );
	}

	handleFilterType(type : string){	
		var newFilter = this.state.filter;
		if(type == ''){
			newFilter.type = '';
			this.setState( {filter: newFilter, typeDisplay: 'All vehicles'} );
		}
		else{
			newFilter.type = type;
			let typeTranslation = T.translate(type).toString();
			this.setState( {filter: newFilter, typeDisplay: typeTranslation} );
		}
		this.props.onFilter( newFilter );
	}

	render(){
		return(
			<FilterLayout filter={ this.state.filter } typeDisplay={ this.state.typeDisplay } onFilterType={ this.handleFilterType } onFilterFleet={ this.handleFilterFleet } />
		);
	}
}

export default Filter;