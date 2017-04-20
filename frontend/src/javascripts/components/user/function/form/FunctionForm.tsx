import React    from 'react';

import Actions from '../../../forms/Actions.tsx';
import Errors from '../../../app/Errors.tsx';
import Info from './Info.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : SFunction.Field, e : any) => void;
  errors       : Form.Error[];
  hasError     : (field : SFunction.Field) => boolean;
  Sfunction         : SFunctionData;
  roles  : RoleData[];
  companies : Company[];
}

const FunctionForm : React.StatelessComponent<Props> = props => {
  var { Sfunction, roles, companies, onSubmit, handleChange, errors, hasError } = props;

  const submit = Sfunction.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ errors } />
          <Info Sfunction={ Sfunction } roles={ roles } companies={ companies } handleChange={ handleChange } hasError={ hasError } />
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ submit } cancelUrl='/auth' model='function' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default FunctionForm;
