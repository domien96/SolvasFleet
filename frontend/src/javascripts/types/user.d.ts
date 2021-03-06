namespace User {
  export type Field =
    'id' | 'firstName' | 'lastName' | 'email' | 'password';
}

interface UserData {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  password?: string;
  archived?: boolean;
}

interface UserFilterData {
  [lastName: string]: string;
  [firstName: string]: string;
  [email: string]: string;
  [archived: string]: string;
  [sort: string]: string;
}
