import React from 'react';
import { Link } from 'react-router';
import Card from '../app/Card.tsx';
import T from 'i18n-react';

import { correctInvoice } from '../../actions/fleet_actions.ts';

interface CProps {
  value: boolean;
}

const CorrectionInfo: React.StatelessComponent<CProps> = props => {
  if (props.value === null) { return null; }

  if (props.value === true) {
    return (
      <div className='col-sm-12'>
        <div className='alert alert-success'>
          Corrections were made.
        </div>
      </div>
    );
  } else {
    return (
      <div className='col-sm-12'>
        <div className='alert alert-info'>
          No Corrections were made.
        </div>
      </div>
    );
  }
}

interface Props {
  fleet: number;
  companyId: number;
}

interface State {
  correctionsDone: boolean;
}

class InvoiceActions extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = { correctionsDone: null };
    this.correctInvoiceClick = this.correctInvoiceClick.bind(this);
  }

  correctInvoiceClick = () => {
    const s = (data: any) => {
      this.setState({ correctionsDone: data.corrections });
    }

    correctInvoice(this.props.fleet, s);
  };

  render() {
    const { fleet, companyId } = this.props;
    return (
      <Card>
        <div className='card-content invoice-actions'>
          <div className='row actions'>
            <CorrectionInfo value={ this.state.correctionsDone } />
            <div className='col-sm-4'>
              <Link to={ `/clients/${companyId}/fleets/${fleet}/invoices/current` } className='btn btn-default form-control'>
                <span className='glyphicon glyphicon-file' /> { T.translate('invoice.showCurrent') }
              </Link>
            </div>
            <div className='col-sm-4'>
              <Link to={ `/clients/${companyId}/fleets/${fleet}/invoices` } className='btn btn-default form-control'>
                <span className='glyphicon glyphicon-folder-open' /> { T.translate('invoice.showAll') }
              </Link>
            </div>
            <div className='col-sm-4'>
              <span className='btn btn-default form-control' onClick={ this.correctInvoiceClick }>
                <span className='glyphicon glyphicon-ok' /> { T.translate('invoice.correct') }
              </span>
            </div>
          </div>
        </div>
      </Card>
    );
  }
};

export default InvoiceActions;
