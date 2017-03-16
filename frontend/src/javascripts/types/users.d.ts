namespace Users {
  export interface Props { }

  export interface State {
    users : User[];
  }

  export interface Data {
    [ users : string ] : any;
  }
}
