import React from 'react';

import { fetchInvoices } from '../../actions/fleet_actions.ts';
import { redirect_to } from '../../routes/router.tsx';
import InvoicesView from './InvoicesView.tsx';

interface Props {
  [ params: string ]: { fleetId: number, companyId: number };
}

interface State {
  invoices: InvoiceData[];
  tableData: any;
}

class Invoices extends React.Component<Props, State> {
  constructor(props: {}) {
    super(props);
    this.state = { invoices: [], tableData: [] };
    this.handleClick = this.handleClick.bind(this);
  }

  componentDidMount() {
    if(this.props.params.companyId && this.props.params.fleetId){
      this.fetchInvoices(this.props.params.companyId, this.props.params.fleetId);
    }
  }

  componentWillReceiveProps(nextProps: any){
    if (nextProps.params.id !== this.props.params.fleetId){
      this.fetchInvoices(nextProps.params.companyId, nextProps.params.fleetIid);
    }
  }

  fetchInvoices(companyId: number, fleetId: number) {
    fetchInvoices(companyId, fleetId, ((data: Invoices.Data) => {
      this.setTableData(data.data);
      this.setState({ invoices: data.data })
    }));
  }

  handleClick(id: number) {
    redirect_to(`clients/${this.props.params.companyId}/fleets/${this.props.params.fleetId}/invoices/${id}`);
  }

  setTableData(invoices: InvoiceData[]) {
    const data = invoices.map((invoice: InvoiceData) => {
      const { id, paid, startDate, endDate } = invoice;
      let newStartDate = startDate
      let newEndDate = endDate
      if(startDate) {
        newStartDate = startDate.split('T')[0];
        newEndDate = endDate.split('T')[0];
      }
      let newPaid = 'Not Paid';
      if(paid !== undefined) {
        newPaid = paid.toString();
      }
      if(newPaid) {
        if(newPaid === 'true') {
          newPaid = 'Paid';
        } else {
          newPaid = 'Not Paid';
        }
      }
      return {
        id: id,
        startDate: newStartDate,
        endDate: newEndDate,
        paid: newPaid
      }
    });

    this.setState({ tableData: data });
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child: any) => React.cloneElement(child, {
        fetchInvoices: this.fetchInvoices.bind(this),
      }),
    );

    return (
      <InvoicesView invoices={ this.state.invoices } onInvoiceSelect={ this.handleClick } tableData={ this.state.tableData } >
        { children }
      </InvoicesView>
    );
  }
}

export default Invoices;
