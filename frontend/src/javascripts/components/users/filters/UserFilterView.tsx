import React from 'react';
import Card from '../../app/Card.tsx';
import { Typeahead } from 'react-bootstrap-typeahead';

interface Props {
  typeaheadfields: Typeaheadfield[];
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
