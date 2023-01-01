import useFetch from '../../utils/useFetch';
import {
  fetchCreateComment,
  fetchDeleteComment,
  fetchPatchComment,
} from '../../utils/api';
import useInput from '../../utils/useInput';
import { useParams, Link } from 'react-router-dom';
import styled from 'styled-components';
import { useState } from 'react';
import { ModalComment } from './ModalComment';
import { Cookies } from 'react-cookie';

const Container = styled.div`
  /* border: 1px solid red; */
`;
const CommentPatch = styled.div``;
const CommentPost = styled.div`
  padding: 16px 8px 16px 0;
  .comment__post {
    display: flex;
    justify-content: space-between;
    gap: 12px;
  }
  input {
    border: 0;
    border-bottom: 1px solid #e6e9ea;
    background-color: #fff;
    padding: 0.6em 0.8em;
    flex-grow: 100;
    color: #3b4045;
    ::placeholder {
      color: #838c95;
    }
  }
  input:focus {
    border-color: #6bbbf7;
    outline: none;
  }
  .comment__post--button {
    height: fit-content;
    padding: 4px 8px;
    border-radius: 3px;
    cursor: pointer;
  }
`;
const CommentRead = styled.div`
  border-top: 1px solid #e6e9ea;
  box-sizing: border-box;
  .comment__read {
  }
  .comment__read--id {
    padding: 12px 8px;
    border-bottom: 1px solid #e6e9ea;
  }
  .comment__read--head {
    display: flex;
    justify-content: space-between;
    margin-bottom: 12px;
  }
  .comment__read--user {
    font-weight: 500;
    color: #6a737c;
    :hover {
      color: #0074cc;
    }
  }
  .comment__read--buttoncontainer {
    display: flex;
    flex-direction: row;
    gap: 8px;
    button {
      cursor: pointer;
    }
  }
  .comment__read--text {
    color: #232629;
  }
  .comment__read--text {
  }
  .comment__delete--button {
    border-radius: 3px;
    height: fit-content;
  }

  .hide {
    display: none !important;
  }
`;
const Text = styled.div``;

function CommentCreateDetail() {
  const cookies = new Cookies();
  const memberId = Number(cookies.get('memberId'));

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
                    <div className="comment__read--head">
                      <Link
                        to={`/users/${el.memberId}`}
                        className="comment__read--user"
                      >
                        {el.memberNick}
                      </Link>
                      <div
                        className={
                          memberId === el.memberId
                            ? 'comment__read--buttoncontainer'
                            : 'comment__read--buttoncontainer hide'
                        }
                      >
                        <ModalComment
                          onClick={(e) => toggleInput(el.commentId)}
                          commentId={el.commentId}
                          id={id}
                          commentContent={commentContent}
                          bindCommentContent={bindCommentContent}
                          resetCommentContent={resetCommentContent}
                        />
                        <button
                          onClick={(e) => deleteForm(el.commentId)}
                          className="comment__delete--button btn-style2"
                        >
                          삭제
                        </button>
                      </div>
                    </div>
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
              <button
                className="comment__post--button btn-style1"
                type="submit"
              >
                제출
              </button>
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
