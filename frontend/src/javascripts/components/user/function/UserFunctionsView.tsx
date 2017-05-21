import React from 'react';

import { th } from '../../../utils/utils.ts';
import ExtendedInfoTable from '../../tables/ExtendedInfoTable.tsx';
import { Link } from 'react-router';
import T from 'i18n-react';
import DynamicGuiComponent from '../../app/DynamicGuiComponent.tsx';
import Auth from '../../../modules/Auth.ts';

interface OverviewProps {
  Sfunctions: SFunctionData[];
  onFunctionDelete: (id: number) => void;
  onGetCompanyName: (id: number) => void;
  onGetRoleName: (id: number) => void;
}

const Overview: React.StatelessComponent<OverviewProps> = props => {
  const tableHead = [
    th('role', 'function.role'),
    th('company', 'function.company'),
  ];

  const data = props.Sfunctions.map((f: SFunctionData) => {
    return {
      id: f.id,
      company: props.onGetCompanyName(f.company),
      role: props.onGetRoleName(f.role),
      user: f.user
    }
  });

  return (
    <ExtendedInfoTable head={ tableHead } data={ data } onDelete={ props.onFunctionDelete } />
  );
};

interface Props {
  userId: number;
  Sfunctions: SFunctionData[];
  onFunctionDelete: (id: number) => void;
  onGetCompanyName: (id: number) => void;
  onGetRoleName: (id: number) => void;
}

const UserFunctionsView: React.StatelessComponent<Props> = props => {
  return (
    <DynamicGuiComponent authorized={ Auth.canReadRoles() }>
      <div>
        <DynamicGuiComponent authorized={ Auth.canWriteFunctions() }>
          <Link to={ `/users/${props.userId}/functions/new` } className='btn btn-default pull-right'>
            <span className='glyphicon glyphicon-plus' aria-hidden='true'/> { T.translate('function.addNew') }
          </Link>
        </DynamicGuiComponent>
        <DynamicGuiComponent authorized={ Auth.canReadFunctions() }>
          <h3>
            { T.translate('function.functions') }
          </h3>
          <Overview
            Sfunctions={ props.Sfunctions }
            onFunctionDelete={ props.onFunctionDelete }
            onGetCompanyName={ props.onGetCompanyName }
            onGetRoleName={ props.onGetRoleName } />
        </DynamicGuiComponent>
      </div>
    </DynamicGuiComponent>
  );
};

export default UserFunctionsView;
