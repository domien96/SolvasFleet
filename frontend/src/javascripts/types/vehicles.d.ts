namespace Vehicles {
  export interface Props { }

  export interface State {
    vehicles : Vehicle[];
  }

  export interface Data {
    [ vehicles : string ] : any;
  }
}
