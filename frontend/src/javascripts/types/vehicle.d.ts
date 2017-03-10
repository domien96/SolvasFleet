namespace Vehicle {
  export interface Props { }

  export interface State {
    vehicle : Vehicle;
  }

  export type Field =
    'id' | 'licencePlate' | 'chassisNumber' | 'brand'
      | 'model' | 'type' | 'kmCount' | 'year'
      | 'leasingCompany' | 'value' | 'company';


  namespace New {
    export interface Props { }

    export interface State {
      errors  : Form.Error[];
      vehicle : Vehicle;
    }
  }
}

interface Vehicle {
  id             : number;
  licencePlate   : string;
  chassisNumber  : string; //VIN: vehicle identification number
  brand          : string;
  model          : string;
  type           : string;
  kmCount        : number;
  year           : number;
  leasingCompany : string;
  value          : number;
  company        : string
}
