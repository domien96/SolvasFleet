import React from 'react';
import { Link } from 'react-router';
//import Auth from '../../modules/Auth.ts';
//import { parseClaims } from '../../modules/Auth.ts';

import SidebarLink from './SidebarLink.tsx';

const Sidebar : React.StatelessComponent<{}> = () => {

  //Auth.getAccessToken().then((value) => { console.log(parseClaims(value.toString())) });

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
        <SidebarLink path='/auth'>Permission Settings</SidebarLink>
        <li>
          <Link to='/sign_out'>
            Sign out
          </Link>
        </li>
      </ul>
    </nav>
  );
}

export default Sidebar;
