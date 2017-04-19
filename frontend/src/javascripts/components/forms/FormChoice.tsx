import React      from 'react';
import T          from 'i18n-react';

interface Props {
      placeholder : string;
      choices : Table.Head.Data[];
      callback    : (e : any) => void;
      value : string;
}

const FormChoice : React.StatelessComponent<Props> = props => {

	var { placeholder, choices, value, callback } = props;

    const label = T.translate(placeholder);

    const choicesList = choices.map((c : Table.Head.Data, i : number) => (
      <option key={i} value={c.key}>{ T.translate(c.label) }</option>
    ));

    return (
      <div className={ 'form-group' }>
        <label>{ label }:{' '}
        <select className='btn btn-default' value={ value } onChange={ callback }>
        { choicesList }
        </select>
        </label>
      </div>
    );
}

export default FormChoice;