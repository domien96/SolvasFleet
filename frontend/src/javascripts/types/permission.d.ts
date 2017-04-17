namespace Permission {
  export type Field =
    'id' | 'action' | 'resource' ;
}

interface PermissionData {
  id?           : number
  action?        : string;
  resource?   : string;
}

