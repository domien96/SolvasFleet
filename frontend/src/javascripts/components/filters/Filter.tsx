import React from 'react';
import Card from '../app/Card.tsx';
import { ButtonGroup, DropdownButton, MenuItem } from 'react-bootstrap';
import { Typeahead } from 'react-bootstrap-typeahead';
import DateForm from '../forms/DateForm.tsx';

/*
  A re-usable component for showing filters with multiple filter criteria

  @param selections is a list of Selection objects

  Selection is an object with 3 variables:
  @param name The name of the property that has choices
  @param title The current selected value of the possible choices
  @param choices A map of choices

  Choice is an object with 2 variables:
  @param name The name of the choice
  @param callback The function that gets called when this choice gets selected

  @param inputfields is a list of Inputfield objects

  Inputfield has 4 variables
  @param name The name of the required data
  @param value The current entered value in the inputfield
  @param type The type of data required in the inputfield
  @param callback The function that gets called when the inputvalue changes

  @param typeaheadfields is a list of Typeaheadfield objects, a field featuring typeahead lookup for filtering

  @param onReset A function that clears all input fields and sets the selections to their default choice
*/
interface Props {
  selections: Selectionfield[];
  inputfields: Inputfield[];
  typeaheadfields: Typeaheadfield[];
  datefields: Datefield[];
  onReset: () => void;
  onHide: () => void;
  toggleArchive?: () => void;
  archived?: string;
}

const Filter: React.StatelessComponent<Props> = props => {

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

  const inputfields = props.inputfields.map((inputfield: Inputfield, i: number) => {
    const { name, value, type, callback} = inputfield;
    return(
      <div key={ i }>
        <label>{ name }</label>
        <input
          type={ type }
          placeholder={ name }
          className='form-control'
          onChange={ callback }
          value={ value || '' } />
      </div>
    );
  });

  const typeaheadfields = props.typeaheadfields.map((typeaheadfield: Typeaheadfield, i: number) => {
    const { name, data, selected, callback } = typeaheadfield;
    return(
      <div key={ i }>
        <label>{ name }</label>
        <Typeahead onChange={ callback } options={ data } selected={ selected }/>
      </div>
    );
  });

  const datefields = props.datefields.map((datefield, i) => {
    const { name, data, callback } = datefield;
    return (
      <div key={ i }>
        <DateForm callback={ callback } value={ data } label={ name } hasError={ false } />
      </div>
    );
  });

  let archive = <div></div>;
  if (props.archived) {
    archive = (
      <div className="checkbox">
      <label className="label-input"> 
        <input type='checkbox' checked={ (props.archived == 'true') } onChange={ props.toggleArchive } name="Show Archived" />
        Show Archived
      </label>
      </div>
    );
  }

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
            { dropdowns }
          </div>
          <div>
            { inputfields }
          </div>
          <div>
            { archive }
          </div>
        </div>
        <div className='col-sm-6'>
          <div>
            { datefields }
          </div>
          <div>
            { typeaheadfields }
          </div>
        </div>

        <div className="clearfix" />
      </div>
    </Card>
    </div>
  );
};

export default Filter;
