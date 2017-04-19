import React from 'react';
import { Link } from 'react-router';

import SidebarLink from './SidebarLink.tsx';
import LanguageSwitcher from './LanguageSwitcher.tsx';

const Sidebar : React.StatelessComponent<{}> = () => {
  return (
    <nav className='navbar-default navbar-side'>
      <div id='logo'>
        <Link to='/'>
          <h2>SolvasFleet</h2>
        </Link>
      </div>
      <ul className='nav'>
        <SidebarLink path='/users'>Users</SidebarLink>
        <SidebarLink path='/clients'>Clients</SidebarLink>
        <SidebarLink path='/vehicles'>Vehicles</SidebarLink>
        <li>
          <Link to='/sign_out'>
            Sign out
          </Link>
        </li>
      </ul>
      <LanguageSwitcher />
    </nav>
  );
}

export default Sidebar;
