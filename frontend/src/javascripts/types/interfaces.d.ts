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
  placeholder : string;
  type : string;
  hasError: boolean;
  callback : (e : any) => void;
}

/* Form Errors */

interface FormError {
  field : string;
  error : string;
}

/* Add Company */

interface CompanyProps{
  url : string;
}

interface CompanyState{
  errors : FormError[];
  name : string;
  vat_number : string;
  phone_number : string;
  address : string;
}


/* Companies */

interface Company {
  name:         string;
  VATnumber:    string;
  phone_number: string;
  address:      string;
}

interface CompaniesProps {
  url : string;
}

interface CompaniesState {
  companies: Company[];
}
