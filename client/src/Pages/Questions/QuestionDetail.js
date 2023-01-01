import { useParams, Link } from 'react-router-dom';
import Loading from '../../Components/Loading';
import useFetch from '../../utils/useFetch';
import useScrollTop from '../../utils/useScrollTop';
import styled from 'styled-components';
import CommentCreateDetail from '../../Components/Question/CommentCreateDetail';
import AnswerDetail from '../../Components/Question/AnswerDetail';
import SidebarWidget from '../../Components/Questions/SidebarWidget';
import { Viewer } from '@toast-ui/react-editor';

const Container = styled.div`
  display: flex;
  width: 100%;
  gap: 24px;
  padding: 24px;
  border-left: 1px solid #d6d9dc;
  .board__details {
    margin-bottom: 80px;
    flex-basis: calc(100% - 300px - 24px);
  }

  @media screen and (max-width: 1200px) {
    .board__details {
      flex-basis: auto;
    }
  }
`;
const Content = styled.div`
  .detail__header {
    padding-bottom: 13px;
    border-bottom: 1px solid #e6e9ea;
  }
  .detail__title {
    margin-bottom: 15px;
  }
  .detail__body {
    margin-top: 20px;
    line-height: 1.4rem;
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

  const [board, isPending, error] = useFetch(`/api/questions/${id}`);
  useScrollTop();

  return (
    <Container>
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
                    <span>Modified {board.modifiedAt} months ago</span>
                  </div>
                  <div className="date__viewed">
                    <span>Viewed 250k times</span>
                  </div>
                </Dates>
              </div>
              <div className="detail__body">
                <Viewer initialValue={board.text} />
              </div>
            </Content>
          </article>
        )}
        <Clicks>
          <div className="detail__share">
            <span>Share</span>
          </div>
          <div className="detail__edit">
            <Link to="/patch/${id}">
              <span>Edit</span>
            </Link>
          </div>
          <div className="detail__follow">
            <span>Follow</span>
          </div>
        </Clicks>
        <CommentCreateDetail />
        <AnswerDetail />
      </div>
      <SidebarWidget />
    </Container>
  );
}

export default BoardDetail;
