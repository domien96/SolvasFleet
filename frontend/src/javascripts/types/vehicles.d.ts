namespace Vehicles {
  export interface Props { }

  export interface State {
    vehicles : Vehicle[];
    filter: VehicleFilter;
  }

  export interface Data {
    [ vehicles : string ] : any;
  }
}

interface VehicleFilter{
	fleet : string;
	type : string;
}
