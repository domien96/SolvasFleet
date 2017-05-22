import React from 'react';
import Card from '../app/Card.tsx';
import T from 'i18n-react';
import FleetForm from '../fleets/FleetForm.tsx';
import DynamicGuiComponent from '../app/DynamicGuiComponent.tsx';
import Auth from '../../modules/Auth.ts';
import { Link } from 'react-router';
import { Collapse } from 'react-bootstrap';

interface Props {
  showSettings: boolean;
  onSettingsClick: any;
  handleChange: (field: any, e: any) => void;
  onSubmit: (e: any) => void;
  fleet: FleetData;
}

const FleetSettings: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Card>
        <div className='card-content fleet-action-card' onClick={ props.onSettingsClick }>
        <h3><label>{ T.translate('fleet.settings') }</label></h3>
          <div className='actions pull-right'>
            <h3>
              <span className='glyphicon glyphicon-cog'/>
            </h3>
          </div>
        </div>
      </Card>

      <Collapse in={ props.showSettings }>
        <Card>
          <div className='card-content fleets fleetsettings'>
            <div>
              <FleetForm handleChange={ props.handleChange } onSubmit={ props.onSubmit } fleet={ props.fleet } icon='glyphicon-floppy-disk'/>
            </div>
            <DynamicGuiComponent authorized={ Auth.canWriteFleetsOfCompany(-1) }>
              <Link to={ `/commissions/clients/${props.fleet.company}/fleets/${props.fleet.id}` } className='btn pull-right btn-info '>
                <span className='glyphicon glyphicon-euro' /> Commissions
              </Link>
            </DynamicGuiComponent>
          </div>

        </Card>
      </Collapse>
    </div>
  );
}

export default FleetSettings;
