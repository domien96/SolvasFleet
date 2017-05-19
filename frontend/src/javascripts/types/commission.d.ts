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
  burgerlijkeAansprakelijkheid: CommissionData;
  omnium: CommissionData;
  rechtsbijstand: CommissionData;
  veiligheid: CommissionData;
  reisbijstand: CommissionData;
  [key: string]: CommissionData;
}
