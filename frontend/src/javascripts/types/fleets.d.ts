namespace Fleets {
  export interface Props {
    fleets : Fleet[];
    company : number;
  }

  export interface State {
    formVisible : boolean;
    fleet : Fleet;
    errors : Form.Error[];
  }

  export interface Data {
    [ fleets : string ] : any;
  }
}
