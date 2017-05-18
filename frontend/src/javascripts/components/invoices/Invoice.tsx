import React from 'react';

import FileSaver from 'file-saver';
import InvoiceView from './InvoiceView.tsx';
import { fetchInvoice, fetchInvoicePdf } from '../../actions/fleet_actions.ts';

interface Props {
  params : { companyId: number, fleetId: number, invoiceId: number };
  fetchInvoices : () => void;
}

interface State {
  invoice: InvoiceData;
}

class Invoice extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { invoice: {} };
    this.handleDownload = this.handleDownload.bind(this);
  }

  componentDidMount() {
    this.fetchInvoice(this.props.params.companyId, this.props.params.fleetId, this.props.params.invoiceId);
  }


  fetchInvoice(companyId: number, fleetId: number, invoiceId: number) {
    fetchInvoice(companyId, fleetId, invoiceId, ((data: any) => {
      this.setState({ invoice: data })
    }), undefined, {type:'billing'});
  }

  handleDownload(){
    const { companyId, fleetId, invoiceId } = this.props.params;
    fetchInvoicePdf(companyId, fleetId, invoiceId, ((data: any) => {
      FileSaver.saveAs(data, `invoice${invoiceId}.pdf`);
    }), undefined, { type: 'billing' });
  }

  render() {
    return (
      <InvoiceView invoice={ this.state.invoice } onDownload={ this.handleDownload } />
    );
  }
}

export default Invoice;

