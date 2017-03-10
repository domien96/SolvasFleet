namespace Company {
  export interface Props { 
    [ params : string ] : { [ id : string ] : number };
  }

  export interface State {
    company : Company;
  }

  namespace New {
    export interface Props { }

    export interface State {
      errors : Form.Error[];
      company   : Company;
    }
  }
}
interface Company {
  name?        : string;
  vat_number?   : string;
  phone_number? : string;
  address?     : string;
}
