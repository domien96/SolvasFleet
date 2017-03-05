import React from 'react';

// import fetchCompanies from '../api.ts';

class Companies extends React.Component<CompaniesProps, CompaniesState> {

  constructor(props : CompaniesProps) {
    super(props);
    this.state = { companies: [] };
  }

  componentDidMount() {

    fetch(this.props.url)
      .then(function(response) {
        return response.json()
      })
      .then((data:any) => {
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
