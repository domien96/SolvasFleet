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
  civilLiability: CommissionData;
  omnium: CommissionData;
  driverInsurance: CommissionData;
  travelInsurance: CommissionData;
  legalAid: CommissionData;
  [key: string]: CommissionData;
}
