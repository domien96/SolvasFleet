import React from 'react';
import { browserHistory, Link } from'react-router';

import Card       from '../app/Card.tsx';
import { InfoTable, th } from '../tables/InfoTable.tsx';
import SubFleets from '../subfleets/SubFleets.tsx'

import fetchFleets from '../../actions/fetch_fleets.ts';

interface OverviewProps {
  fleets: Fleet[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(id : number) {
    browserHistory.push('fleets/' + id + '/subfleets');
  }

  render() {
    const tableHead = [
      th('id',   'fleet.id'),
      th('name', 'fleet.name'),
      th('size', 'fleet.size')
    ];

    return (
      <InfoTable head={ tableHead } data={ this.props.fleets } onClick={ this.handleClick } />
    );
  }
}

class Fleets extends React.Component<Fleets.Props, Fleets.State> {

  constructor(props : {id : number}) {
    super(props);
    this.state = { fleets: [] };
  }

  componentDidMount() {
    this.fetchFleets(this.props.id);
  }

  fetchFleets(id : number) {
    fetchFleets(id)
      .then((data : Fleets.Data) => {
        this.setState({ fleets: data.fleets })
      });
  }

  render() {
    
    return (
      <div>
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-6'>
              <Card>
                <div className='card-title'>
                  <h2>Fleets
                    <Link to='/fleets/new' className='btn btn-default pull-right'>
                      <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
                      Add new fleet
                    </Link>
                  </h2>
                </div>
                <div className='card-content'>
                  <Overview fleets={ this.state.fleets } />
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-6'>
              
                <SubFleets id={this.props.id} />
              
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Fleets;
