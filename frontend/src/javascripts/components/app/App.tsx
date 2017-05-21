import React from 'react';

import Sidebar from './Sidebar.tsx';
import Home from '../Home.tsx';

interface Props {
  location: any;
}

class App extends React.Component<Props, {}> {
  constructor() {
    super();
    this.updateLanguage = this.updateLanguage.bind(this);
  }

  static childContextTypes = {
    location: React.PropTypes.object,
  };

  getChildContext() {
    return {
      location: this.props.location,
    };
  }

  updateLanguage() {
    this.forceUpdate();
  }

  render() {
    return (
      <div className='all-wrapper'>
        <div className='sidebar-wrapper'>
          <Sidebar updateLanguage={ this.updateLanguage } />
          <main className='page-wrapper'>
            { this.props.children || <Home /> }
          </main>
        </div>
      </div>
    );
  }
}

export default App;
