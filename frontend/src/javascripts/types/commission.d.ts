namespace Commission {
  export type Field =
    'id' | 'fleet' | 'company' | 'insuranceType' | 'vehicleType' | 'value' | 'vehicle';
}

interface CommissionData {
  id?: number;
  fleet?: number;
  vehicle?: number;
  company?: number;
  insuranceType?: string;
  vehicleType?: string;
  value?: number;
}




interface CommissionGroupData {
  CivilLiability: CommissionData;
  Omnium: CommissionData;
  DriverInsurance: CommissionData;
  TravelInsurance: CommissionData;
  LegalAid: CommissionData;
  [key: string]: CommissionData;
}
