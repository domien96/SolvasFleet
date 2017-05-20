namespace Company {
  export type Field =
    'id' | 'name' | 'vatNumber' | 'type' | 'phoneNumber' | 'city' | 'country' | 'houseNumber' | 'postalCode' | 'street';
}

interface CompanyData {
  id?: number;
  name?: string;
  vatNumber?: string;
  phoneNumber?: string;
  type?: string;
  [address: string ]: { [ city: string ]: string ,
                        [ country: string ]: string,
                        [ street: string ]: string,
                        [ postalCode: string ]: string,
                        [ houseNumber: string ]: string };
  [key: string]: string;
}

interface OptionData {
  id : number;
  label : string;
}

interface CompanyFilterData {
  [name: string]: string;
  [vatNumber: string]: string;
  [country: string]: string;
  [type: string]: string;
  [archived: string]: string;
}