import useFetch from '../../utils/useFetch';
import { fetchCreateComment } from '../../utils/api';
import useInput from '../../utils/useInput';
import { useParams } from 'react-router-dom';
function CommentCreateDetail() {
  const { id } = useParams();
  const [comments, Pending, error, setComments] = useFetch(
    `http://localhost:4000/comments/`
  );
  const [comment, setComment] = useInput('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = { comment };
    fetchCreateComment('http://localhost:4000/comments/', data);
  };
  return (
    <>
      {/* <div>
        {comments.map((comment) => {
          <div className="comment__preview" key={comment.id}>
            <div className="comment__list">{comment.comment}</div>
          </div>;
        })}
      </div> */}
      <form onSubmit={handleSubmit}>
        <input placeholder="Add a comment"></input>
        <button onSubmit={handleSubmit}>제출</button>
      </form>
    </>
  );
}

export default CommentCreateDetail;
