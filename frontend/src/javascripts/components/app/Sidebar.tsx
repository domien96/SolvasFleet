import React from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';
import { slide as Menu } from 'react-burger-menu';
import LanguageSwitcher from './LanguageSwitcher.tsx';
import T from 'i18n-react';
import _ from 'lodash';
import { parseClaims } from '../../modules/Auth.ts';


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
    const classes = classNames({ active: this.context.location.pathname.includes(this.props.path) });

    const comp = (
      <li className={ classes } >
        <Link to={ this.props.path }>
          { this.props.children }
        </Link>
      </li>
    );

    return this.props.authorized ? comp : null;
  }
}

const Sidebar: React.StatelessComponent<{}> = () => {

  const info = (
    <div>
      <div id='logo'>
        <Link to='/'>
          <h2>SolvasFleet</h2>
        </Link>
      </div>
      <ul className='nav'>
        <SidebarLink path='/users' authorized={ Auth.canReadUsers() } >{ T.translate('user.users') }</SidebarLink>
        <SidebarLink path='/clients' authorized={ Auth.canClickCompaniesLink() }>{ T.translate('company.clients') }</SidebarLink>
        <SidebarLink path='/vehicles' authorized={ Auth.canClickVehiclesLink() }>{ T.translate('vehicle.vehicles') }</SidebarLink>
        <SidebarLink path='/log' authorized={ Auth.canReadRevisions() }>{ T.translate('log.log') }</SidebarLink>
        <SidebarLink path='/auth' authorized={ Auth.canReadRoles() }>{ T.translate('auth.permissionSettings') }</SidebarLink>
      </ul>
      <ul className='nav session-actions'>
        <li className='plain'>
          { T.translate('app.signedInStatus') } <p>{ Auth.getLocalSub() }</p>
        </li>
        <li>
          <Link to='/sign_out'>
            { T.translate('app.signOut') }
          </Link>
        </li>
      </ul>
      <LanguageSwitcher />
    </div>
  );

  return (
    <div>
      <Menu className='mobile-menu'>
        {info}
      </Menu>
      <nav className='standard-menu'>
        {info}
      </nav>
    </div>
  );
};

export default Sidebar;
