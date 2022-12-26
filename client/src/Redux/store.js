import { configureStore } from '@reduxjs/toolkit';

import authReducer from './auth';
import windowWidthReducer from './windowWidth';

const store = configureStore({
  reducer: { auth: authReducer, windowWidth: windowWidthReducer },
});

export default store;
