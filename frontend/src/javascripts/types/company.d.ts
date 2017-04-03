namespace Company {
  export type Field =
    'id' | 'name' | 'vatNumber' | 'phoneNumber' | 'city' | 'country' | 'houseNumber' | 'postalCode' | 'street';
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

