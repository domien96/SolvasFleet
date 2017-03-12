namespace Companies {
  export interface Props { }

  export interface State {
    companies : Company[];
  }

  export interface Data {
    [ companies : string ] : any;
  }
}
