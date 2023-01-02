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
    margin-top: 30px;
  }
  .answer__length {
    font-size: large;
    margin-bottom: 40px;
  }
  .answer__read--id {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #e6e9ea;
    margin-top: 12px;
  }
  .answer__read--patchdelete {
    display: flex;
    align-items: center;
  }
  .answer__read--patch {
    margin-right: 5px;
  }
  .answer__read--delete {
  }
  button {
    height: 24px;
    color: #3e76ab;
    background-color: #e1ecf4;
    border: 1px solid #7aa7c7;
    border-radius: 3px;
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
            <div className="answer__length">{getanswer.length} Answer</div>
            {getanswer &&
              getanswer.map((el) => {
                return (
                  <div className="answer__read--id" key={el.answerId}>
                    <div className="answer__read--left">
                      <div className="">{el.memberNick}</div>
                      <Viewer initialValue={el.text} />
                    </div>

                    <div className="answer__read--patchdelete">
                      <Link to={'/patch/answer'}>
                        <div className="answer__read--patch">
                          <button
                            onClick={() => {
                              dispatch(modifyActions.modifyAnswer(el.answerId));
                            }}
                          >
                            수정
                          </button>
                        </div>
                      </Link>
                      <div className="answer__read--delete">
                        <button onClick={(e) => deleteForm(el.answerId)}>
                          삭제
                        </button>
                      </div>
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
