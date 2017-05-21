import React from 'react';
import ClientFilterLayout from './ClientFilterLayout.tsx';
import HiddenFilter from '../../filters/HiddenFilter.tsx';
import T from 'i18n-react';

interface FilterProps {
  companies: CompanyData[];
  onFilter: (filter: CompanyFilterData) => void;
}

interface FilterState {
  filter: CompanyFilterData;
  hidden: boolean;
  countryData: string[];
  vatData: string[];
  nameData: string[];
  typeDisplay: string;
}

class ClientFilter extends React.Component<FilterProps, FilterState> {
  constructor() {
    super();
    this.state = {
      filter: {
        nameContains: '',
        vatNumber: '',
        country: '',
        type: '',
        archived: 'false',
      },
      hidden: false,
      countryData: [],
      vatData: [],
      nameData: [],
      typeDisplay: T.translate('company.type.allTypes').toString()
    };

    this.handleFilterType = this.handleFilterType.bind(this);
    this.handleFilterVat = this.handleFilterVat.bind(this);
    this.handleFilterName = this.handleFilterName.bind(this);
    this.handleFilterCountry = this.handleFilterCountry.bind(this);
    this.handleFilterArchived = this.handleFilterArchived.bind(this);
    this.handleReset = this.handleReset.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.handleShow = this.handleShow.bind(this);
  }

  componentDidMount() {
    this.setTypeaheadOptions(this.props.companies);
  }

  componentWillReceiveProps(nextProps: any) {
    if (this.props.companies !== nextProps.companies) {
      this.setTypeaheadOptions(nextProps.companies);
    }
  }

  handleFilterType(event: string) {
    const type = event;
    const newFilter = this.state.filter;
    if (type === 'allTypes') {
      newFilter.type = '';
      const typeTranslation = T.translate('company.type.allTypes').toString();
      this.setState( {filter: newFilter, typeDisplay: typeTranslation} );
    } else {
      newFilter.type = type;
      const typeTranslation = T.translate(`company.types.${type}`).toString();
      this.setState( { filter: newFilter, typeDisplay: typeTranslation } );
    }
    this.props.onFilter(newFilter);
  }

  handleFilterArchived() {
    const newFilter = this.state.filter;
    if (this.state.filter.archived === 'true') {
      newFilter.archived = 'false';
    } else {
      newFilter.archived = 'true';
    }
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterVat(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.vatNumber = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterName(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.nameContains = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterCountry(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.country = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleReset() {
    const newFilter: CompanyFilterData = {
      name: '',
      vatNumber: '',
      country: '',
      type: '',
      archived: 'false'
    };
    const typeTranslation = T.translate('company.type.allTypes').toString();
    this.setState({ filter: newFilter, typeDisplay: typeTranslation });
    this.props.onFilter(newFilter);
  }

  handleHide() {
    this.setState({ hidden: true });
  }

  handleShow() {
    this.setState({ hidden: false });
  }

  setTypeaheadOptions(companies: CompanyData[]) {
    const newVatData: string[] = [];
    const newNameData: string[] = [];
    const newCountryData: string[] = [];


    companies.map((company: CompanyData) => {
      if (company.vatNumber !== null && company.vatNumber !== undefined && company.vatNumber !== '') {
        newVatData.push(company.vatNumber);
      }
      if (company.name !== null && company.name !== undefined && company.name !== '') {
        newNameData.push(company.name);
      }
      if (company.address.country !== null && company.address.country !== undefined && company.address.country !== '') {
        if (!newCountryData.includes(company.address.country)) {
          newCountryData.push(company.address.country);
        }
      }
    });

    this.setState({
      vatData: newVatData,
      nameData: newNameData,
      countryData: newCountryData
    });
  }

  render() {
    const { filter, typeDisplay, vatData, nameData, countryData } = this.state;

    if (this.state.hidden || this.props.companies === []) {
      return(
        <HiddenFilter onReset={ this.handleReset } onShow={ this.handleShow }/>
      );
    } else {
      return(
        <ClientFilterLayout
          filter={ filter }
          typeDisplay={ typeDisplay }
          vatData={ vatData }
          nameData={ nameData }
          countryData={ countryData }
          onFilterType={ this.handleFilterType }
          onFilterVat={ this.handleFilterVat }
          onFilterName={ this.handleFilterName }
          onFilterCountry={ this.handleFilterCountry }
          onFilterArchive={ this.handleFilterArchived }
          onReset={ this.handleReset }
          onHide={ this.handleHide }
        />
      );
    }
  }
}

export default ClientFilter;
