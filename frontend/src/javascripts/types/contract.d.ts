namespace Contract {
  export type Field =
    'franchise' | 'insuranceCompany' | 'premium' | 'type' | 'vehicle'| 'startDate' | 'endDate';
}

interface ContractsData {
  [ contracts : string ] : any;
}

interface ContractData{
	franchise? 				: number;
	id? 							: number;
	insuranceCompany? : number;
	premium? 					: number;
	type? 						: string;
	vehicle? 					: number;
	startDate? : Date;
	endDate? : Date;
}