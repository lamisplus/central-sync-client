import React, { useRef, useState } from 'react';
import Button from '@mui/material/Button';
import { Typography } from '@material-ui/core';
import { FileDownload } from '@mui/icons-material';

const DownloadButton = ({ styles, textToDownload, downloadedStateText="Downloaded!", buttonText = 'Download As File' }) => {
    const [isCopied, setIsCopied] = useState(false);
  const textRef = useRef(null);

  const handleButtonClick = async () => {
    try {
        const element = document.createElement("a");
        const file = new Blob([textToDownload], {type: 'text/plain'});
        element.href = URL.createObjectURL(file);
        element.download = "facilityKey.key";
        document.body.appendChild(element); // Required for this to work in FireFox
        element.click();
        setIsCopied(true);

    } catch (err) {
        setIsCopied(false);
        console.error('Unable to copy text to clipboard:', err);
    }
  };

  return (
    <div>
      <Button className={{styles}} onClick={handleButtonClick} variant="contained" color={isCopied ? "success" : "primary"}>
        <FileDownload/>
        <Typography style={{marginLeft: "5px"}}>
        {isCopied ? downloadedStateText : buttonText}
        </Typography>
      </Button>
      <input
        ref={textRef}
        type="text"
        value={textToDownload}
        readOnly
        style={{ position: 'absolute', left: '-9999px' }}
      />
    </div>
  );
};

export default DownloadButton;
