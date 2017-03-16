namespace Vehicle {
  export interface Props {
    [ params : string ] : { [ id : string ] : number };
    fetchVehicles : () => void;
  }

  export interface State {
    vehicle : Vehicle;
  }

  export type Field =
    'id' | 'licensePlate' | 'vin' | 'brand'
      | 'model' | 'type' | 'mileage' | 'year'
      | 'leasingCompany' | 'value' | 'fleet';


  namespace VForm {
    export interface Props {
      onSubmit     : (e : any) => void;
      handleChange : (field : Vehicle.Field, e : any) => void;
      errors       : Form.Error[];
      hasError     : (field : Vehicle.Field) => boolean;
      vehicle      : Vehicle;
    }

    export interface State {
      errors : Form.Error[];
      vehicle   : Vehicle;
    }
  }
}

interface Vehicle {
  id?             : number;
  licensePlate?   : string;
  vin?            : string; //VIN: vehicle identification number
  brand?          : string;
  model?          : string;
  type?           : string;
  mileage?        : number;
  year?           : number;
  leasingCompany? : string;
  value?          : number;
  fleet?          : number
  [key : string]  : string;
}
