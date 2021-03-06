interface Choice {
  name: any; // TODO change to ReactNode or string (translations)
  eventKey: string;
  callback: (value: string) => void;
}

interface Selectionfield {
  name: string;
  title: string;
  choices: Choice[];
}

interface Inputfield {
  name: any; // TODO change to ReactNode or string (translations)
  value: string | number;
  type: string;
  callback: (value: any) => void;
}

interface Typeaheadfield {
  name: any;
  data: string[];
  selected: string[];
  callback: (value: any) => void;
}

interface Datefield {
  name: string;
  data: string;
  callback: (value: any) => void;
}
