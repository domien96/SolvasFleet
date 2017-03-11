namespace Company {
  export interface Props { 
    [ params : string ] : { [ id : string ] : number };
  }

  export interface State {
    company : Company;
  }

  export type Field =
    'id' | 'name' | 'vat_number' | 'phone_number' | 'address';

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
  vat_number?   : string;
  phone_number? : string;
  address?     : string;
  [key : string]: string; 
}

