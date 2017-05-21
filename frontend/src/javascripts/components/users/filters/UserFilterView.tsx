import React from 'react';
import Card from '../../app/Card.tsx';
import { Typeahead } from 'react-bootstrap-typeahead';
import { ButtonGroup, DropdownButton, MenuItem } from 'react-bootstrap';

interface Props {
  typeaheadfields: Typeaheadfield[];
  selections: Selectionfield[];
  onReset: () => void;
  onHide: () => void;
  toggleArchive: () => void;
  archived: string;
}

const UserFilterView: React.StatelessComponent<Props> = props => {

  const typeaheadfields = props.typeaheadfields.map((typeaheadfield: Typeaheadfield, i: number) => {
    const { name, data, selected, callback } = typeaheadfield;
    return(
      <div key={ i }>
        <label>{ name }</label>
        <Typeahead onChange={ callback } options={ data } selected={ selected }/>
      </div>
    );
  });

  const dropdowns = props.selections.map((selection: Selectionfield) => {
    const choices = selection.choices.map((choice: Choice) => {
      return(
        <MenuItem key={ choice.eventKey } eventKey={ choice.eventKey } onSelect={ choice.callback }>
          { choice.name }
        </MenuItem>
      );
    });

    return(
      <div key={ selection.name }>
        <label>{ selection.name }</label>
        <ButtonGroup justified>
          <DropdownButton
            id={ selection.title }
            key={ selection.title }
            className='btn btn-default'
            title={ selection.title }>
            { choices }
          </DropdownButton>
        </ButtonGroup>
      </div>
    );
  });

  const archive = (
    <div className="checkbox">
    <label className="label-input"> 
      <input type='checkbox' checked={ (props.archived == 'true') } onChange={ props.toggleArchive } name="Show Archived" />
      Show Archived
    </label>
    </div>
  );
  return(
    <div>
    <Card>
      <div className='card-title'>
        <div className='col-sm-6'>
        <h3>Filter</h3>
        </div>
        <div className='col-sm-6'>
          <button className='btn btn-default pull-right' onClick={ props.onReset }>Reset filters</button>
          <button className='btn btn-default pull-right' onClick={ props.onHide }>Hide</button>
        </div>
        <div className="clearfix" />
      </div>
      <div className='card-content'>
        <div className='col-sm-6'>
          <div>
            { typeaheadfields }
          </div>
          <div>
            { dropdowns }
          </div>
          <div>
            { archive }
          </div>
        </div>
        <div className="clearfix" />
      </div>
    </Card>
    </div>
  );
};

export default UserFilterView;
