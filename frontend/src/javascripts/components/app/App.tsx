import React    from 'react';
import Sidebar  from './Sidebar.tsx'
import MobileSidebar  from './MobileSidebar.tsx'

interface Props {
  location: any;
}

interface State {
  width: number;
  height: number;
}

class App extends React.Component<Props, State> {

  constructor(props : any){
    super(props);
    this.state = {width: 0, height: 0};
    this.updateWindowDimensions = this.updateWindowDimensions.bind(this);
  }

  componentDidMount(){
    this.updateWindowDimensions();
    window.addEventListener('resize', this.updateWindowDimensions.bind(this));
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.updateWindowDimensions.bind(this));
  }

  updateWindowDimensions() {
    this.setState({ width: window.innerWidth, height: window.innerHeight });
  }

  static childContextTypes = {
    location: React.PropTypes.object
  }

  getChildContext() {
    return {
      location: this.props.location
    }
  }

  render() {
    let sidebar;
    if(this.state.width > 1000){
      sidebar = <Sidebar />
    }
    else{
      sidebar = <MobileSidebar />
    }
    return (
      <div id='outer-container'>
        {sidebar}
        <main id='page-wrap' className='page-wrapper'>
          { this.props.children }
        </main>
      </div>
    );
  }
}

export default App;
