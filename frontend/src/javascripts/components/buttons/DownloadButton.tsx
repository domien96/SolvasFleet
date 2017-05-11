import React from 'react';

interface DownloadButtonProps {
  onDownload: () => void;
  label: string;
}

const DownloadButton: React.StatelessComponent<DownloadButtonProps> = props => {
  return (
    <div>
      <button onClick={ props.onDownload } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon glyphicon-download-alt' /> { props.label }
      </button>
    </div>
  );
};

export default DownloadButton;