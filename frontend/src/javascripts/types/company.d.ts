namespace Company {
  export interface Props { 
    [ params : string ] : { [ id : string ] : number };
  }

  export interface State {
    company : Company;
  }

  export type Field =
    'id' | 'name' | 'vatNumber' | 'phoneNumber' | 'city' | 'country' | 'houseNumber' | 'postalCode' | 'street';

  namespace CForm {
    export interface Props {
      onSubmit     : (e : any) => void;
      handleChange : (field : Company.Field, isAddress : boolean, e : any) => void;
      errors       : Form.Error[];
      hasError     : (field : Company.Field) => boolean;
      company      : Company;
    }

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
  [address : string ] : { [ city : string ] : string ,
                          [ country : string ] : string,
                          [ street : string ] : string,
                          [ postalCode : string ] : string,
                          [ houseNumber : string ] : string  }
  [key : string]: string; 
}

