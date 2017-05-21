import React from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';
import { slide as Menu } from 'react-burger-menu';
import LanguageSwitcher from './LanguageSwitcher.tsx';
import { fetchUser } from '../../actions/user_actions.ts';
import T from 'i18n-react';
import DynamicGuiComponent from '../app/DynamicGuiComponent.tsx';

import Auth from '../../modules/Auth.ts';

interface SProps {
  path: string;
  authorized: boolean;
}

class SidebarLink extends React.Component<SProps, {}> {
  static contextTypes = {
    location: React.PropTypes.object,
  };

  render() {
    const classes = classNames({
      active: this.context.location.pathname.startsWith(this.props.path)
    });

    return (
      <DynamicGuiComponent authorized={ this.props.authorized }>
        <li className={ classes } >
          <Link to={ this.props.path }>
            { this.props.children }
          </Link>
        </li>
      </DynamicGuiComponent>
    );
  }
}

interface Props {
  updateLanguage: () => void;
}

interface State {
  email: string;
}



class Sidebar extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { email: '' };
  }

  componentDidMount() {
    this.fetchUserEmail(parseInt(Auth.getLocalSub(),10));
  }

  fetchUserEmail(id: number) {
    fetchUser(id, ((data: any) => {
      if (data) {
        this.setState({ email: data.email });
      }
    }));
  }

  render() {
    const info = (
      <div>
        <div id='logo'>
          <Link to='/'>
            <h2>SolvasFleet</h2>
          </Link>
        </div>
        <ul className='nav'>
          <SidebarLink path='/users' authorized={ Auth.canReadUsers() }>{ T.translate('user.users') }</SidebarLink>
          <SidebarLink path='/clients' authorized={ Auth.canReadCompany(-1) }>{ T.translate('company.clients') }</SidebarLink>
          <SidebarLink path='/vehicles' authorized= {  Auth.canReadCompany(-1)}>{ T.translate('vehicle.vehicles') }</SidebarLink>
          <SidebarLink path='/log' authorized={ Auth.canReadRevisions() }>{ T.translate('log.log') }</SidebarLink>
          <SidebarLink path='/auth' authorized={ true }>{ T.translate('auth.permissionSettings') }</SidebarLink>
          <SidebarLink path='/commissions' authorized={Auth.canWriteFleetsOfCompany(-1)}>{ T.translate('commissions.commissions') }</SidebarLink>
        </ul>
        <ul className='nav session-actions'>
          <li className='plain'>
            { T.translate('app.signedInStatus') } <p>{ this.state.email }</p>
          </li>
          <li>
            <Link to='/sign_out'>
              { T.translate('app.signOut') }
            </Link>
          </li>
        </ul>
        <LanguageSwitcher updateLanguage={ this.props.updateLanguage } />
      </div>
    );

    return (
      <div>
        <Menu className='mobile-menu'>
          { info }
        </Menu>
        <nav className='standard-menu'>
          { info }
        </nav>
      </div>
    );
  }
}

export default Sidebar;
