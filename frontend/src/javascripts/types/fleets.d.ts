namespace Fleets {
  export interface Props {
   id : number ;
  }

  export interface State {
    fleets : Fleet[];
  }

  export interface Data {
    [ fleets : string ] : any;
  }
}
