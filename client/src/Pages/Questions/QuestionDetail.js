import { useParams, Link, useNavigate } from 'react-router-dom';
import Loading from '../../Components/Loading';
import useFetch from '../../utils/useFetch';
import useScrollTop from '../../utils/useScrollTop';
import styled from 'styled-components';
import CommentCreateDetail from '../../Components/Question/CommentCreateDetail';
import AnswerDetail from '../../Components/Question/AnswerDetail';
import SidebarWidget from '../../Components/Questions/SidebarWidget';
import { Viewer } from '@toast-ui/react-editor';
import { modifyActions } from '../../Redux/modify';
import { useDispatch } from 'react-redux';
import { Cookies } from 'react-cookie';
import axios from 'axios';

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
    padding: 24px 16px;
    .board__details {
      flex-basis: 100%;
    }
  }
`;
const Content = styled.div`
  .detail__header {
    padding-bottom: 13px;
    border-bottom: 1px solid #e6e9ea;
  }
  .detail__header--top {
    display: flex;
    justify-content: space-between;
  }
  .detail__title {
    margin-bottom: 15px;
    color: #3b4045;
    font-weight: 500;
    font-size: 2.07rem;
    line-height: 1.3;
  }
  .question__vote {
    display: flex;
    gap: 8px;
    button {
      height: fit-content;
      padding: 4px 8px;
      border-radius: 3px;
      cursor: pointer;
    }
  }
  .detail__body {
    margin-top: 20px;
    line-height: 1.4rem;
    font-size: 13px;
  }
`;
const Dates = styled.div`
  display: flex;
  gap: 20px;
  color: #939596;
  span {
    color: #3b4045;
    display: inline-block;
    margin-left: 4px;
  }
  @media screen and (max-width: 1200px) {
    gap: 8px;
    flex-direction: column;
  }
`;
const Clicks = styled.div`
  div {
    display: flex;
    padding: 16px 0px;
    gap: 4px;
  }
  button {
    border-radius: 3px;
    cursor: pointer;
  }
  .hide {
    display: none !important;
  }
`;

function BoardDetail() {
  const cookies = new Cookies();
  const memberId = Number(cookies.get('memberId'));
  const accessToken = cookies.get('Authorization');
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { id } = useParams();

  const [board, isPending, error] = useFetch(
    process.env.REACT_APP_DB_HOST + `/api/questions/${id}`
  );
  useScrollTop();

  const questionDeleteHandler = () => {
    const isDelete = confirm('게시물을 삭제할까요?');
    if (isDelete) {
      const deleteQuestion = async () => {
        try {
          await axios.delete(
            process.env.REACT_APP_DB_HOST + `/api/questions/${id}`,
            {
              headers: {
                Authorization: accessToken,
              },
              data: {},
            }
          );
          setTimeout(() => {
            navigate('/');
            window.location.reload();
          }, 250);
        } catch (error) {
          console.log(error);
          alert('요청 실패');
        }
      };
      deleteQuestion();
    }
  };

  const questionUprating = () => {
    const postUprating = async () => {
      try {
        await axios.post(
          process.env.REACT_APP_DB_HOST + `/api/questions/${id}/upratings`,
          '',
          {
            headers: {
              Authorization: accessToken,
            },
          }
        );
        alert('추천완료');
        window.location.reload();
      } catch (error) {
        console.log(error);
        alert('요청실패');
      }
    };
    postUprating();
  };
  const questionDownrating = () => {
    const postDownrating = async () => {
      try {
        await axios.post(
          process.env.REACT_APP_DB_HOST + `/api/questions/${id}/downratings`,
          '',
          {
            headers: {
              Authorization: accessToken,
            },
          }
        );
        alert('비추천완료');
        window.location.reload();
      } catch (error) {
        console.log(error);
        alert('요청실패');
      }
    };
    postDownrating();
  };

  return (
    <Container>
      <div className="board__details">
        {isPending && <Loading />}
        {error && <div>{error}</div>}
        {board && (
          <>
            <article>
              <Content>
                <div className="detail__header">
                  <div className="detail__header--top">
                    <h2 className="detail__title">{board.title}</h2>
                    <div className="question__vote">
                      <button
                        type="button"
                        className="btn-style1"
                        name="upratings"
                        onClick={questionUprating}
                      >
                        추천
                      </button>
                      <button
                        type="button"
                        className="btn-style2"
                        name="downratings"
                        onClick={questionDownrating}
                      >
                        비추천
                      </button>
                    </div>
                  </div>
                  <Dates>
                    <div>
                      Asked
                      <span>
                        {new Date(board.createdAt + 'Z').toLocaleString(
                          'ko-KR'
                        )}
                      </span>
                    </div>
                    <div>
                      Modified
                      <span>
                        {new Date(board.modifiedAt + 'Z').toLocaleString(
                          'ko-KR'
                        )}
                      </span>
                    </div>
                    <div>
                      Viewed<span>{board.views} times</span>
                    </div>
                    <div>
                      <span>{board.ratingScore}</span> rating
                    </div>
                  </Dates>
                </div>
                <div className="detail__body">
                  <Viewer initialValue={board.text} />
                </div>
              </Content>
            </article>

            <Clicks>
              <div
                className={
                  memberId === board.memberId
                    ? 'detail__buttoncontainer'
                    : 'detail__buttoncontainer hide'
                }
              >
                <Link to="/patch">
                  <button
                    type="button"
                    className="btn-style2"
                    onClick={() => {
                      dispatch(modifyActions.modifyQuestion(id));
                    }}
                  >
                    Edit
                  </button>
                </Link>
                <button
                  type="button"
                  className="btn-style2"
                  onClick={questionDeleteHandler}
                >
                  Delete
                </button>
              </div>
            </Clicks>
          </>
        )}
        <CommentCreateDetail />
        <AnswerDetail memberId={memberId} questionId={id} />
      </div>
      <SidebarWidget />
    </Container>
  );
}

export default BoardDetail;
