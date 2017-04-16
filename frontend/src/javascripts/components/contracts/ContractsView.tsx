import React from 'react';
import { Link } from 'react-router';
import Card   from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';
import Listing from '../app/Listing.tsx';
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
    <Listing onSelect={props.onContractSelect} addNewRoute='/contracts/new' fetchModels={props.fetchUsers} modelName='contract' columns={['id','type','vehicle']}/>
	);

}

export default ContractsView;
