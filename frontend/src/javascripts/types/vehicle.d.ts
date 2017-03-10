namespace Vehicle {
  export interface Props { 
    [ params : string ] : { [ id : string ] : number };
    fetchVehicles : () => void;
  }

  export interface State {
    vehicle : Vehicle;
  }

  export type Field =
    'id' | 'license_plate' | 'chassis_number' | 'brand'
      | 'model' | 'type' | 'kilometer_count' | 'year'
      | 'leasing_company' | 'value' | 'company';


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
  license_plate?   : string;
  chassis_number?  : string; //VIN: vehicle identification number
  brand?          : string;
  model?          : string;
  type?           : string;
  kilometer_count?        : number;
  year?           : number;
  leasing_company? : string;
  value?          : number;
  company?        : string
  [key : string]: string; 
}
