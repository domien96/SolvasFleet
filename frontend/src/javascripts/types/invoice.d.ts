
interface InvoiceData {
  id?         : number;
  fleet? : number;
  paid?  : boolean;
  type?      : string;
  totalAmount?   : number;
  startDate? : string;
  endDate? : string;
}

namespace Invoices {
  export interface Data {
    [ invoices : string ] : any;
  }
}
