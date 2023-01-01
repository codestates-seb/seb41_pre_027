import { createSlice } from '@reduxjs/toolkit';

const initialModifyState = {
  answerId: 0,
  questionId: 0,
};

const modifySlice = createSlice({
  name: 'modify',
  initialState: initialModifyState,
  reducers: {
    modifyAnswer(state, action) {
      state.answerId = action.payload;
    },
    modifyQuestion(state, action) {
      state.questionId = action.payload;
    },
  },
});

export const modifyActions = modifySlice.actions;

export default modifySlice.reducer;
