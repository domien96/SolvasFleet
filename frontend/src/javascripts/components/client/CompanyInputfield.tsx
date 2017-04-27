import React from 'react';
import T          from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';
import { fetchClients } from '../../actions/client_actions.ts';
import classNames from 'classnames';

interface Props {
	value : number;
  callback : (e : any) => void;
  placeholder : string;
  hasError    : boolean;
}

interface State {
  companies : CompanyData[];
}

class CompanyInputfield extends React.Component<Props, State> {

	constructor(props : any) {
    super(props);
    this.state = { companies : [] };
    this.fetchClients=this.fetchClients.bind(this);
    this.handleChange=this.handleChange.bind(this);
  }

	componentDidMount(){
		this.fetchClients();
	}

	componentWillReceiveProps(nextProps : any){
		if(nextProps.value != this.props.value){
			this.fetchClients();
		}
	}

	handleChange(selectedCompanies : string[]){
		if(selectedCompanies){
			let e = {target: {value: parseInt(selectedCompanies[0].split(':')[0])}};
			this.props.callback(e);
		}
	}

	fetchClients(query?:any) {
    fetchClients((data : any) => {
      this.setState({ companies: data.data })
    },undefined,query);
  }

	render(){
		let optionList : string[] = [];
		var selected;
		if(this.state.companies){
			optionList = this.state.companies.map((c : CompanyData) => {
				let option = (c.id.toString() + ': ' + c.name);
				if(c.id == this.props.value){
					selected = [option];
				}
				return option;
			});
		}

		const label = T.translate(this.props.placeholder);
		const wrapperClasses = classNames('form-group', { 'has-error': this.props.hasError });

		return(
			<div className={ wrapperClasses }>
				<label>{ label }</label>
				<Typeahead onChange={ this.handleChange } options={ optionList } selected={ selected }/>
			</div>	
		);
	}

}

export default CompanyInputfield;