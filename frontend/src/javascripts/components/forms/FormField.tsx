import React      from 'react';
import classNames from 'classnames';
import T          from 'i18n-react';

export default class FormField extends React.Component<LoginField, {}> {
  render() {
    const label = T.translate(this.props.placeholder);
    const wrapperClasses = classNames('form-group', { 'has-error': this.props.hasError });
    return (
      <div className={ wrapperClasses }>
        <label>{ label }</label>
        <T.text
          tag='input'
          type={ this.props.type}
          placeholder={ label }
          className='form-control'
          onChange= { this.props.callback } />
      </div>
    )
  }
}
