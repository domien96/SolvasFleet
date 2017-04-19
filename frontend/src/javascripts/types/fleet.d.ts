namespace Fleet {
  export type Field =
    'id' | 'name' | 'company';
}

interface FleetData {
  id?         : number;
  name?       : string;
  size?       : number;
  company?    : number;
  facturationPerod? : number;
  paymentPeriod? : number;
}
