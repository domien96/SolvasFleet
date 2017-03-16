namespace Vehicles {
  export interface Props { }

  export interface State {
    vehicles : Vehicle[];
    type : string;
    fleet : string;
  }

  export interface Data {
    [ vehicles : string ] : any;
  }
}
