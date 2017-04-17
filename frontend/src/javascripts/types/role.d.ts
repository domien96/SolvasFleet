namespace Role {
  export type Field =
    'id' | 'name' | 'permissions' ;
}

interface RoleData {
	id? 		: number;	
  name?    : string;
  permissions? : string;
}
