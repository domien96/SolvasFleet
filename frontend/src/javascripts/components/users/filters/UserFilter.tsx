import React from 'react';
import T from 'i18n-react';

import UserFilterLayout from './UserFilterLayout.tsx';
import HiddenFilter from '../../filters/HiddenFilter.tsx';

interface FilterProps {
  users: UserData[];
  onFilter: (filter: UserFilterData) => void;
  getUser: (users: UserData[], id: number, init?: boolean) => string;
}

interface FilterState {
  filter: UserFilterData;
  hidden: boolean;
  userData: any;
  typeDisplay: string;
  methodDisplay: string;
}

class UserFilter extends React.Component<FilterProps, FilterState> {
  constructor() {
    super();
    this.state = {
      filter: { after: '', before: '', method: '', entityType: '', user: '' },
      hidden: false,
      userData: [],
      typeDisplay: 'All types',
      methodDisplay: 'All methods'
    };

    this.handleFilterType = this.handleFilterType.bind(this);
    this.handleFilterMethod = this.handleFilterMethod.bind(this);
    this.handleFilterUser = this.handleFilterUser.bind(this);
    this.handleFilterStartDate = this.handleFilterStartDate.bind(this);
    this.handleFilterEndDate = this.handleFilterEndDate.bind(this);
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

  handleFilterType(event: string) {
    const type = event;
    const newFilter = this.state.filter;
    if (type === 'allTypes') {
      newFilter.entityType = '';
      const typeTranslation = T.translate('user.types.allTypes').toString();
      this.setState( {filter: newFilter, typeDisplay: typeTranslation} );
    } else {
      newFilter.entityType = type;
      const typeTranslation = T.translate(`user.types.${type}`).toString();
      this.setState( { filter: newFilter, typeDisplay: typeTranslation } );
    }
    this.props.onFilter(newFilter);
  }

  handleFilterMethod(event: string) {
    const method = event;
    const newFilter = this.state.filter;
    if (method === 'allMethods') {
      newFilter.method = '';
      const typeTranslation = T.translate('user.methods.allMethods').toString();
      this.setState( {filter: newFilter, methodDisplay: typeTranslation} );
    } else {
      newFilter.method = method;
      const typeTranslation = T.translate(`user.methods.${method}`).toString();
      this.setState( { filter: newFilter, methodDisplay: typeTranslation } );
    }
    this.props.onFilter(newFilter);
  }

  handleFilterUser(selected: string[]) {
    const newFilter = this.state.filter;
    newFilter.user = selected[0].split(':')[0];
    this.setState({ filter: newFilter });
    this.props.onFilter( newFilter );
  }

  handleFilterStartDate(event: any) {
    const newFilter = this.state.filter;
    if (event) {
      newFilter.after = event.split('Z')[0];
    } else {
      newFilter.after = "";
    }
    this.setState({ filter: newFilter });
    this.props.onFilter(newFilter);
  }

  handleFilterEndDate(event: any) {
    const newFilter = this.state.filter;
    if (event) {
      newFilter.before = event.split('Z')[0];
    } else {
      newFilter.before = "";
    }
    this.setState({ filter: newFilter });
    this.props.onFilter(newFilter);
  }

  handleReset() {
    const newFilter: UserFilterData = { after: '', before: '', method: '', type: '', user: '' };
    this.setState({ filter: newFilter, typeDisplay: 'All types', methodDisplay: 'All methods' });
    this.props.onFilter(newFilter);
  }

  handleHide() {
    this.setState({ hidden: true });
  }

  handleShow() {
    this.setState({ hidden: false });
  }

  containsUser(users: string[], id: number){
    for (const user in users) {
      if (parseInt(user.split(':')[0],10) === id) {
        return true;
      }
    }
    return false;
  }

  setTypeaheadOptions(users: UserData[]) {
    const newUserData: string[] = [];

    users.map((user: UserData) => {
      if (user.user !== null && user.user !== undefined && user.user !== -1) {
        if (!this.containsUser(newUserData, user.user)) {
          newUserData.push(`${user.user}: ${this.props.getUser([], user.user, true)}`);
        }
      }
    });

    this.setState({ userData: newUserData });
  }

  render() {
    const { filter, typeDisplay, userData, methodDisplay } = this.state;

    if (this.state.hidden || this.props.users === []) {
      return(
        <HiddenFilter onReset={ this.handleReset } onShow={ this.handleShow }/>
      );
    } else {
      return(
        <UserFilterLayout
          filter={ filter }
          typeDisplay={ typeDisplay }
          userData={ userData }
          methodDisplay={ methodDisplay }
          onFilterType={ this.handleFilterType }
          onFilterMethod={ this.handleFilterMethod }
          onFilterStartDate={ this.handleFilterStartDate }
          onFilterEndDate={ this.handleFilterEndDate }
          onFilterUser={ this.handleFilterUser }
          onReset={ this.handleReset }
          onHide={ this.handleHide }
        />
      );
    }
  }
}

export default UserFilter;
