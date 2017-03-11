import React from 'react';


import fetchCompanies from '../actions/fetch_companies.ts';
import InfoTable from './tables/InfoTable.tsx';
import Card       from './app/Card.tsx';
import WrappedCol from './app/WrappedCol.tsx';

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
    //label = translation key for i18n
    const tableHead = [
      { key: 'id', label: 'company.id' },
      { key: 'name', label: 'company.name' },
      { key: 'VATnumber', label: 'company.vatNumber' },
      { key: 'phoneNumber', label: 'company.phoneNumber' },
      { key: 'address', label: 'company.address' }
    ];

    return (
      <WrappedCol cols={ 12 }>
        <Card>
          <div className='card-title'><h5>List of all companies</h5></div>
          <div className='card-content'>
            <InfoTable head={tableHead} data={this.state.companies}/>  
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default Companies;
