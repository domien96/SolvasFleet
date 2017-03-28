namespace User {
  export interface Props {
    [ params : string ] : { [ id : string ] : number };
    fetchUsers : () => void;
  }

  export interface State {
    user : User;
  }

  export type Field =
    'id' | 'firstName' | 'lastName' | 'email' | 'password';

  namespace UForm {

    export interface State {
      errors : Form.Error[];
      user   : User;
    }
  }
}

interface User {
  id?         : number;
  firstName? : string;
  lastName?  : string;
  email?      : string;
  password?   : string;
}

interface MUser {
  id?         : number;
  firstName? : string;
  lastName?  : string;
  email?      : string;
  password?   : string;
}
