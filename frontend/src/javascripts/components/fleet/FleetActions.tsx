import React from 'react';
import Card from '../app/Card.tsx';
import Confirm from 'react-confirm-bootstrap';
import T from 'i18n-react';
import classNames from 'classnames';

interface Props {
  callToArchive: any;
  isDisabled: boolean;
}

const FleetActions: React.StatelessComponent<Props> = props => {
  const disableButton: string = props.isDisabled ? ' disabled':'';
  const buttonclass = classNames('btn', 'btn-danger', disableButton);

  return (
    <Card>
      <div className='card-content fleet-actions'>
        <h3><label>{ T.translate('fleet.actions') }</label></h3>
        <div className='actions pull-right'>
          <Confirm
            onConfirm={ props.callToArchive }
            body="Are you sure you want to archive these vehicle(s) ?"
            confirmText="Confirm Archive"
            title="Archive fleet">
            <button className={ buttonclass } onClick={ props.callToArchive }>
              <span className='glyphicon glyphicon-plus' /> Archive
            </button>
          </Confirm>
        </div>
      </div>
    </Card>
  );

}

export default FleetActions;
