import React    from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';

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
      </ul>
    </nav>
  );
}

interface Props {
  location: any;
}

class App extends React.Component<Props, {}> {
  static childContextTypes = {
    location: React.PropTypes.object
  }

  getChildContext() {
    return {
      location: this.props.location
    }
  }

  render() {
    return (
      <div id='wrapper' className='all-wrapper'>
        <Sidebar />
        <div className='page-wrapper'>
          { this.props.children }
        </div>
      </div>
    );
  }
}

export default App;
