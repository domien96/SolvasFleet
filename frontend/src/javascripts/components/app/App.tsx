import React    from 'react';
import { Link } from 'react-router';

import Header from './Header.tsx';

class App extends React.Component<{}, {}> {
  render() {
    return (
      <div id='wrapper'>
        <Header />
        <div>Dit is Solvas Fleet</div>
        <div>
          <Link to='/'>Root</Link>
          <Link to='/companies'>Companies</Link>
          <Link to='/blargh'>Dit bestaat niet</Link>
        </div>
        { this.props.children }
      </div>
    );
  }
}

export default App;
