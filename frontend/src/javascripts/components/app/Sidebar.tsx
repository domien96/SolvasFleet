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
    const classes = classNames({ active: this.context.location.pathname.includes(this.props.path) });

    return (
      <li className={ classes } >
        <Link to={ this.props.path }>
          { this.props.children }
        </Link>
      </li>
    );
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
        <SidebarLink path='/users'>Users</SidebarLink>
        <SidebarLink path='/clients'>Clients</SidebarLink>
        <SidebarLink path='/vehicles'>Vehicles</SidebarLink>
        <SidebarLink path='/auth'>Permission Settings</SidebarLink>
      </ul>
      <ul className='nav session-actions'>
        <li className='plain'>
          Signed in as <p>{ Auth.getLocalSub() }</p>
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
