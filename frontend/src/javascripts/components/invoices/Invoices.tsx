import React              from 'react';

import { fetchInvoices } from '../../actions/fleet_actions.ts';
import { redirect_to } from'../../routes/router.tsx';
import InvoicesView from './InvoicesView.tsx'

interface Props {
  [ params: string ]: { fleetId: number, companyId: number };
}

interface State {
  invoices : InvoiceData[];
}

class Invoices extends React.Component<Props, State> {

  constructor(props: {}) {
    super(props);
    this.state = { invoices: [] };
    this.handleClick=this.handleClick.bind(this);
  }

  componentDidMount() {
    if(this.props.params.companyId && this.props.params.fleetId){
      this.fetchInvoices(this.props.params.companyId, this.props.params.fleetId);
    }
  }

  componentWillReceiveProps(nextProps: any){
    if (nextProps.params.id != this.props.params.fleetId){
      this.fetchInvoices(nextProps.params.companyId, nextProps.params.fleetIid);
    }
  }

  fetchInvoices(companyId: number, fleetId: number) {
    fetchInvoices(companyId, fleetId, ((data: Invoices.Data) => {
      this.setState({ invoices: data.data })
    }));
  }

  handleClick(id : number) {
    redirect_to(`clients/${this.props.params.companyId}/fleets/${this.props.params.fleetId}/invoices/${id}`);
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child: any) => React.cloneElement(child, {
        fetchInvoices: this.fetchInvoices.bind(this)
      })
    );

    return (

      <InvoicesView invoices={ this.state.invoices } onInvoiceSelect={ this.handleClick } >
        { children }
      </InvoicesView>
    );
  }
}

export default Invoices;
