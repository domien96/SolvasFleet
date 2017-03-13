import React from 'react';
import { browserHistory, Link } from'react-router';

import Card      from '../app/Card.tsx';
import InfoTable from '../tables/InfoTable.tsx';

import fetchSubFleets from '../../actions/fetch_subfleets.ts';
import { th } from '../../utils/utils.ts';

interface OverviewProps {
  subfleets: SubFleet[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(id : number) {
    console.log(id);
    browserHistory.push('/vehicles/');
  }

  render() {
    const tableHead = [
      th('type', 'subfleet.name'),
      th('size', 'subfleet.size')
    ];

    return (
      <InfoTable head={ tableHead } data={ this.props.subfleets } onClick={ this.handleClick } />
    );
  }
}

class SubFleets extends React.Component<SubFleets.Props, SubFleets.State> {

  constructor(props : {id : number}) {
    super(props);
    this.state = { subfleets: [] };
  }

  componentDidMount() {
    this.fetchSubFleets(this.props.id);
  }

  fetchSubFleets(id : number) {
    fetchSubFleets(id, 'persoonswagen') // TODO
      .then((data : SubFleets.Data) => {
        this.setState({ subfleets: data.subfleets })
      });
  }

  render() {

    return (
      <div>
        <div className='col-xs-12 col-md-12'>
          <Card>
            <div className='card-title'>
              <h2>SubFleets
                <Link to='/subfleets/new' className='btn btn-default pull-right'>
                  <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
                  Add new subfleet
                </Link>
              </h2>
            </div>
            <div className='card-content'>
              <Overview subfleets={ this.state.subfleets } />
            </div>
          </Card>
        </div>
      </div>
    );
  }
}

export default SubFleets;
