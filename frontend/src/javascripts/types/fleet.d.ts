namespace Fleet {
  export interface Props {
   
  }

  export interface State {
    fleet : Fleet;
  }

}

interface Fleet {
  id?         : number;
  name?       : string;
  size?       : number;
}

