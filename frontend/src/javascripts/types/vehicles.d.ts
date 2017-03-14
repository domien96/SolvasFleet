namespace Vehicles {
  export interface Props { }

  export interface State {
    vehicles : Vehicle[];
    type : string;
    fleet : number;
  }

  export interface Data {
    [ vehicles : string ] : any;
  }
}
