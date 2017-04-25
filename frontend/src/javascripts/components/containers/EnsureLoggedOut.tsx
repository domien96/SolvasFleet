import React from 'react';

import Auth from '../../modules/Auth.ts';
import { redirect_to } from '../../routes/router.tsx';

class EnsureLoggedOutContainer extends React.Component<{}, {}> {
  componentDidMount() {
    if (Auth.isAuthenticated()) {
      redirect_to('/users');
    }
  }

  render() {
    if (!Auth.isAuthenticated()) {
      return (
        <div>
          { this.props.children }
        </div>
      );
    } else {
      return null;
    }
  }
}

export default EnsureLoggedOutContainer;
