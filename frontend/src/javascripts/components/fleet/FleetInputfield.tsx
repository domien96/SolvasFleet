import React from 'react';
import T from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';
import { fetchClients } from '../../actions/client_actions.ts';
import { fetchFleets } from '../../actions/fleet_actions.ts';
import classNames from 'classnames';

interface Props {
  value: number;
  callback: (e: any) => void;
  placeholder: string;
  hasError: boolean;
}

interface State {
  companies: CompanyData[];
  fleets: FleetData[];
}

class FleetInputfield extends React.Component<Props, State> {

  constructor(props : any) {
    super(props);
    this.state = { companies: [], fleets: [] };
    this.fetchClients = this.fetchClients.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.getCompanyName = this.getCompanyName.bind(this);
  }

  componentDidMount() {
    this.fetchClients();
    this.fetchFleets();
  }

  componentWillReceiveProps(nextProps: any) {
    if (nextProps.value !== this.props.value) {
      this.fetchClients();
      this.fetchFleets();
    }
  }

  handleChange(selectedFleets: string[]) {
    if(selectedFleets){
      let e = { target: { value: parseInt(selectedFleets[0].split(':')[0], 10) } };
      this.props.callback(e);
    }
  }

  fetchClients(query?: any) {
    fetchClients((data: any) => {
      this.setState({ companies: data.data });
      this.fetchFleets();
    }, undefined, query);
  }

  getCompanyName(id: number) {
    let company = this.state.companies.filter((company) => company.id == id);
    return company[0].name;
  }

  fetchFleets(){
    let allFleets: FleetData[] = []
    if (this.state.companies) {
      this.state.companies.map((company: CompanyData) => {
        fetchFleets(company.id, (data: any) => {
          let fleets: FleetData[] = data.data 
          fleets.map((fleet: FleetData) => {
            allFleets.push(fleet);
          })
          this.setState({ fleets: allFleets });
        });
      })
    }
  }

  render() {
    let optionList: string[] = [];
    let selected;
    if (this.state.fleets) {
      optionList = this.state.fleets.map((f: FleetData) => {
        let option = `${this.getCompanyName(f.company)} - ${f.id.toString()}: ${f.name}`;
        if (f.id === this.props.value) {
          selected = [option];
        }
        return option;
      });
    }

    const label = T.translate(this.props.placeholder);
    const wrapperClasses = classNames('form-group', { 'has-error': this.props.hasError });

    return (
      <div className={ wrapperClasses }>
        <label>{ label }</label>
        <Typeahead onChange={ this.handleChange } options={ optionList } selected={ selected }/>
      </div>
    );
  }
}

export default FleetInputfield;
