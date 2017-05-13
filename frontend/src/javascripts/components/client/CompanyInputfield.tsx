import React from 'react';
import T from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';
import { fetchClients } from '../../actions/client_actions.ts';
import classNames from 'classnames';

interface Props {
  value: number[];
  callback: (e: any) => void;
  placeholder: string;
  hasError: boolean;
  query?: any;
  multiple?: boolean;
}

interface State {
  companies: CompanyData[];
}

class CompanyInputfield extends React.Component<Props, State> {

  constructor(props: any) {
    super(props);
    this.state = { companies: [] };
    this.fetchClients = this.fetchClients.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount(){
    this.fetchClients(this.props.query);
  }

  componentWillReceiveProps(nextProps: any) {
    if (nextProps.value !== this.props.value) {
      this.fetchClients(nextProps.query);
    }
  }

  handleChange(selectedCompanies: string[]) {
    if (selectedCompanies) {
      console.log(selectedCompanies);
      let ids: any = [];
      for (let i = 0; i < selectedCompanies.length; i++){
        const e = { target: { value: parseInt(selectedCompanies[i].split(':')[0], 10) } };
        ids.push(e);
      }
      if (ids.length === 1) {
        this.props.callback(ids[0]);
      } else {
        this.props.callback(ids);
      }
    }
  }

  fetchClients(query?: any) {
    fetchClients((data: any) => {
      this.setState({ companies: data.data })
    }, undefined, query);
  }

  render() {
    let optionList: string[] = [];
    let selected: string[] = [];
    if (this.state.companies) {
      optionList = this.state.companies.map((c: CompanyData) => {
        const option = `${c.id.toString()}: ${c.name}`;
        this.props.value.map((v: number) => {
          if (c.id === v) {
            selected.push(option);
          }
        });
        return option;
      });
    }
    if (this.props.multiple) {
      const allCompaniesTranslated = T.translate('company.allCompanies');
      const allCompanies = `-1: ${allCompaniesTranslated}`
      optionList.push(allCompanies);
      if (selected.length === 0) {
        selected.push(allCompanies);
      }
    }

    const label = T.translate(this.props.placeholder);
    const wrapperClasses = classNames('form-group', { 'has-error': this.props.hasError });
    let typeahead;
    if(this.props.multiple){
      typeahead = <Typeahead multiple onChange={ this.handleChange } options={ optionList } selected={ selected }/>;
    }
    else{
      typeahead = <Typeahead onChange={ this.handleChange } options={ optionList } selected={ selected }/>;
    }

    return (
      <div className={ wrapperClasses }>
        <label>{ label }</label>
        {typeahead}
      </div>
    );
  }
}

export default CompanyInputfield;
