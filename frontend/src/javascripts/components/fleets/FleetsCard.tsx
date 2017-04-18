import React from 'react';
import { Link } from 'react-router';
import { Collapse } from 'react-bootstrap';

import Card from '../app/Card.tsx';
import FleetForm from '../fleets/FleetForm.tsx';

interface FProps {
  id : number;
  name : string;
}

const FleetLink : React.StatelessComponent<FProps> = ({ id, name }) => {
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
}

interface Props {
  onSubmit : (e : any) => void;
  handleChange : (field: Fleet.Field, e : any) => void;
  onClick : () => void;
  formIsVisible : boolean;
  fleets : Fleet[];
}

const FleetsCard : React.StatelessComponent<Props> = props => {
  let fleets = props.fleets.map(({ id, name }) => {
    <FleetLink id={ id } name={ name } />
  });

  return (
    <Card>
      <div className='card-title'>
        <h2>Fleets</h2>
        <span className='click' onClick={ props.onClick }>Add a new fleet</span>
      </div>
      <div className='card-content fleets'>
        <div className='fleet-form-wrapper'>
          <Collapse in={ props.formIsVisible }>
            <div>
              <FleetForm handleChange={ props.handleChange } onSubmit={ props.onSubmit } />
            </div>
          </Collapse>
        </div>
        { fleets }
      </div>
    </Card>
  );
}

export default FleetsCard;
