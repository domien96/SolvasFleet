import React from 'react';
import { Route, IndexRoute } from 'react-router';

import Users from '../components/users/Users.tsx';
import AddUser from '../components/user_form/AddUser.tsx';
import EditUser from '../components/user_form/EditUser.tsx';
import User from '../components/user/User.tsx';
import NoUser from '../components/user/NoUser.tsx';

export default [
  <Route path="users/new" component={ AddUser } />,
  <Route path="users/:id/edit" component={ EditUser } />,
  <Route path="users" component={ Users } >
    <IndexRoute component={ NoUser } />
    <Route path=":id" component={ User } />
  </Route>
]
