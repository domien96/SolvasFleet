import React from 'react';
import T from 'i18n-react';

import fetchCompanies from '../actions/fetch_companies.ts';
import InfoTable from './tables/InfoTable.tsx';

class Companies extends React.Component<CompaniesProps, CompaniesState> {

  constructor(props : CompaniesProps) {
    super(props);
    this.state = { companies: [] };
  }

  componentDidMount() {
    fetchCompanies()
      .then((data : CompaniesData) => {
        this.setState({ companies: data.companies })
      });
  }

  render() {

    let tName = T.translate('form.placeholders.name');

    const tableHead = [
      { key: 'id', label: 'ID' },
      { key: 'name', label: {tName} }
    ];

    return (
      <div>
        Testing:
        <InfoTable head={tableHead} data={this.state.companies} />  
      </div>
    );
  }
}

export default Companies;
