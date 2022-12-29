import styled from 'styled-components';
import ask from '../../assets/images/ask.png';

import useInput from '../../utils/useInput';
import { fetchCreate } from '../../utils/api';
import ToastAsk from '../toast/ToastAsk';

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
  border-radius: 3px;
  background-color: #ebf4fb;
  padding: 25px 20px;
  h3 {
    font-size: 20px;
    font-weight: 400;
  }
  .description__head--detail {
    margin-top: 15px;
    font-size: 15px;
    line-height: 1.4rem;
    margin-bottom: 15px;
  }
  h4 {
    margin-bottom: 13px;
  }
  li {
    margin-left: 15px;
    line-height: 1.4rem;
  }
`;

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
  //const [title, setTitle] = useInput('');
  //const [body, setBody] = useInput('');

  // const handleSubmit = (e) => {
  //   e.preventDefault();
  //   const data = { title, body };
  //   fetchCreate('/api/questions/posting', data);
  // };
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
            <div className="description__head">
              <h3>Writing a good question</h3>
              <div className="description__head--detail">
                <span>
                  You’re ready to ask a programming-related question and this
                  form will help guide you through the process.
                </span>
                <div>
                  <span>
                    Looking to ask a non-programming question? See the topics
                    here to find a relevant site.
                  </span>
                </div>
              </div>
              <div className="description__tail">
                <h4>Steps</h4>
                <ul>
                  <li>Summarize your problem in a one-line title.</li>
                  <li>Describe your problem in more detail.</li>
                  <li>
                    Describe what you tried and what you expected to happen.
                  </li>
                  <li>
                    Add “tags” which help surface your question to members of
                    the community.
                  </li>
                  <li>Review your question and post it to the site.s</li>
                </ul>
              </div>
            </div>
          </Description>
          {/* <form onSubmit={handleSubmit}>
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
          {/* </div>
            <button onSubmit={handleSubmit}>제출</button>
          </form> */}
          <ToastAsk />
        </Main>
      </Container>
    </>
  );
}

export default CreateBoard;
