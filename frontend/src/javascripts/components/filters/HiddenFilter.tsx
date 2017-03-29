import React from 'react';
import Card      from '../app/Card.tsx';

interface Props {
  onReset : () => void;
  onShow : () => void;
}

const HiddenFilter : React.StatelessComponent<Props> = props => {

	return(
		<div>
			<Card>
				<div className='card-title'>
					<div className='col-sm-6'>
					<h3>Filter</h3>
					</div>
					<div className='col-sm-6'>
						<button className='btn btn-default pull-right' onClick={ props.onReset }>Reset filters</button>
						<button className='btn btn-default pull-right' onClick={ props.onShow }>Show</button>
      		</div>
      		<div className="clearfix" />
				</div>
			</Card>
		</div>
	);
}

export default HiddenFilter;