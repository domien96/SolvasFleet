import React      from 'react';

class NoMatch extends React.Component<{}, {}> {
  render() {
    return (
      <div className='row'>
        <div className='col-lg-12 fof text-center'>
          <div>
            <h2>Something just went wrong!</h2>
            <h1>404 error</h1>
            <p>For Some Reason The Page You Requested Could Not Be Found On Our Server</p>
          </div>
        </div>
      </div>
    );
  }
}

export default NoMatch;
