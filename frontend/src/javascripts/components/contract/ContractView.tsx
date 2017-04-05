import React from 'react';

interface Props {
	contract : ContractData
}

const ContractView : React.StatelessComponent<Props> = props => {
	var { franchise, id, insuranceCompany, premium, type, vehicle, startDate, endDate } = props.contract;
	console.log(franchise)
	return(
		<div>
			<div>Franchise : { franchise }</div>
			<div>id : { id }</div>
			<div>insuranceCompany : { insuranceCompany }</div>
			<div>premium : { premium }</div>
			<div>type : { type }</div>
			<div>vehicle : { vehicle }</div>
			<div>startDate : { startDate }</div>
			<div>endDate : { endDate }</div>
		</div>
	);
}

export default ContractView;