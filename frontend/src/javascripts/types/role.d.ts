namespace Role {
  export interface Props { }

  export interface State {
    errors : Form.Error[];
    role   : Role;
  }
}

interface Role {
  company    : string;
  permission : string;
  user       : string;
  startDate  : string;
  endDate    : string;
}
