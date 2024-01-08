import React, { useRef, useState } from 'react';
import Button from '@mui/material/Button';
import FileCopyIcon from '@mui/icons-material/FileCopy';
import { Typography } from '@material-ui/core';

const CopyToClipboardButton = ({ styles, textToCopy, copiedStateText="Copied!", buttonText = 'Copy to Clipboard' }) => {
    const [isCopied, setIsCopied] = useState(false);
  const textRef = useRef(null);

  const handleCopyClick = async () => {
    try {
      await navigator.clipboard.writeText(textToCopy);
      // Optionally, focus on the text element after copying
      if (textRef.current) {
        textRef.current.focus();
        setIsCopied(true);
      }
    } catch (err) {
        setIsCopied(false);
        console.error('Unable to copy text to clipboard:', err);
    }
  };

  return (
    <div>
      <Button className={{styles}} onClick={handleCopyClick} variant="contained" color={isCopied ? "success" : "primary"}>
        <FileCopyIcon/>
        <Typography style={{marginLeft: "5px"}}>
        {isCopied ? copiedStateText : buttonText}
        </Typography>
      </Button>
      <input
        ref={textRef}
        type="text"
        value={textToCopy}
        readOnly
        style={{ position: 'absolute', left: '-9999px' }}
      />
    </div>
  );
};

export default CopyToClipboardButton;
