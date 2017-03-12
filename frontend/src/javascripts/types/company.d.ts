namespace Company {
  export interface Props { 
    [ params : string ] : { [ id : string ] : number };
  }

  export interface State {
    company : Company;
  }

  export type Field =
    'id' | 'name' | 'vatNumber' | 'phoneNumber' | 'address';

  namespace New {
    export interface Props { }

    export interface State {
      errors : Form.Error[];
      company   : Company;
    }
  }
}

interface Company {
  id?           : number
  name?        : string;
  vatNumber?   : string;
  phoneNumber? : string;
  address?     : string;
  [key : string]: string; 
}

