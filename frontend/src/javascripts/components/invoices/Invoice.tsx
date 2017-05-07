import React from 'react';

import FileSaver from 'file-saver';
import InvoiceView from './InvoiceView.tsx';
import { fetchInvoice, fetchInvoicePdf } from '../../actions/fleet_actions.ts';

interface Props {
  params : { fleetId : number, invoiceId : number };
  fetchInvoices : () => void;
}

interface State {
  invoice : InvoiceData;
}

class Invoice extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { invoice: {} };
    this.handleDownload = this.handleDownload.bind(this);
  }

  componentDidMount() {
    this.fetchInvoice(this.props.params.fleetId, this.props.params.invoiceId);
  }

  fetchInvoice(fleetId : number, invoiceId : number) {
    let fail = (data : any) => console.log(data);
    fetchInvoice(fleetId, invoiceId, ((data) => {
      this.setState({ invoice: data })
    }), fail, {type:'billing'});
  }

  handleDownload(){

    var { fleetId, invoiceId } = this.props.params;
    let fail = (data : any) => console.log(data);
    fetchInvoicePdf(fleetId, invoiceId, ((data : any) => {
      FileSaver.saveAs(data, `invoice${invoiceId}.${'pdf'}`);
    }), fail, {type:'billing'});
  }

  render() {
    return (
      <InvoiceView invoice={ this.state.invoice } onDownload={ this.handleDownload } />
    );
  }
}

export default Invoice;
