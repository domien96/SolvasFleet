namespace User {
  export interface Props {
    [ params : string ] : { [ id : string ] : number };
  }

  export interface State {
    user : User;
  }

  export type Field =
    'id' | 'first_name' | 'last_name' | 'email' | 'password';

  namespace New {
    export interface Props { }

    export interface State {
      errors : Form.Error[];
      user   : User;
    }
  }
}

interface User {
  id?         : number;
  first_name? : string;
  last_name?  : string;
  email?      : string;
  password?   : string;
}

interface UserTemp {
  id         : number;
  first_name : string;
  last_name  : string;
  email      : string;
  [ password : string ]   : string;
}
