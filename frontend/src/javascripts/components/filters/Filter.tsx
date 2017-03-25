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
  selections : Selectionfield[];
  inputfields : Inputfield[];
  onReset : () => void;
}

const Filter : React.StatelessComponent<Props> = props => {

	const dropdowns = props.selections.map((selection : Selectionfield) => {
		const choices = selection.choices.map((choice : Choice) => {
				return(
					<MenuItem key={ choice.eventKey } eventKey={ choice.eventKey } onSelect={ choice.callback }>{ choice.name }</MenuItem>
				);
			}
		);
		return(
			<div key={ selection.name }>
				<div className='col-sm-12'>
					<label>{ selection.name }</label>
				</div>
				<div className='col-sm-12'>
					<ButtonGroup justified>
						<DropdownButton id={ selection.title } key={ selection.title } className='btn btn-default' title={ selection.title }>{ choices }</DropdownButton>
					</ButtonGroup>
				</div>
			</div>
		);
	}
	);

	const inputfields = props.inputfields.map((inputfield : Inputfield, i : number) => {
		var { name, value, type, callback} = inputfield;
		return(
			<div key={ i }>
				<label>{ name }</label>
				<input type={ type } placeholder={ name } className='form-control' onChange={ callback } value={ value } />
			</div>
		);
	}
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
      		</div>
      		<div className="clearfix" />
				</div>
      <div className='card-content'>
        <div className='col-sm-6'>
          <div>
        		{ inputfields }
        	</div>
	      </div>
	      <div className='col-sm-6'>
	       	<div>
	        	{ dropdowns }
	        </div>
        </div>	
      	<div className="clearfix" />
      </div>
    </Card>
    </div>
	);

}

export default Filter;