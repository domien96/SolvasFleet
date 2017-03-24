import React from 'react';
import Card      from '../app/Card.tsx';
import { ButtonGroup, DropdownButton, MenuItem } from 'react-bootstrap';

/*
	A re-usable component for implementing filters

	@param selections is a list of Selection objects

	Selection is an object with 2 variables: 
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
*/
interface Props {
  selections : Selection[];
  inputfields : Inputfield[];
  onReset : () => void;
}

const Filter : React.StatelessComponent<Props> = props => {

	const dropdowns = props.selections.map((selection : Selection, i : number) => {
		const choices = selection.choices.map((choice : Choice, j : number) => {
				return(
					<MenuItem key={ j } eventKey={ choice.eventKey } onSelect={ choice.callback }>{ choice.name }</MenuItem>
				);
			}
		);
		return(
			<DropdownButton id={ i } key={ i } className='btn btn-default' title={ selection.title }>{ choices }</DropdownButton>
		);
	}
	);

	const inputfields = props.inputfields.map((inputfield : Inputfield, i : number) => {
		var { name, value, type, callback} = inputfield;
		return(
			<div>
				<label>{ name }</label>
				<input key={ i } type={ type } placeholder={ name } className='form-control' onChange={ callback } value={ value } />
			</div>
		);
	}
	);

	return(
		<div>
		<Card>
				<div className='card-title'>
					<h3>Filter</h3>
				</div>
      <div className='card-content'>
        <ButtonGroup justified>
        	<div>
        		{ dropdowns }
        	</div>
        	<div>
        		{ inputfields }
        	</div>
        	<div>
        	  <button className='btn btn-default' onClick={ props.onReset }>Reset filters</button>
        	</div>
        </ButtonGroup>
      </div>
    </Card>
    </div>
	);

}

export default Filter;