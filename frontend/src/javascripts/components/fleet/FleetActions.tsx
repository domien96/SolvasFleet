import React from 'react';
import deleteVehicle from '../../actions/vehicle_actions.ts';
import Card   from '../app/Card.tsx';
import T from 'i18n-react';

interface Props {
  isDisabled : boolean;
  callToArchive : any;
}

const FleetActions : React.StatelessComponent<Props> = props => {
  var disableButton : string = props.isDisabled ? ' disabled':'';




  return (
    <Card>
    <div className='card-content fleet-actions'>
    <h3><label>{ T.translate('fleet.actions') }</label></h3>
    <div className='actions pull-right'>
    <button className={'btn btn-danger'+disableButton} onClick={props.callToArchive}>
      <span className='glyphicon glyphicon-plus' /> Archive
    </button>
    </div></div></Card>
  );

}

export default FleetActions;
