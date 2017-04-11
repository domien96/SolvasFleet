import React from 'react';
import { Link } from 'react-router';
import Card   from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';

export interface OverviewProps {
  contracts : ContractData[];
  onContractSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<OverviewProps> = props => {
	const tableHead = [
    th('id',        'contract.id'),
    th('type', 			'contract.type'),
		th('vehicle', 	'contract.vehicle'),
	 ];

  return (
    <InfoTable head={ tableHead } data={ props.contracts } onClick={ props.onContractSelect } />
  );
}

export interface Props {
	contracts: ContractData[];
	onContractSelect : (id : number) => void;
}

const ContractsView : React.StatelessComponent<Props> = props => {
	return(
		<div>
			<Card>
        <div className='card-title'>
        	<h2> 
        		Contracts: 
	        	<Link to={ '/contracts/new' } className='btn btn-default pull-right'>
			        <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new contract
			      </Link>
			     </h2>
        </div>
        <div className='card-content'>
					<Overview contracts={ props.contracts } onContractSelect={ props.onContractSelect }/>
				</div>
			</Card>	
		</div>
	);

}

export default ContractsView;