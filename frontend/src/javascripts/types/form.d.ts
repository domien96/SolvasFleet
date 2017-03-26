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

  namespace Choice {
    export interface Props {
      placeholder : string;
      choices : Table.Head.Data[];
      callback    : (e : any) => void;
      value : string;
    }
  }

  export interface Error {
    field : string;
    error : string;
  }
}
