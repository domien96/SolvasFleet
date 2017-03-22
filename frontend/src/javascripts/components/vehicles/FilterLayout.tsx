import React from 'react';

import Card      from '../app/Card.tsx';
import { Link } from 'react-router';
import { ButtonGroup, DropdownButton, MenuItem } from 'react-bootstrap';
import T     from 'i18n-react';

const Filter :  React.StatelessComponent<Props> = props => {
  return (
    <Card>
      <div className='row actions card-content'>
        <ButtonGroup justified>
          <div className='col-md-3'>
            <div>             
              <DropdownButton className='btn btn-default' title={ props.typeDisplay } id='vehicleTypeChoice' >
                <MenuItem onSelect={ props.onFilterType('') }>              { T.translate('vehicle.options.allVehicles') }</MenuItem>
                <MenuItem onSelect={ props.onFilterType('personalCar') }>   { T.translate('vehicle.options.personalCar') }</MenuItem>
                <MenuItem onSelect={ props.onFilterType('van') }>           { T.translate('vehicle.options.van') }</MenuItem>
                <MenuItem onSelect={ props.onFilterType('semiTrailer') }>   { T.translate('vehicle.options.semiTrailer') }</MenuItem>
                <MenuItem onSelect={ props.onFilterType('trailer') }>       { T.translate('vehicle.options.trailer') }</MenuItem>
                <MenuItem onSelect={ props.onFilterType('truck') }>         { T.translate('vehicle.options.truck') }</MenuItem>
              </DropdownButton>           
            </div>
          </div>    
          <div className='col-md-5'>
            <form>
              <span>
                <label className='col-md-7 lab-padding'> 
                  <div>Fleet ID:</div>
                </label>
                <div className='input-padding align-left'>
                  <input className='col-md-4' type='number' value={ props.filter.fleetId } onChange={ props.onFilterFleet } />
                </div>
              </span>             
            </form>
          </div>
          <div className='col-md-3'>
            <div>
              <Link to='/vehicles/new' className='btn btn-default pull-right'>
                <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new vehicle
              </Link>
            </div>
          </div>
        </ButtonGroup>
      </div>
    </Card>
    );
  }