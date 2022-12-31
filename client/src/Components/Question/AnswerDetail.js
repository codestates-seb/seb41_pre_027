import ToastAnswer from '../toast/ToastAnswer';
import styled from 'styled-components';
import { useParams } from 'react-router';
import useFetch from '../../utils/useFetch';
import useInput from '../../utils/useInput';
import {
  fetchCreateAnswer,
  fetchDeleteAnswer,
  fetchPatchAnswer,
} from '../../utils/api';
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
  const { id } = useParams();
  const answer = useFetch(`/api/questions/${id}/answers`);
  const getanswer = answer[0];
  console.log(getanswer);

  const [answerContent, bindAnswerContent, resetAnswerContent] = useInput('');
  const submitForm = (e) => {
    e.preventDefault();
    fetchCreateAnswer(
      process.env.REACT_APP_DB_HOST + `api/questions/${id}/answers`,
      id,
      {
        text: answerContent,
      }
    );
    resetAnswerContent();
  };
  const deleteForm = (answerId) => {
    alert('삭제');
    fetchDeleteAnswer(
      process.env.REACT_APP_DB_HOST + `/api/answers/${answerId}`,
      id
    );
  };
  const patchForm = (answerId) => {
    alert('수정');
    fetchPatchAnswer(
      process.env.REACT_APP_DB_HOST + `/api/answers/${answerId}`,
      id
      //데이터 들어가야함
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
                    <button onClick={(e) => patchForm(el.commentId)}>
                      수정
                    </button>
                    <button onClick={(e) => deleteForm(el.commentId)}>
                      삭제
                    </button>
                    {console.log('이거야', el.answerId)}
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
