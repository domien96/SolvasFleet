import React from 'react';
import { Route, IndexRoute } from 'react-router';

import Users from '../components/users/Users.tsx';
import AddUser from '../components/user_form/AddUser.tsx';
import EditUser from '../components/user_form/EditUser.tsx';
import User from '../components/user/User.tsx';
import NoUser from '../components/user/NoUser.tsx';

export default [
  <Route key={ 1 } path="users/new" component={ AddUser } />,
  <Route key={ 2 } path="users/:id/edit" component={ EditUser } />,
  <Route key={ 3 } path="users" component={ Users } >
    <IndexRoute component={ NoUser } />
    <Route path=":id" component={ User } />
  </Route>
]
