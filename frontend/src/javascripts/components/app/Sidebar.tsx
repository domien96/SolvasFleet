import React from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';
import { slide as Menu } from 'react-burger-menu';
import LanguageSwitcher from './LanguageSwitcher.tsx';
import T from 'i18n-react';

import Auth from '../../modules/Auth.ts';

interface SProps {
  path: string;
}

class SidebarLink extends React.Component<SProps, {}> {
  static contextTypes = {
    location: React.PropTypes.object,
  };

  render() {
    const classes = classNames({ active: this.context.location.pathname.startsWith(this.props.path) });

    return (
      <li className={ classes } >
        <Link to={ this.props.path }>
          { this.props.children }
        </Link>
      </li>
    );
  }
}

interface Props {
  updateLanguage: () => void;
}

const Sidebar: React.StatelessComponent<Props> = props => {
  const info = (
    <div>
      <div id='logo'>
        <Link to='/'>
          <h2>SolvasFleet</h2>
        </Link>
      </div>
      <ul className='nav'>
        <SidebarLink path='/users'>{ T.translate('user.users') }</SidebarLink>
        <SidebarLink path='/clients'>{ T.translate('company.clients') }</SidebarLink>
        <SidebarLink path='/vehicles'>{ T.translate('vehicle.vehicles') }</SidebarLink>
        <SidebarLink path='/log'>{ T.translate('log.log') }</SidebarLink>
        <SidebarLink path='/auth'>{ T.translate('auth.permissionSettings') }</SidebarLink>
        <SidebarLink path='/commissions'>Commissions</SidebarLink>
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
      <LanguageSwitcher updateLanguage={ props.updateLanguage } />
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
