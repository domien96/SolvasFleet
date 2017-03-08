import React      from 'react';
import classNames from 'classnames';
import T          from 'i18n-react';

export default class FormField extends React.Component<LoginField, {}> {
  public errors() : string {
    return classNames(
      { 'has-error': this.props.hasError },
      'form-control'
    );
  }

  render() {
    const label = T.translate(this.props.placeholder);
    return (
      <div className='form-group'>
        <label>{ label }</label>
        <T.text
          tag='input'
          type={ this.props.type}
          placeholder={ label }
          className={ this.errors() }
          onChange= { this.props.callback } />
      </div>
    )
  }
}
