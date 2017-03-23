namespace Companies {
  export interface Props { }

  export interface State {
    clients : Company[];
  }

  export interface Data {
    [ companies : string ] : any;
  }
}
