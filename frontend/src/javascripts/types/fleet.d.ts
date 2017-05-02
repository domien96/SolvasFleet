namespace Fleet {
  export type Field =
    'id' | 'name' | 'company' | 'facturationPeriod' | 'paymentPeriod';
}

interface FleetData {
  id?         : number;
  name?       : string;
  size?       : number;
  company?    : number;
  facturationPeriod? : number;
  paymentPeriod? : number;
}
