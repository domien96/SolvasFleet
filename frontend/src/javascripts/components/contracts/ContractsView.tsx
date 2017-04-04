import React from 'react';

export interface Props {
	contracts: ContractData[];
	onContractSelect : (event : any) => void;
}

const ContractsView : React.StatelessComponent<Props> = props => {

	const contractList = props.contracts.map((contract : ContractData, i : number) =>{
		var { franchise, id, insuranceCompany, premium, type, vehicle } = contract;
		return(
			<div key={ i }>
				<div>Franchise : { franchise }</div>
				<div>id : { id }</div>
				<div>insuranceCompany : { insuranceCompany }</div>
				<div>premium : { premium }</div>
				<div>type : { type }</div>
				<div>vehicle : { vehicle }</div>
				<button value={id} onClick={ props.onContractSelect }>Select Contract</button>
			</div>
		);
	})

	return(

		<div>
		<h2> Contracts: </h2>
		{ contractList }
		</div>
	);

}

export default ContractsView;