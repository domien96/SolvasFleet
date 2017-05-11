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
