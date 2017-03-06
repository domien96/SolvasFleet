import React    from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';

class SideBarLink extends React.Component<SideBarLinkProps, {}> {
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
class Header extends React.Component<{}, {}> {
  render() {
    return (
      <nav className='navbar-default navbar-side'>
        <ul className='nav'>
          <SideBarLink path='/'>Home</SideBarLink>
          <SideBarLink path='/companies' active={ true }>Companies</SideBarLink>
        </ul>
      </nav>
    );
  }
}

class App extends React.Component<{}, {}> {
  render() {
    return (
      <div id='wrapper'>
        <Header />
        <div className='page-wrapper'>
          { this.props.children }
        </div>
      </div>
    );
  }
}

export default App;
