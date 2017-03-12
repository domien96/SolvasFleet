namespace Vehicle {
  export interface Props { 
    [ params : string ] : { [ id : string ] : number };
    fetchVehicles : () => void;
  }

  export interface State {
    vehicle : Vehicle;
  }

  export type Field =
    'id' | 'licensePlate' | 'chassisNumber' | 'brand'
      | 'model' | 'type' | 'kilometerCount' | 'year'
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
  id?             : number;
  licensePlate?   : string;
  chassisNumber?  : string; //VIN: vehicle identification number
  brand?          : string;
  model?          : string;
  type?           : string;
  kilometerCount?        : number;
  year?           : number;
  leasingCompany? : string;
  value?          : number;
  company?        : string
  [key : string]: string; 
}
