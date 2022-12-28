import CreateBoard from '../../Components/Question/CreateBoard';
function AskQuestions({ boards }) {
  return (
    <>
      <CreateBoard boards={boards} />
    </>
  );
}
export default AskQuestions;
