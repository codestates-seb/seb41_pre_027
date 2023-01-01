import ToastAnswer from '../toast/ToastAnswer';
import styled from 'styled-components';
import { useParams } from 'react-router';
import useFetch from '../../utils/useFetch';
import useInput from '../../utils/useInput';
import { fetchCreateAnswer, fetchDeleteAnswer } from '../../utils/api';
import ToastAnswerPatch from '../toast/ToastAnswerPatch';
import { Link } from 'react-router-dom';
import { modifyActions } from '../../Redux/modify';
import { useDispatch } from 'react-redux';
import { Viewer } from '@toast-ui/react-editor';

const AnswerPost = styled.div``;
const AnswerRead = styled.div`
  .answer__read {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  .answer__read--id {
    background: #f7f7f7;
    padding: 12px 12px 0;
    box-sizing: border-box;
    border-radius: 5px;
  }
  .answer__author {
    font-weight: 500;
    color: #6a737c;
    :hover {
      color: #0074cc;
    }
  }
  .hide {
    display: none !important;
  }
`;
const Container = styled.div`
  .answer__label {
    margin-top: 20px;
    margin-bottom: 20px;
    line-height: 1.3;
  }
  span {
    font-size: 17px;
  }
  .answer__submit--button {
    background-color: #0a95ff;
    color: white;
  }
  .answer__buttoncontainer {
    display: flex;
    gap: 4px;
    button {
      cursor: pointer;
      border-radius: 3px;
    }
  }
`;
function AnswerDetail(memberId) {
  const dispatch = useDispatch();
  const { id } = useParams();
  const answer = useFetch(`/api/questions/${id}/answers`);
  const getanswer = answer[0];

  const [answerContent, bindAnswerContent, resetAnswerContent] = useInput('');
  const deleteForm = (answerId) => {
    alert(`${answerId}`);
    fetchDeleteAnswer(
      process.env.REACT_APP_DB_HOST + `/api/answers/${answerId}`,
      id
    );
  };

  return (
    <>
      <Container>
        <AnswerRead>
          <div className="answer__read">
            {getanswer &&
              getanswer.map((el) => {
                return (
                  <div className="answer__read--id" key={el.answerId}>
                    <div className="answer__author">
                      <Link to={`/users/${el.memberId}`}>{el.memberNick}</Link>
                    </div>
                    <div className="answer__read--text">
                      <Viewer initialValue={el.text} />
                    </div>
                    <div
                      className={
                        memberId === el.memberId
                          ? 'answer__buttoncontainer'
                          : 'answer__buttoncontainer hide'
                      }
                    >
                      <Link to={'/patch/answer'}>
                        <button
                          className="btn-style2"
                          type="button"
                          onClick={() => {
                            dispatch(modifyActions.modifyAnswer(el.answerId));
                          }}
                        >
                          수정
                        </button>
                      </Link>
                      <button
                        className="btn-style2"
                        type="button"
                        onClick={(e) => deleteForm(el.answerId)}
                      >
                        삭제
                      </button>
                    </div>
                  </div>
                );
              })}
          </div>
        </AnswerRead>
        <AnswerPost>
          <div className="answer__label">
            <span>Your Answer</span>
          </div>
          <div className="answer__content">
            <ToastAnswer />
          </div>
        </AnswerPost>
      </Container>
    </>
  );
}

export default AnswerDetail;
