import React from 'react';
import { Link } from 'react-router';

export interface Props {
	contracts: ContractData[];
	onContractSelect : (event : any) => void;
}

const ContractsView : React.StatelessComponent<Props> = props => {

	const contractList = props.contracts.map((contract : ContractData, i : number) =>{
		var { franchise, id, insuranceCompany, premium, type, vehicle, startDate, endDate } = contract;
		return(
			<div key={ i }>
				<div>Franchise : { franchise }</div>
				<div>id : { id }</div>
				<div>insuranceCompany : { insuranceCompany }</div>
				<div>premium : { premium }</div>
				<div>type : { type }</div>
				<div>vehicle : { vehicle }</div>
				<div>startDate : { startDate }</div>
				<div>endDate : { endDate }</div>
				<button value={id} onClick={ props.onContractSelect }>Select Contract</button>
			</div>
		);
	})

	return(
		<div>
			<h2> Contracts: </h2>
		  <Link to='/contracts/new' className='btn btn-default pull-right'>
        <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new contract
      </Link>
			{ contractList }
		</div>
	);

}

export default ContractsView;