namespace Company {
  export interface Props { }

  export interface State {
    company : Company;
  }
}

interface Company {
  name        : string;
  vatNumber   : string;
  phoneNumber : string;
  address     : string;
}
