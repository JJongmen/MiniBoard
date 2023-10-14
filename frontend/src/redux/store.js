import { configureStore } from '@reduxjs/toolkit';
import snackbarReducer from './snackbarSlice';

export const store = configureStore({
  reducer: {
    snackbar: snackbarReducer,
  },
});

export default store;