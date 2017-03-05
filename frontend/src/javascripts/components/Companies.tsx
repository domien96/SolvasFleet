import React from 'react';

import fetchCompanies from '../actions/fetch_companies.ts';

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
    const companies = this.state.companies.map((c, i) =>
      <div key={ i } >{ c.name }</div>
    );
    return (
      <div>
        { companies }
      </div>
    );
  }
}

export default Companies;
