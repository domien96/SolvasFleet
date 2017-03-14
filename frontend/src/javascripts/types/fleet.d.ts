namespace Fleet {
  export interface Props {
   [ params : string ] : { [ id : string ] : number };
  }

  export interface State {
    fleet : Fleet;
  }

  export type Field =
    'id' | 'name' | 'company';

  namespace Form {
    export interface Props { }

    export interface State {
      errors : Form.Error[];
      fleet   : Fleet;
    }
  }
}

interface Fleet {
  id?         : number;
  name?       : string;
  size?       : number;
  company?    : number;
}

