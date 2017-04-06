namespace User {
  export type Field =
    'id' | 'firstName' | 'lastName' | 'email' | 'password';
}

interface UserData {
  id?         : number;
  firstName? : string;
  lastName?  : string;
  email?      : string;
  password?   : string;
}
