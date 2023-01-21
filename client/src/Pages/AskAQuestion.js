import React from 'react';
import CreateBoard from '../Components/Question/CreateBoard';

const AskAQuestion = ({ boards }) => {
  return <CreateBoard boards={boards} />;
};

export default AskAQuestion;
