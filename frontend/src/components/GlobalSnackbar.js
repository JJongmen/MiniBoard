import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { closeSnackbar } from '../redux/snackbarSlice';

function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

export default function GlobalSnackbar() {
  const dispatch = useDispatch();
  const snackbar = useSelector((state) => state.snackbar);

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    dispatch(closeSnackbar());
  };

  return (
    <Snackbar open={snackbar.open} autoHideDuration={6000} onClose={handleClose}>
        <div>
            <Alert onClose={handleClose} severity={snackbar.severity}>
                {snackbar.message}
            </Alert>
        </div>
    </Snackbar>
  );
}