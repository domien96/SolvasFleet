import React from 'react';
import Header from '../../app/Header.tsx';

import { postFunction } from '../../../actions/user_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from '../../../routes/router.tsx';
import T from 'i18n-react';
import FunctionForm from './form/FunctionForm.tsx';

interface Props {
  params: { id: string };
}

interface State{
	errors: Form.Error[];
  Sfunctions: SFunctionData[];
}

class CreateUserFunction extends React.Component<Props, State> {
  constructor() {
    super();
    this.state = { Sfunctions: [{role: 1, company: -1}], errors: [] };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

	handleChange(field: SFunction.Field, e: any) {
    if (field === "role"){
      var newSfunctions: SFunctionData[] = this.state.Sfunctions.map((Sfunction: SFunctionData) => {
        return { role: e.target.value, company: Sfunction.company };
      });
      this.setState({ Sfunctions: newSfunctions });
    }
    if (field === "company"){
      var newSfunctions: SFunctionData[] = []
      if (e.length > 1) {
        newSfunctions = e.map((elem: any) => {
          return { role: this.state.Sfunctions[0].role, company: elem.target.value };
        });
        newSfunctions = newSfunctions.filter((elem: any) => {
          return elem.company !== -1;
        });
      } else if (e.length !== 0) {
        newSfunctions = [{ role: this.state.Sfunctions[0].role, company: e.target.value }];
      }
      if (newSfunctions.length === 0) {
        newSfunctions = [{ role: this.state.Sfunctions[0].role, company: -1 }];
      }
      this.setState({ Sfunctions: newSfunctions });
    }
  }

  onSubmit(e: any) {
    this.state.Sfunctions.map((Sfunction: SFunctionData) => {
      e.preventDefault();
      let newSfunction: SFunctionData = Sfunction;
      newSfunction['user'] = parseInt(this.props.params.id, 10);
      let setErrors = (e: Form.Error[]) => this.setState({ errors: e });
      let success = () => redirect_to(`/users/${this.props.params.id}`);
      let fail = (data: any) => {
        setErrors(data.errors.map(function(e: any) {
          return { field: e, error: 'null' };
        }));
      }
      postFunction(parseInt(this.props.params.id, 10), newSfunction, success, fail);
    })
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('function.createNew') }</h2>
        </Header>
        <FunctionForm
          Sfunctions={ this.state.Sfunctions }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default CreateUserFunction;
