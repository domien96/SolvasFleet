import React from 'react';
import Card from '../app/Card.tsx';
import Confirm from 'react-confirm-bootstrap';
import T from 'i18n-react';
import classNames from 'classnames';
import { Link } from 'react-router';

interface Props {
  callToArchive: (e: any) => void;
  isDisabled: boolean;
  companyId: number;
  fleetId: number;
}

const FleetActions: React.StatelessComponent<Props> = props => {
  const buttonclass = classNames('btn', 'btn-danger', { disabled: props.isDisabled });

  return (
    <Card>
      <div className='card-content fleet-actions'>

        <h3><label>{ T.translate('fleet.actions') }</label></h3>
        <div className='actions pull-right'>

            <Link to={ `/commissions/clients/${props.companyId}/fleets/${props.fleetId}` } className='btn btn-default '>
              <span className='glyphicon glyphicon-euro' /> Commissions
            </Link>
          <Confirm
            onConfirm={ props.callToArchive }
            body="Are you sure you want to archive these vehicle(s) ?"
            confirmText="Confirm Archive"
            title="Archive fleet">
            <button className={ buttonclass } onClick={ props.callToArchive }>
              <span className='glyphicon glyphicon-plus' /> Archive vehicle(s)
            </button>
          </Confirm>
        </div>
      </div>
    </Card>
  );

}

export default FleetActions;
