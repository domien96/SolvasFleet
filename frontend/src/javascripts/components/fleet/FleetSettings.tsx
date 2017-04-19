import React from 'react';


interface Props {
  test:string
}

const FleetSettings : React.StatelessComponent<Props> = props => {

  return (
      <div className='actions pull-right'>
        <h3>
          <span className='glyphicon glyphicon-cog' />
        </h3>
      </div>
  );
}

export default FleetSettings;
