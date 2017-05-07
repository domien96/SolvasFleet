import React from 'react';
import { Link } from 'react-router';
import Card from '../app/Card.tsx';
import T from 'i18n-react';

interface Props {
  fleet : number;
}

const InvoiceActions : React.StatelessComponent<Props> = props => {

	var { fleet } = props;

	return(
	  <Card>
	    <div className='card-content'>
	      <div className='row actions'>
	        <div className='col-sm-6'>
	          <Link to={ '/fleets/' + fleet + '/invoices/current' } className='btn btn-default form-control'>
	            <span className='glyphicon glyphicon glyphicon-file' /> { T.translate('invoice.showCurrent') }
	          </Link>
	        </div>
	        <div className='col-sm-6'>
	          <Link to={ '/fleets/' + fleet + '/invoices' } className='btn btn-default form-control'>
	            <span className='glyphicon glyphicon glyphicon-folder-open' /> { T.translate('invoice.showAll') }
	          </Link>
	        </div>
	      </div>
	    </div>
	  </Card>
  );
}

export default InvoiceActions;