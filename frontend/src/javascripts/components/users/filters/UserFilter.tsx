import React from 'react';
import UserFilterLayout from './UserFilterLayout.tsx';
import HiddenFilter from '../../filters/HiddenFilter.tsx';
import T from 'i18n-react';

interface FilterProps {
  users: UserData[];
  onFilter: (filter: UserFilterData) => void;
}

interface FilterState {
  filter: UserFilterData;
  hidden: boolean;
  firstNameData: string[];
  lastNameData: string[];
  emailData: string[];
  sortDisplay: string;
}

class UserFilter extends React.Component<FilterProps, FilterState> {
  constructor() {
    super();
    this.state = {
      filter: { firstName: '', lastName: '', email: '', archived: 'false', sort: 'id' },
      hidden: false,
      firstNameData: [],
      lastNameData: [],
      emailData: [],
      sortDisplay: 'Sort'
    };

    this.handleFilterFirstName = this.handleFilterFirstName.bind(this);
    this.handleFilterLastName = this.handleFilterLastName.bind(this);
    this.handleFilterEmail = this.handleFilterEmail.bind(this);
    this.handleFilterArchived = this.handleFilterArchived.bind(this);
    this.handleSort = this.handleSort.bind(this);
    this.handleReset = this.handleReset.bind(this);
    this.handleHide = this.handleHide.bind(this);
    this.handleShow = this.handleShow.bind(this);
  }

  componentDidMount() {
    this.setTypeaheadOptions(this.props.users);
  }

  componentWillReceiveProps(nextProps: any) {
    if (this.props.users !== nextProps.users) {
      this.setTypeaheadOptions(nextProps.users);
    }
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

  handleSort(event: string) {
    const newFilter = this.state.filter;
    newFilter.sort = event;
    const sortTranslation = T.translate(`user.${event}`).toString();
    this.setState( { filter: newFilter, sortDisplay: sortTranslation } );
    this.props.onFilter(newFilter);
  }

  handleFilterFirstName(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.firstName = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterLastName(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.lastName = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterEmail(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.email = selected[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleReset() {
    const newFilter: UserFilterData = { firstName: '', lastName: '', email: '', archived: 'false', sort: 'id' };
    const sortTranslation = T.translate('user.id').toString();
    this.setState({ filter: newFilter, sortDisplay: sortTranslation });
    this.props.onFilter(newFilter);
  }

  handleHide() {
    this.setState({ hidden: true });
  }

  handleShow() {
    this.setState({ hidden: false });
  }

  setTypeaheadOptions(users: UserData[]) {
    const firstNameData: string[] = [];
    const lastNameData: string[] = [];
    const emailData: string[] = [];
    users.map((user: UserData) => {
      if (user.firstName !== null && user.firstName !== undefined && user.firstName !== '') {
        firstNameData.push(user.firstName);
      }
      if (user.lastName !== null && user.lastName !== undefined && user.lastName !== '') {
        lastNameData.push(user.lastName);
      }
      if (user.email !== null && user.email !== undefined && user.email !== '') {
        emailData.push(user.email);
      }
    });
    this.setState({ firstNameData: firstNameData, lastNameData: lastNameData, emailData: emailData });
  }

  render() {
    const { filter, firstNameData, lastNameData, emailData, sortDisplay } = this.state;

    if (this.state.hidden || this.props.users === []) {
      return(
        <HiddenFilter onReset={ this.handleReset } onShow={ this.handleShow }/>
      );
    } else {
      return(
        <UserFilterLayout
          filter={ filter }
          firstNameData={ firstNameData }
          lastNameData={ lastNameData }
          emailData={ emailData }
          sortDisplay={ sortDisplay }
          onFilterFirstName={ this.handleFilterFirstName }
          onFilterLastName={ this.handleFilterLastName }
          onFilterEmail={ this.handleFilterEmail }
          onFilterArchive={ this.handleFilterArchived }
          onSort={ this.handleSort }
          onReset={ this.handleReset }
          onHide={ this.handleHide }
        />
      );
    }
  }
}

export default UserFilter;
