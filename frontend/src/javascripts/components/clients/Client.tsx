import React from 'react';
import { browserHistory, Link } from'react-router';

import fetchClient from '../../actions/fetch_company.ts';
import deleteClient from '../../actions/delete_company.ts';
import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import { DetailTable, th } from '../tables/DetailTable.tsx';


import Fleets from '../fleets/Fleets.tsx'

class Client extends React.Component<Company.Props, Company.State> {

  constructor() {
    super();
    this.state = { company : {} };
    this.deleteClient = this.deleteClient.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id)
      .then((data : any) => {
        this.setState({ company: data })
      });
  }

  public deleteClient(){
    deleteClient(this.props.params.id).then(function(this: any) {
      browserHistory.push('/clients');
    });
  }

  render() {
    var { name, vatNumber, phoneNumber, address } = this.state.company;
    var id = this.props.params.id;
    console.log(id)

    const data = [
      th('company.vatNumber', vatNumber),
      th('company.phoneNumber', phoneNumber),
      th('company.address', address)
    ];

    return (
      <div>
        <Header>
          <h2>{ name }</h2>  
        </Header>
        <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-12'>
            <Card>
              <div className='card-content'>
                <div className='col-sm-4'>
                  <div className='row actions'>
                    <div className='col-sm-6'>
                      <Link to={ '/clients/' + id + '/edit' } className='btn btn-default form-control'>
                        <span className='glyphicon glyphicon-edit' /> Edit
                      </Link>
                    </div>
                    <div className='col-sm-6'>
                      <button onClick = { this.deleteClient } className='btn btn-danger form-control'>
                        <span className='glyphicon glyphicon-remove' />
                        Delete
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </Card>
            <Card>
              <div className='col-sm-6'>
                <div className='card-content'>
                  <DetailTable data={ data }/>
                </div>
              </div>
            </Card>
          </div>   
            <div>
              <Fleets id={ id } />
            </div>
          </div>
        </div>
      </div>

    );
  }
}

export default Client;
