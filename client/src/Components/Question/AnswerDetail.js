import ToastAnswer from '../toast/ToastAnswer';
import styled from 'styled-components';
import { useParams } from 'react-router';
import useFetch from '../../utils/useFetch';
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
