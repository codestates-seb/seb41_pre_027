import useFetch from '../../utils/useFetch';
import {
  fetchCreateComment,
  fetchDeleteComment,
  fetchPatchComment,
} from '../../utils/api';
import useInput from '../../utils/useInput';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { useState } from 'react';
import { ModalComment } from './ModalComment';
const Container = styled.div``;
const CommentPatch = styled.div``;
const CommentPost = styled.div`
  .comment__post {
    border: none;
  }
  input {
    border: none;
    font-size: 9px;
    width: 400px;
    ::placeholder {
      color: #e4e6e7;
    }
  }
`;
const CommentRead = styled.div`
  .comment__read--text {
    border-bottom: 1px solid #f1f2f3;
    margin-bottom: 18px;
    padding: 20px;
  }
`;
const Text = styled.div``;

function CommentCreateDetail() {
  const { id } = useParams();
  //comment 가져오기
  const comment = useFetch(
    process.env.REACT_APP_DB_HOST + `/api/questions/${id}/comments`
  );
  const getcomment = comment[0]; //배열 안으로 접근
  //comment 생성하기
  const [commentContent, bindCommentContent, resetCommentContent] =
    useInput('');
  const [toggle, setToggle] = useState(false);

  function toggleInput(commentId) {
    setToggle(true);
    patchForm(commentId);
  }
  const submitForm = (e) => {
    e.preventDefault();
    fetchCreateComment(
      process.env.REACT_APP_DB_HOST + `/api/questions/${id}/comments`,
      id,
      {
        text: commentContent,
      }
    );
    resetCommentContent();
  };

  const deleteForm = (commentId) => {
    alert(`${commentId}`);
    fetchDeleteComment(
      process.env.REACT_APP_DB_HOST + `/api/comments/${commentId}`,
      id
    );
  };

  const patchForm = (commentId) => {
    alert(`${commentId}`);
    fetchPatchComment(
      process.env.REACT_APP_DB_HOST + `/api/comments/${commentId}`,
      id,
      {
        commentId: commentId,
        text: commentContent,
      }
    );
    resetCommentContent();
  };

  return (
    <>
      <Container>
        <CommentRead>
          <div className="comment__read">
            {getcomment &&
              getcomment.map((el) => {
                return (
                  <div className="comment__read--id" key={el.commentId}>
                    <span className="comment__read--user">{el.memberNick}</span>
                    <ModalComment
                      onClick={(e) => toggleInput(el.commentId)}
                      commentId={el.commentId}
                      id={id}
                      commentContent={commentContent}
                      bindCommentContent={bindCommentContent}
                      resetCommentContent={resetCommentContent}
                    />
                    <button onClick={(e) => deleteForm(el.commentId)}>
                      삭제
                    </button>
                    <div className="comment__read--text">{el.text}</div>
                  </div>
                );
              })}
          </div>
        </CommentRead>
        <CommentPost>
          <form onSubmit={(e) => submitForm(e)}>
            <div className="comment__post">
              <input
                type="text"
                placeholder="Add a comment"
                {...bindCommentContent}
              ></input>
              <button type="submit">제출</button>
            </div>
          </form>
        </CommentPost>
      </Container>
    </>
  );
}
export default CommentCreateDetail;

// const handleAnswerSubmit = () => {
//   fetch(`/api/questions/${id}/comments`, {
//     method: 'POST',
//     headers: {
//       'Content-type': 'application/json',
//       //Authorization: token,
//     },
//     body: JSON.stringify({
//       commentContent,
//     }),
//   })
//     .then((res) => {
//       if (!res.ok) {
//         throw Error('could not fetch the data for that resource');
//       }
//       window.location.reload();
//     })
//     .catch((error) => {
//       throw new Error(error);
//     });
// };

// fetchCreateComment(`/api/questions/${id}/comments`, id, {
//   text: '이거야',
// });
