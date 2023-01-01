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

const AnswerPost = styled.div``;
const AnswerRead = styled.div`
  .answer__read {
    margin-top: 30px;
  }
`;
const Container = styled.div`
  .answer__label {
    margin-top: 50px;
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
`;
function AnswerDetail() {
  const dispatch = useDispatch();
  const { id } = useParams();
  const answer = useFetch(`/api/questions/${id}/answers`);
  const getanswer = answer[0];
  console.log(getanswer);

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
                    <div className="answer__read--text">{el.text}</div>
                    <Link to={`/patch/answer/${el.answerId}`}>
                      <button
                        onClick={() => {
                          dispatch(modifyActions.modifyAnswer(el.answerId));
                        }}
                      >
                        수정
                      </button>
                    </Link>
                    <button onClick={(e) => deleteForm(el.answerId)}>
                      삭제
                    </button>
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
