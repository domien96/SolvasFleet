import React    from 'react';
import Sidebar  from './Sidebar.tsx'

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
      <div id='outer-container' className='sidebar-wrapper'>
        <Sidebar />
        <main id='page-wrap' className='page-wrapper'>
          { this.props.children }
        </main>
      </div>
    );
  }
}

export default App;
