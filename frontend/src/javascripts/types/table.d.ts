namespace Table {
  export interface Props {
    head    : Table.Head.Data[];
    data    : any;
    onClick : (e : any) => void;
  }

  namespace Head {
    export interface Data {
      key: string;
      label: string;
    }
  }
}
