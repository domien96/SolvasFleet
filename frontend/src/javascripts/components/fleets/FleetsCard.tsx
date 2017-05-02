import React from 'react';
import { Link } from 'react-router';
import { Collapse } from 'react-bootstrap';
import T from 'i18n-react';

import Card from '../app/Card.tsx';
import FleetForm from '../fleets/FleetForm.tsx';

interface FProps {
  id: number;
  name: string;
}

const FleetLink: React.StatelessComponent<FProps> = ({ id, name }) => {
  return (
    <Link to={ `/fleets/${id}` } key={ id } className='fleet'>
      <h3>{ name }</h3>
      <div className='actions pull-right'>
        <h3>
          <span className='glyphicon glyphicon-menu-right' />
        </h3>
      </div>
    </Link>
  );
};

interface Props {
  onSubmit: (e: any) => void;
  handleChange: (field: Fleet.Field, e: any) => void;
  onClick: () => void;
  formIsVisible: boolean;
  fleets: FleetData[];
  fleet: FleetData;
}

const FleetsCard: React.StatelessComponent<Props> = props => {
  const fleets = props.fleets.map(({ id, name }) => {
    return <FleetLink key={id} id={ id } name={ name } />;
  });

  return (
    <Card>
      <div className='card-title'>
        <h2>{ T.translate('fleet.fleets') }</h2>
        <span className='click' onClick={ props.onClick }>
          <span className='glyphicon glyphicon-plus' aria-hidden='true'/>
          { T.translate('fleet.addNew') }
        </span>
      </div>
      <div className='card-content fleets'>
        <div className='fleet-form-wrapper'>
          <Collapse in={ props.formIsVisible }>
            <div>
              <FleetForm handleChange={ props.handleChange } onSubmit={ props.onSubmit } fleet={props.fleet} />
            </div>
          </Collapse>
        </div>
        { fleets }
      </div>
    </Card>
  );
};

export default FleetsCard;
