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
  return (
    <Menu className='nav'>
      <div id='logo'>
        <Link to='/'>
          <h2>SolvasFleet</h2>
        </Link>
      </div>
      <div><SidebarLink path='/users'>Users</SidebarLink></div>
      <div><SidebarLink path='/clients'>Clients</SidebarLink></div>
      <div><SidebarLink path='/vehicles'>Vehicles</SidebarLink></div>
    </Menu>
  );
}

export default Sidebar;