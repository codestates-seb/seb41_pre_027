import ToastAnswer from '../toast/ToastAnswer';
import styled from 'styled-components';

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
  return (
    <>
      <Container>
        <div className="answer__label">
          <span>Your Answer</span>
        </div>
        <div className="answer__content">
          <ToastAnswer />
        </div>
      </Container>
    </>
  );
}

export default AnswerDetail;
