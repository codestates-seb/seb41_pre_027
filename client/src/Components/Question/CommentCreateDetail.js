import useFetch from '../../utils/useFetch';
import { fetchCreateComment } from '../../utils/api';
import useInput from '../../utils/useInput';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { useEffect, useState } from 'react';
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
const CommentRead = styled.div``;
const Text = styled.div``;
function CommentCreateDetail() {
  const { id } = useParams();
  const [commentContent, setcommentContent] = useState('');

  const handleInputChange = (event) => {
    setcommentContent(event.target.value);
  };

  useEffect(() => {
    console.log(commentContent);
  }, [commentContent]);

  const handleAnswerSubmit = () => {
    fetch(
      `http://ec2-43-201-73-28.ap-northeast-2.compute.amazonaws.com:8080/questions/${id}/answer`, //이 부분 써야함!!
      {
        method: 'POST',
        headers: {
          'Content-type': 'application/json',
          // Authorization: token,
        },
        body: JSON.stringify({
          commentContent,
        }),
      }
    )
      .then((res) => {
        if (!res.ok) {
          throw Error('could not fetch the data for that resource');
        }
        window.location.reload();
      })
      .catch((error) => {
        throw new Error(error);
      });
  };
  const onCheckEnter = (e) => {
    if (e.key === 'Enter') {
      handleAnswerSubmit();
      setcommentContent('');
      console.log('post');
    }
  };

  useEffect(() => {
    fetch(
      `http://ec2-43-201-73-28.ap-northeast-2.compute.amazonaws.com:8080/users/me`, //여기 작성해야 함
      {
        method: 'GET',
        headers: {
          'Content-type': 'application/json',
          //Authorization: initialToken,
        },
      }
    )
      .then((res) => {
        if (!res.ok) {
          throw Error('could not fetch the data for that resource');
        }
        return res.json();
      })
      .then((json) => {
        console.log('json', json);
        setcommentContent(json.text);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <>
      <CommentRead>
        <div className="comment__read">
          {/*지금 여기 map 부분때문에 input에 enter 치지도 않았는데 새로고침된다.*/}
          {commentContent &&
            commentContent.map((el) => {
              <div className="comment__read--id" key={el.id}>
                <div className="comment__read--text">{el.text}</div>
              </div>;
            })}
        </div>
      </CommentRead>
      <CommentPost>
        <div className="comment__post">
          <input
            type="text"
            placeholder="Add a comment"
            value={commentContent}
            onChange={handleInputChange}
            onKeyDown={onCheckEnter}
          ></input>
        </div>
      </CommentPost>
    </>
  );
}
export default CommentCreateDetail;

/* <div>
        {comments.map((comment) => {
          <div className="comment__preview" key={comment.id}>
            <div className="comment__list">{comment.comment}</div>
          </div>;
        })}
      </div> */
