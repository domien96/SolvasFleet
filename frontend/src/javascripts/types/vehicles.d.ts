namespace Vehicles {
  export interface Props { }

  export interface State {
    vehicles : Vehicle[];
    filter: VehicleFilterData;
  }

  export interface Data {
    [ vehicles : string ] : any;
  }
}

interface VehicleFilterData{
	[fleet:string] : string;
	[type:string] : string;
}
