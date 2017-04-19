import React    from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';
import { slide as Menu } from 'react-burger-menu';

interface SProps {
  path: string;
}

class SidebarLink extends React.Component<SProps, {}> {
  static contextTypes = {
    location: React.PropTypes.object
  }

  render() {
    const classes = classNames({ active: this.context.location.pathname.includes(this.props.path) });

    return (
      <li className={ classes } >
        <Link to={ this.props.path }>
          { this.props.children }
        </Link>
      </li>
    )
  }
}

const Sidebar : React.StatelessComponent<{}> = () => {

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
      </ul>
    </div> 
  );

  return (
    <div>
      <Menu pageWrapId={'page-wrap'} outerContainerId={'outer-container'} className='mobile-menu'>
        {info}
      </Menu>
      <nav className='standard-menu'>
        {info}
      </nav>
    </div>
  );
}

export default Sidebar;