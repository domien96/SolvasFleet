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
    return (
      <div className='form-group'>
        <T.text 
          tag='input'
          type={ this.props.type} 
          placeholder={ T.translate(this.props.placeholder) } 
          className={ this.errors() } 
          onChange= { this.props.callback } />
      </div>
    )
  }
}
