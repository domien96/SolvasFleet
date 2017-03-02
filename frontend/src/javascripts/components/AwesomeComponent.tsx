import React from 'react';

import AwesomeState from '../types/AwesomeState';
import { INITIAL_LIKES } from '../constants/constants.ts';
import { uuid } from '../utils/utils.ts';

class AwesomeComponent extends React.Component<{}, AwesomeState> {

  constructor(props:{}) {
    super(props);
    console.log(uuid());
    this.state = {likesCount : INITIAL_LIKES as any};
    this.onLike = this.onLike.bind(this)
  }

  onLike () {
    let newLikesCount = this.state.likesCount + 1;
    this.setState({likesCount: newLikesCount});
  }

  render() {
    return (
      <div>
        Likes : <span>{this.state.likesCount}</span>
        <div><button onClick={this.onLike}>Like Me</button></div>
      </div>
    );
  }

}

export default AwesomeComponent;
