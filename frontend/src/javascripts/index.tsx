import React from 'react';
import { render } from 'react-dom';

import AwesomeComponent from './components/AwesomeComponent.tsx';

class App extends React.Component<{}, {}> {
  render () {
    return (
      <div>
        <h3>Solvas Flaat</h3>
        <p>Hello Jij!</p>
        <AwesomeComponent />
      </div>
    );
  }
}

render(<App/>, document.getElementById('app'));
