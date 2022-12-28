import { useRef } from 'react';
import styled from 'styled-components';
// Toast 에디터
import { Editor } from '@toast-ui/react-editor';
// import '@toast-ui/editor/dist/toastui-editor.css';

const Container = styled.div`
  border: 1px solid black;
  background-color: white;
`;

const Button = styled.div`
  margin-top: 12px;
  button {
    background-color: #81c7fc;
    color: white;
    border: none;
    border-radius: 5px;
    width: 8rem;
    height: 2rem;
  }
`;

export default function ToastAnswer() {
  const editorRef = useRef();
  // 등록 버튼 핸들러
  const handleRegisterButton = () => {
    // 입력창에 입력한 내용을 MarkDown 형태로 취득
    console.log(editorRef.current?.getInstance().getMarkdown());
  };

  return (
    <>
      <Container>
        <Editor
          ref={editorRef}
          placeholder={`내용을 입력해주세요`}
          previewStyle="vertical" // 미리보기 스타일 지정
          height="300px" // 에디터 창 높이
          initialEditType="" // 초기 입력모드 설정(디폴트 markdown)
          toolbarItems={[
            // 툴바 옵션 설정
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task', 'indent', 'outdent'],
            ['table', 'image', 'link'],
            ['code', 'codeblock'],
          ]}
          useCommandShortcut={false}
        ></Editor>
      </Container>
      <Button>
        <button onClick={handleRegisterButton}>Review your question</button>
      </Button>
    </>
  );
}
