namespace Table {
  namespace Info {
    export interface Props {
      head    : Table.Head.Data[];
      data    : any;
      onClick : (e : any) => void;
    }
  }

  namespace Detail {
    export interface Props {
      data : Table.Head.Data[];
    }
  }

  namespace Head {
    export interface Data {
      key: string;
      label: string;
    }
  }
}
