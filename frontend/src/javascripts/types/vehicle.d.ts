namespace Vehicle {
  export type Field =
    'id' | 'licensePlate' | 'vin' | 'brand'
      | 'model' | 'type' | 'mileage' | 'year'
      | 'leasingCompany' | 'value' | 'fleet';
}

interface VehicleData {
  id?: number;
  licensePlate?: string;
  vin?: string; // VIN: vehicle identification number
  brand?: string;
  model?: string;
  type?: string;
  mileage?: number;
  year?: string;
  leasingCompany?: number;
  value?: number;
  fleet?: number;
  [key: string]: string;
}

interface VehicleFilterData {
  [fleet: string]: string;
  [type: string]: string;
  [leasingCompany: string]: string;
  [licensePlate: string]: string;
  [vin: string]: string;
  [year: string]: string;
  [archived: string]: string;
}
