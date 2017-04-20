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
    fetchInvoice(fleetId, invoiceId, ((data) => {
      this.setState({ invoice: data })
    }));
  }

  handleDownload(){

    var { fleetId, invoiceId } = this.props.params;

    fetchInvoicePdf(fleetId, invoiceId, ((data : any) => {
      FileSaver.saveAs(data, `invoice${invoiceId}.${'pdf'}`);
    }));
  }

  render() {
    return (
      <InvoiceView invoice={ this.state.invoice } onDownload={ this.handleDownload } />
    );
  }
}

export default Invoice;
