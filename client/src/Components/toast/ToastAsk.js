import { useRef } from 'react';
import styled from 'styled-components';
// Toast 에디터
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
//
import useInput from '../../utils/useInput';
import { useParams } from 'react-router';
import { Viewer } from '@toast-ui/react-editor';
import { fetchCreate } from '../../utils/api';
import '@toast-ui/editor/dist/i18n/ko-kr';
const BigContainer = styled.div`
  .content {
    background-color: white;
    border: 1px solid #edeff1; //이렇게 바꿨다가도 다시 새로고침하면 이상하게 됨
    padding: 30px 30px 20px 20px;
    border-radius: 5px;
    margin: 20px 0px;
  }
  .content__container {
    margin-bottom: 8px;
  }
  .content__title {
    margin-top: -2px;
    margin-bottom: 5px;
  }
  .content__title__description {
  }
  input {
    width: 100%;
    border: 1px solid #c3c7cb;
    border-radius: 3px;
    height: 30px;
  }
`;
const Container = styled.div`
  border: 1px solid #edeff1;
  background-color: white;
  padding: 30px 25px;
  border-radius: 5px;
  h3 {
    margin-top: -8px;
    margin-bottom: 4px;
  }
  .ask__body {
    margin-bottom: 7px;
  }
`;

const Button = styled.div`
  margin-top: 12px;
  button {
    background-color: #81c7fc;
    color: white;
    border: none;
    border-radius: 5px;
    width: 180px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 15px;
    margin-bottom: 80px;
  }
`;
//
export default function ToastAsk() {
  const { id } = useParams();
  // Editor DOM 선택용
  const editorRef = useRef();
  const [askTitle, bindAskTitle, resetAskTitle] = useInput('');
  // 등록 버튼 핸들러
  const handleRegisterButton = (e) => {
    // 입력창에 입력한 내용을 HTML 태그 형태로 취득
    alert(editorRef.current?.getInstance().getMarkdown());
    fetchCreate(process.env.REACT_APP_DB_HOST + `/api/questions/posting`, id, {
      title: askTitle,
      text: editorRef.current?.getInstance().getMarkdown(),
    });
  };
  return (
    <div>
      <BigContainer>
        <form onSubmit={handleRegisterButton} name="asksubmit">
          <div className="content">
            <div className="content__container">
              <h3 className="content__title">Title</h3>
              <span className="content__title__description">
                Be specific and imagine you’re asking a question to another
                person.
              </span>
            </div>
            <div>
              <input type="text" {...bindAskTitle}></input>
            </div>
          </div>
          <Container>
            <div className="ask__body">
              <h3>What are the details of your problem?</h3>
              <span className="ask__body--detail">
                Introduce the problem and expand on what you put in the
                title.Minimum 20 characters.
              </span>
            </div>
            <Viewer />
            <Editor
              ref={editorRef} // DOM 선택용 useRef
              placeholder="내용을 입력해주세요."
              previewStyle="vertical" // 미리보기 스타일 지정
              height="300px" // 에디터 창 높이
              initialEditType="markdown" //
              toolbarItems={[
                // 툴바 옵션 설정
                ['heading', 'bold', 'italic', 'strike'],
                ['hr', 'quote'],
                ['ul', 'ol', 'task', 'indent', 'outdent'],
                ['table', 'image', 'link'],
                ['code', 'codeblock'],
              ]}
              useCommandShortcut={false} // 키보드 입력 컨트롤 방지
              language="ko-KR"
            ></Editor>
          </Container>
          <Button>
            <button type="submit" onClick={handleRegisterButton}>
              Review your question
            </button>
          </Button>
        </form>
      </BigContainer>
    </div>
  );
}
