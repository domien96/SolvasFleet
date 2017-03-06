import React    from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';

class SidebarLink extends React.Component<SidebarLinkProps, {}> {
  static contextTypes = {
    location: React.PropTypes.object
  }

  render() {
    const classes = classNames({ active: this.context.location.pathname == this.props.path });

    return (
      <li className={ classes } >
        <Link to={ this.props.path }>
          { this.props.children }
        </Link>
      </li>
    )
  }
}

class Sidebar extends React.Component<{}, {}> {
  render() {
    return (
      <nav className='navbar-default navbar-side'>
        <ul className='nav'>
          <SidebarLink path='/'>Home</SidebarLink>
          <SidebarLink path='/companies'>Companies</SidebarLink>
          <SidebarLink path='/companies/new'>Add Company</SidebarLink>
        </ul>
      </nav>
    );
  }
}

class App extends React.Component<AppProps, {}> {
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
      <div id='wrapper'>
        <Sidebar />
        <div className='page-wrapper'>
          { this.props.children }
        </div>
      </div>
    );
  }
}

export default App;
