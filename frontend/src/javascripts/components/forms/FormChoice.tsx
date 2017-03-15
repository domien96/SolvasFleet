import React      from 'react';
import T          from 'i18n-react';

export default class FormChoice extends React.Component<Form.Choice.Props, {}> {


  render() {
    const label = T.translate(this.props.placeholder);

    const choices = this.props.choices.map((c : Table.Head.Data, i : number) => (
      <option key={i} value={c.key}>{ T.translate(c.label) }</option>
      ));

    return (
      <div className={ 'form-group' }>
        <label>{ label }:
        <select className='btn btn-default' value={ this.props.value } onChange={ this.props.callback }>
        { choices }
        </select>
        </label>
      </div>
    )
  }
}