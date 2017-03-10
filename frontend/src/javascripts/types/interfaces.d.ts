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

interface ErrorsProps {
  errors: FormError[];
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

/* User */

interface AddUserState{
  errors: FormError[];
  first_name: string;
  last_name: string;
  email: string;
  password: string;
}

interface UserState{
  id:         number;
  first_name: string;
  last_name:  string;
  email:      string;
  password:   string;
}


interface UserData{
  [ users : string ] : any;
}

interface UserProps {}

interface UserProp{
  params : any;
}

/* Users */

interface User {
  id:         number;
  first_name: string;
  last_name:  string;
  email:      string;
  password:   string;
}

interface UsersState {
  users: User[];
}

interface UsersData {
  [ users : string ] : any;
}

/* Companies */

interface Company {
  id:           number;
  name:         string;
  VATnumber:    string;
  phoneNumber: string;
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

interface Vehicle {
  licencePlate: string;
  chassisNumber: string; //VIN: vehicle identification number
  brand: string;
  model: string;
  type: string;
  kmCount: number;
  year: number;
  leasingCompany: string;
  value: number;
  company: string
}

interface VehiclesState {
  vehicles: Vehicle[];
}

interface VehiclesData {
  [ vehicles : string ] : any;
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

/* Table */

interface tableHead {
  key: string;
  label: string
}

interface InfoTableProps {
  head: tableHead[];
  data: any;
  onClick : (e : any) => void;
}

/* Sidebar */

interface SidebarLinkProps {
  path: string;
}

/* Card */

interface CardProps {
  className?: string;
}

/* WrappedCol */

interface WrappedColProps {
  cols?: number;
}

/* App */

interface AppProps {
  location: any;
}

