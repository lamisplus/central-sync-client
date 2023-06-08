import React from 'react';
import PropTypes from 'prop-types';

const Progress = ({ percentage }) => {
  return (
    <div className='progress'>
      <div
        className='progress-bar progress-bar-striped bg-success'
        role='progressbar'
        style={{ width: `${percentage}%`, height: '80px' }}

      >
        {percentage}%
      </div>
    </div>
  );
};

Progress.propTypes = {
  percentage: PropTypes.number.isRequired
};

export default Progress;
