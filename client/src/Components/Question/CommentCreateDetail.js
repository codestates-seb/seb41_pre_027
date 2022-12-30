import useFetch from '../../utils/useFetch';
import { fetchCreateComment } from '../../utils/api';
import useInput from '../../utils/useInput';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
const Container = styled.div``;
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
  const comment = useFetch(`/api/questions/${id}/comments`);
  const getcomment = comment[0];
  //comment 생성하기
  const commentContent = useInput('');

  const submitForm = () => {
    fetchCreateComment(`/api/questions/1/comments`, id, {
      text: '올라가라',
    });
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
                    <div className="comment__read--text">{el.text}</div>
                  </div>
                );
              })}
          </div>
        </CommentRead>
        <CommentPost>
          <form onSubmit>
            <div className="comment__post">
              <input
                type="text"
                placeholder="Add a comment"
                {...commentContent}
              ></input>
              <button type="submit" onClick={submitForm}>
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
