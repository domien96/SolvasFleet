namespace SubFleets {
  export interface Props {
   id : number ;
  }

  export interface State {
    subfleets : SubFleet[];
  }

  export interface Data {
    [ subfleets : string ] : any;
  }
}
