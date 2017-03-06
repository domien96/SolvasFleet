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

/* Company */

interface CompanyProps{
  url : string;
}

interface CompanyState{
  errors : FormError[];
  name : string;
  vatNumber : string;
  phoneNumber : string;
  address : string;
}

/* Companies */

interface Company {
  name:         string;
  VATnumber:    string;
  phone_number: string;
  address:      string;
}

interface CompaniesProps { }

interface CompaniesState {
  companies: Company[];
}

interface CompaniesData {
  [ companies : string ] : any;
}

/* Vehicle */

interface VehicleProps{
  url : string;
}

interface VehicleState{
  errors: FormError[];
  licencePlate: string;
  chassisNumber: string; //VIN: vehicle identification number
  brand: string;
  model: string;
  type: string;
  kmCount: number;
  year: number;
  leasingCompany: string;
  value: number;
  company: string;
}

/* User */

interface UserProps{
  url: string;
}

interface UserState{
  errors: FormError[];
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

/* Role */

interface RoleProps{
  url: string;
}

interface RoleState{
  errors: FormError[];
  company: string;
  permission: string;
  user: string;
  startDate: string;
  endDate: string;
}

/* Sidebar */

interface SideBarLinkProps {
  path: string;
  active?: boolean;
}

/* Card */

interface CardProps {
  className?: string;
}
