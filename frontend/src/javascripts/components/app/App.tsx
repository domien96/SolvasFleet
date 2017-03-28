import React    from 'react';

import Sidebar from './Sidebar.tsx';

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
