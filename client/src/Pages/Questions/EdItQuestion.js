import EditBoard from '../../Components/Question/EdItBoard';
function EditQuestion({ boards, setBoard }) {
  return (
    <>
      <div>게시글 수정</div>
      <EditBoard boards={boards} setBoard={setBoard}>
        이렇게 하는건 어때
      </EditBoard>
    </>
  );
}

export default EditQuestion;
