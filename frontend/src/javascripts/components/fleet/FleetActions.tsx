import React from 'react';
import { Link } from 'react-router';
import Card from '../app/Card.tsx';
import T from 'i18n-react';


const FleetActions = () => {

	return(
	  <Card>
	    <div className='card-content no-border'>
	      <div className='row actions'>
	        <div className='col-sm-6'>
	          <Link to={ '/vehicles/new' } className='btn btn-default form-control'>
	            <span className='glyphicon glyphicon glyphicon-file' /> { T.translate('vehicle.addNew') }
	          </Link>
	        </div>
	      </div>
	    </div>
	  </Card>
  );
}

export default FleetActions;