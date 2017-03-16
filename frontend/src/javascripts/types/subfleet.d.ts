namespace SubFleet {
  export interface Props {}

  export interface State {
    fleet : SubFleet;
  }

}

interface SubFleet {
  id?         : number;
  name?       : string;
  size?       : number;
}

