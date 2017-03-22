import React from 'react';

import FilterLayout from './FilterLayout.tsx'

interface FilterProps {
  onFilter : (filter : Filter) => void;
}

interface FilterState {
  filter : Filter;
  typeDisplay: string;
}

class Filter extends React.Component<FilterProps, FilterState>{

	constructor(){
		super();
		this.state = { filter: {fleetId : '', type : ''}, typeDisplay: 'All vehicles' }
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
			newFilter.typeDisplay = 'All vehicles';
			this.setState( {filter: newFilter} );
		}
		else{
			newFilter.type = type;
			newFilter.typeDisplay = T.translate(type);
			this.setState( {filter: newFilter} );
		}
		this.props.onFilter( newFilter );
	}

	render(){
		return(
			<FilterLayout filter={ this.state.filter } typeDisplay={ this.state.typeDisplay } onFilterType={ this.handleFilterType } onFilterFleet={ this.handleFilterFleet } />
		);
	}
}