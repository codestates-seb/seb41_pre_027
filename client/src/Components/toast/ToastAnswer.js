import React, { useRef } from 'react';
import { fetchCreateAnswer } from '../../utils/api';

import styled from 'styled-components';
// Toast 에디터
import { Editor, Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import { useParams } from 'react-router';

const Container = styled.div`
  border: 1px solid black;
  background-color: white;
`;

const Button = styled.div`
  margin-top: 20px;
  button {
    border-radius: 3px;
    font-size: 1.05em;
    padding: 12px;
  }
`;

export default function ToastAnswer() {
  const { id } = useParams();
  const editorRef = useRef();

  const submitFormtoast = () => {
    fetchCreateAnswer(
      process.env.REACT_APP_DB_HOST + `/api/questions/${id}/answers`,
      id,
      {
        text: editorRef.current?.getInstance().getHTML(),
      }
    );
  };

  return (
    <>
      {/* <Viewer height="400px" initialValue={html} /> */}
      <Editor
        previewStyle="vertical"
        toolbarItems={[
          ['heading', 'bold', 'italic'],
          ['code', 'codeblock'],
        ]}
        height="200px"
        initialEditType="tab"
        hideModeSwitch
        initialValue=""
        ref={editorRef}
        language="ko-KR"
        useCommandShortcut={true}
      ></Editor>

      <Button>
        <form>
          <button
            type="submit"
            className="btn-style1"
            onClick={submitFormtoast}
          >
            Post Your Answer
          </button>
        </form>
      </Button>
    </>
  );
}
