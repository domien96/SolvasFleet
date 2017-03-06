import React    from 'react';
import { Link } from 'react-router';

class Header extends React.Component<{}, {}> {
  render() {
    return (
      <nav className='navbar-default navbar-side'>
        <ul className='nav'>
          <li>
            <Link to='/' >Home</Link>
          </li>
          <li className='active'>
            <Link to='/companies' >Companies</Link>
          </li>
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
