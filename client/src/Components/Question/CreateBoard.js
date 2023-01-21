import styled from 'styled-components';
import ask from '../../assets/images/ask.png';

import ToastAsk from '../toast/ToastAsk';

const Container = styled.div`
  width: 100%;
  @media screen and (max-width: 1200px) {
    padding: 24px 16px;
    box-sizing: border-box;
  }
`;

const Head = styled.div`
  display: flex;
  align-items: center;
  height: 130px;
  background: url(${ask}) no-repeat right bottom;
  background-size: auto 130px;
  h2 {
    font-size: 2.07rem;
    line-height: 1.3;
    letter-spacing: -0.025;
    color: #232629;
  }

  @media screen and (max-width: 1200px) {
    background: none;
    height: fit-content;
    margin-bottom: 12px;
    h2 {
      margin-bottom: 2.8rem;
    }
  }
`;
const Description = styled.div`
  margin-top: 16px;
  border: 1px solid #afd3ef;
  border-radius: 3px;
  background-color: #ebf4fb;
  padding: 24px;
  box-sizing: border-box;

  h3 {
    font-size: 1.5rem;
    letter-spacing: -0.025;
    line-height: 1.3;
    margin-bottom: 1em;
    font-weight: 400;
    margin-bottom: 12px;
  }
  .description__head--detail {
    margin-bottom: 24px;
    p {
      font-size: 1.15rem;
      line-height: 1.4;
      color: #3b4045;
      span {
        color: #0074cc;
      }
    }
  }
  h4 {
    margin-bottom: 12px;
    font-weight: 500;
  }
  li {
    margin-left: 32px;
    line-height: 1.4rem;
    list-style: disc;
    color: #3b4045;
  }

  @media screen and (max-width: 640px) {
    .description__head--detail {
      p {
        font-size: 1.3rem;
      }
    }
  }
`;

const Main = styled.div`
  margin-right: 30rem;
  @media screen and (max-width: 1200px) {
    margin-right: auto;
  }
`;
function CreateBoard() {
  return (
    <>
      <Container>
        <Head>
          <h2>Ask a public question</h2>
        </Head>
        <Main>
          <Description>
            <div className="description__head">
              <h3>Writing a good question</h3>
              <div className="description__head--detail">
                <p>
                  You’re ready to <span>ask</span> a
                  <span> programming-related question</span> and this form will
                  help guide you through the process.
                </p>
                <p>
                  Looking to ask a non-programming question? See
                  <span> the topics here</span> to find a relevant site.
                </p>
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
                  <li>Review your question and post it to the site.</li>
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
