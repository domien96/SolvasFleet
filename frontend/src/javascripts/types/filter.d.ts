

interface FilterProps {
  filter: VehicleFilterData;
  typeDisplay: string
  onFilterType : (type : string) => void;
  onFilterFleet : (fleet : any) => void;
  onReset: () => void;
}

interface Choice {
  name: any; //TODO change to ReactNode or string (translations)
  eventKey: string;
  callback : (value : any) => void;
}

interface Selection {
  title: string;
  choices : Choice[];
}

interface Inputfield {
  name: any; //TODO change to ReactNode or string (translations)
  value: string | number;
  type: string;
  callback: (value : any) => void;
}