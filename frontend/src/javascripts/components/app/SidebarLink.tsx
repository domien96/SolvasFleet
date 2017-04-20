import React from 'react';
import { Link } from 'react-router';
import classNames from 'classnames';

interface Props {
  path: string;
}

class SidebarLink extends React.Component<Props, {}> {
  static contextTypes = {
    location: React.PropTypes.object
  }

  render() {
    const classes = classNames({ active: this.context.location.pathname.includes(this.props.path) });

    return (
      <li className={ classes } >
        <Link to={ this.props.path }>
          { this.props.children }
        </Link>
      </li>
    )
  }
}

export default SidebarLink;
