import { createSlice } from '@reduxjs/toolkit';

const memberId = localStorage.getItem('memberId');

const initialAuthState = {
  isAuthenticated: memberId ? true : false,
};

const authSlice = createSlice({
  name: 'authentication',
  initialState: initialAuthState,
  reducers: {
    login(state) {
      state.isAuthenticated = true;
    },
    logout(state) {
      state.isAuthenticated = false;
    },
  },
});

export const authActions = authSlice.actions;

export default authSlice.reducer;
