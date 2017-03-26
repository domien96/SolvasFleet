namespace Form {
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
