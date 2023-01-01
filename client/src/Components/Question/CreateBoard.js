import styled from 'styled-components';
import ask from '../../assets/images/ask.png';

import ToastAsk from '../toast/ToastAsk';

const Container = styled.div`
  background-color: #f8f9f9;
`;

const Head = styled.div`
  display: flex;
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
  margin-right: 40rem;
`;
function CreateBoard() {
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
          <ToastAsk />
        </Main>
      </Container>
    </>
  );
}

export default CreateBoard;
