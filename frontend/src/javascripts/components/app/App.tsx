import React    from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';

class SidebarLink extends React.Component<SidebarLinkProps, {}> {
  render() {
    const classes = classNames({ active: this.props.active }); 

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
          <SidebarLink path='/companies' active={ true }>Companies</SidebarLink>
          <SidebarLink path='/companies/new'>Add Company</SidebarLink>
        </ul>
      </nav>
    );
  }
}

class App extends React.Component<{}, {}> {
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
