import React from 'react';

import { fetchContract, deleteContract } from '../../actions/contract_actions.ts';
import { redirect_to } from '../../routes/router.tsx';
import { fetchClients } from '../../actions/client_actions.ts';
import ContractView from './ContractView.tsx';
import T from 'i18n-react';

interface Props {
   params: { vehicleId: number, contractId: number };
}

interface State {
  contract: ContractData;
  companies: CompanyData[];
}

class Contract extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { contract: {}, companies: [] };
    this.handleDelete = this.handleDelete.bind(this);
    this.handleGetCompanyName = this.handleGetCompanyName.bind(this);
  }

  fetchContract(contractId: number) {
    fetchContract(contractId, ((data) => {
      this.setState({ contract: data });
    }));
  }

  fetchClients() {
    fetchClients((data: any) => {
      this.setState({ companies: data.data })
    }, undefined, 'type: InsuranceCompany');
  }

  componentDidMount() {
    this.fetchContract(this.props.params.contractId);
    this.fetchClients();
  }

  handleDelete() {
    deleteContract(this.props.params.contractId, () => redirect_to('/contracts'));
  }

  handleGetCompanyName(id: number) {
    if (id === -1) {
      return T.translate('company.allCompanies');
    }
    if (this.state.companies.length > 0) {
      const companiesFiltered = this.state.companies.filter((c: CompanyData) => {
        return c.id === id;
      });
      return companiesFiltered[0].name;
    }
    else {
      return id;
    }
  }

  render() {
    return(
      <ContractView 
        contract={ this.state.contract } 
        handleDelete={ this.handleDelete } 
        onGetCompanyName={ this.handleGetCompanyName } />
    );
  }
}

export default Contract;
