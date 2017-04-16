import React    from 'react';
import Sidebar  from './Sidebar.tsx'
import MobileSidebar  from './MobileSidebar.tsx'
//import MOBILE_WIDTH from '../../constants/constants.ts'

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
    let classnamesInner;
    let classnamesOuter;
    if(this.state.width > 1000){ //TODO change to MOBILE_WIDTH
      sidebar = <Sidebar />
      classnamesInner = 'page-wrapper page-wrapper-normal';
      classnamesOuter = '';
    }
    else{
      sidebar = <MobileSidebar />
      classnamesInner = 'page-wrapper page-wrapper-mobile';
      classnamesOuter = 'sidebar-wrapper-mobile';
    }
    return (
      <div id='outer-container' className={classnamesOuter}>
        {sidebar}
        <main id='page-wrap' className={classnamesInner}>
          { this.props.children }
        </main>
      </div>
    );
  }
}

export default App;
