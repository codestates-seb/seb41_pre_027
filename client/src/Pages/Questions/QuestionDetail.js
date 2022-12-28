import { useParams } from 'react-router-dom';
import Loading from '../../Components/Loading';
import { fetchDelete } from '../../utils/api';
import useFetch from '../../utils/useFetch';
import useScrollTop from '../../utils/useScrollTop';
import styled from 'styled-components';
import CommentCreateDetail from '../../Components/Question/CommentCreateDetail';
import AnswerDetail from '../../Components/Question/AnswerDetail';

const Content = styled.div`
  .detail__header {
    margin-top: 25px;
    padding-bottom: 13px;
    border-bottom: 1px solid #e6e9ea;
  }
  .detail__title {
    margin-bottom: 13px;
  }
  .detail__body {
    margin-top: 20px;
    line-height: 1.4rem;
  }
  span {
    font-size: 13px;
  }
`;
const Dates = styled.div`
  display: flex;
  .date__asked {
    margin-right: 8px;
  }
  .date__modified {
    margin-right: 8px;
  }
  span {
    margin-right: 5px;
    color: #939596;
  }
`;
const Count = styled.div``;
const Clicks = styled.div`
  display: flex;
  padding: 40px 0px;
  .detail__share {
    color: #748089;
    font-size: 12px;
    margin-right: 8px;
  }
  .detail__edit {
    color: #748089;
    font-size: 12px;
    margin-right: 8px;
  }
  .detail__follow {
    color: #748089;
    font-size: 12px;
    margin-right: 8px;
  }
`;

function BoardDetail() {
  const { id } = useParams();

  const [board, isPending, error] = useFetch(
    `http://localhost:4000/boards/${id}`
  );
  useScrollTop();

  const handleDelete = () => {
    fetchDelete(`http://localhost:4000/boards`, id);
  };

  // const handleUpdate = () => {
  //   let data = {}; //사용자가 업데이트 한 내용
  //   fetchPatch('http://localhost:4000/boards', id, data);
  // };
  return (
    <>
      <div className="board__details">
        {isPending && <Loading />}
        {error && <div>{error}</div>}
        {board && (
          <article>
            <Content>
              <div className="detail__header">
                <div className="detail__title">
                  <h2>{board.title}</h2>
                </div>
                <Dates>
                  <div className="date__asked">
                    <span>Asked 14years, 5 months ago</span>
                  </div>
                  <div className="date__modified">
                    <span>Modified 5 months ago</span>
                  </div>
                  <div className="date__viewed">
                    <span>Viewed 250k times</span>
                  </div>
                </Dates>
              </div>
              <div className="detail__body">
                <span>{board.body}</span>
              </div>
            </Content>
            {/* <button onClick={handleUpdate}>게시글 수정하기</button> */}
          </article>
        )}
        <Clicks>
          <div className="detail__share">
            <span>Share</span>
          </div>
          <div className="detail__edit">
            <span>Edit</span>
          </div>
          <div className="detail__follow">
            <span>Follow</span>
          </div>
        </Clicks>
        <CommentCreateDetail />
        <AnswerDetail />
      </div>
    </>
  );
}

export default BoardDetail;
