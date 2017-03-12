namespace Form {
  namespace Field {
    export interface Props {
      placeholder : string;
      type        : string;
      hasError    : boolean;
      callback    : (e : any) => void;
      value       : any;
    }
  }

  export interface Error {
    field : string;
    error : string;
  }

  namespace Errors {
    export interface Props {
      errors : Form.Error[];
    }
  }
}
