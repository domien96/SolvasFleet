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
