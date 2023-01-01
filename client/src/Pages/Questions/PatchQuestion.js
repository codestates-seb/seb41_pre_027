import styled from 'styled-components';
import ToastPatch from '../../Components/toast/ToastPatch';
const Container = styled.div`
  flex-basis: calc(100% - 390px);
  padding: 40px 24px 0 24px;
  border-left: 1px solid #d8d9dc;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  .alert {
    border: 1px solid #e6cf7a;
    background-color: #fdf7e2;
    border-radius: 3px;
    padding: 20px;
    color: #3b4045;
    line-height: 1.4;
  }
  .alert__title {
    margin-bottom: 16px;
  }

  @media screen and (max-width: 1200px) {
    padding: 24px 16px;
    width: 100%;
    flex: 1 0 0;
  }
`;

function PatchQuestion() {
  return (
    <Container>
      <div className="alert">
        <div className="alert__title">
          <span>
            Your edit will be placed in a queue until it is peer reviewde.
          </span>
        </div>
        <div className="alert__body">
          <span>
            We welcome edits that make the post easier to understand and more
            valuable for readers.Because community members revies edits, please
            try to make the post substantially better than how you found it, for
            example, by fixing grammer or adding additional resources and
            hyperlinks.
          </span>
        </div>
      </div>
      <ToastPatch />
    </Container>
  );
}

export default PatchQuestion;
