import React    from 'react';
import Header     from '../../app/Header.tsx';

import { postFunction } from '../../../actions/user_actions.ts';
import { hasError } from '../../../utils/utils.ts';
import { redirect_to } from'../../../routes/router.tsx';
import T     from 'i18n-react';
import FunctionForm from './form/FunctionForm.tsx'

interface Props{
  params : { id : string };
}

interface State{
	errors : Form.Error[];
  Sfunctions: SFunctionData[];
}

class CreateUserFunction extends React.Component<Props, State> {
	constructor() {
    super();
    this.state = {Sfunctions: [{role: 1, company: -1}], errors: []}
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

	handleChange(field : SFunction.Field, e : any) : void {
    if(field == "role"){
      var newSfunctions : SFunctionData[] = this.state.Sfunctions.map((Sfunction : SFunctionData) => {
        return { role: e.target.value, company: Sfunction.company };
      });
      this.setState({ Sfunctions: newSfunctions });
    }
    if(field == "company"){
      var newSfunctions : SFunctionData[] = []
      for(let i = 0; i < e.length; i++){
        let newSfunction : SFunctionData = { role: this.state.Sfunctions[0].role, company: e[i].target.value }
        newSfunctions.push(newSfunction);
      }
      if(newSfunctions.length == 0) newSfunctions.push({role: this.state.Sfunctions[0].role, company: -1});
      this.setState({ Sfunctions: newSfunctions });
    }
  }

  onSubmit(e : any) : void {

    this.state.Sfunctions.map((Sfunction : SFunctionData) => {
      let newSfunction : SFunctionData = Sfunction;
      newSfunction['user'] = parseInt(this.props.params.id);
      if(!newSfunction.role){
        newSfunction['role'] = 1; //admin
      }
      if(!newSfunction.company){
        newSfunction['company'] = -1;
      }

      e.preventDefault();
      let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
      let success = () => redirect_to(`/users/${this.props.params.id}`);
      let fail = (data : any) => {
        console.log(data);
        setErrors(data.errors.map(function(e : any) {
          return { field: e, error: 'null' };
        }));
      }

      postFunction(parseInt(this.props.params.id), newSfunction, success, fail);
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

