import ToastAnswer from '../toast/ToastAnswer';
import styled from 'styled-components';
import ask from '../../assets/images/ask.png';

import Input from '../Input';
import useInput from '../../utils/useInput';
import { fetchCreate } from '../../utils/api';

const Container = styled.div`
  background-color: #f8f9f9;
`;

const Head = styled.div`
  display: flex;
  margin-left: 7rem;
  .head__title {
    font-size: larger;
    padding-top: 4rem;
    padding-right: 20rem;
  }
  .head__img {
  }
`;
const Description = styled.div`
  border: 1px solid #afd3ef;
  background-color: #ebf4fb;
  padding: 40px;
`;

// const Input = styled.input`
//   width: 90%;
//   margin: 0px 40px;
// `;
const Main = styled.div`
  margin-left: 7rem;
  margin-right: 40rem;
  .content {
    border: 1px solid black;
    margin: 13px;
    background-color: white;
  }
`;
function CreateBoard({ boards }) {
  const [title, setTitle] = useInput('');
  const [body, setBody] = useInput('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = { title, body };
    fetchCreate('http://localhost:4000/boards/', data);
  };
  return (
    <>
      <Container>
        <Head>
          <div className="head__title">
            <h2>Ask a public question</h2>
          </div>
          <div className="head__img">
            <img src={ask} alt="ask"></img>
          </div>
        </Head>
        <Main>
          <Description>
            <div>
              <h3>Writing a good question</h3>
              <br></br>
              <span>
                You’re ready to ask a programming-related question and this form
                will help guide you through the process.
              </span>
              <br></br>
              <span>
                Looking to ask a non-programming question? See the topics here
                to find a relevant site.
              </span>
              <br></br>
              <h4>steps</h4>
              <ul>
                <li>Summarize your problem in a one-line title.</li>
                <li>Describe your problem in more detail.</li>
                <li>
                  Describe what you tried and what you expected to happen.
                </li>
                <li>
                  Add “tags” which help surface your question to members of the
                  community.
                </li>
                <li>Review your question and post it to the site.s</li>
              </ul>
            </div>
          </Description>
          <form onSubmit={handleSubmit}>
            <div className="content">
              <div className="content__title">Title</div>
              <div className="content__title__description">
                Be specific and imagine you’re asking a question to another
                person.
              </div>
              <Input label={'제목'} value={setTitle}></Input>
            </div>
            <div>
              <h4>What are the details of your problem?</h4>
              <h5>
                Introduce the problem and expand on what you put in the title.
                Minimum 20 characters.
              </h5>
              <Input label={'내용'} value={setBody}></Input>
              {/* <ToastAnswer handleSubmit={handleSubmit} /> */}
            </div>
            <button onSubmit={handleSubmit}>제출</button>
          </form>
          <ToastAnswer />
        </Main>
      </Container>
    </>
  );
}

export default CreateBoard;
