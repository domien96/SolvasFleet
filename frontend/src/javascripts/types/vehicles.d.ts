namespace Vehicles {
  export interface Data {
    [ vehicles: string ]: any;
  }
}

interface VehicleFilterData {
  [fleet: string]: string;
  [type: string]: string;
  [leasingCompany: string]: string;
  [licensePlate: string]: string;
  [vin: string]: string;
  [year: string]: string;
}
