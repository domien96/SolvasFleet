/* Login */

interface LoginProps {
  url : string;
}

interface LoginState {
  errors : FormError[];
  email : string;
  password : string;
}

interface LoginField {
  field : string;
  type : string;
  hasError: boolean;
  callback : (e : any) => void;
}

/* Form Errors */

interface FormError {
  field : string;
  error : string;
}

