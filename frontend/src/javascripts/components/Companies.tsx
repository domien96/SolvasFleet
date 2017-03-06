import React from 'react';
//import T from 'i18n-react';

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

    //let tName = T.translate('form.placeholders.name');

    const tableHead = [
      { key: 'id', label: 'ID' },
      { key: 'name', label: 'Name' },
      { key: 'VATnumber', label: 'VAT number' },
      { key: 'phoneNumber', label: 'Phone number' },
      { key: 'address', label: 'Address' }
    ];

    const comp = this.state.companies;

    return (
      <WrappedCol cols={ 12 }>
        <Card>
          <div className='card-title'><h5>List of all companies</h5></div>
          <div className='card-content'>
            <InfoTable head={tableHead} data={comp}/>  
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default Companies;
