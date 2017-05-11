import React from 'react';
import T from 'i18n-react';

const NoMatch: React.StatelessComponent<{}> = () => {
  return (
    <div className='row'>
      <div className='col-lg-12 fof text-center'>
        <div>
          <h2>{ T.translate('error.404') }</h2>
          <h1>404 error</h1>
          <p>{ T.translate('error.pageNotFound') }</p>
        </div>
      </div>
    </div>
  );
};

export default NoMatch;
