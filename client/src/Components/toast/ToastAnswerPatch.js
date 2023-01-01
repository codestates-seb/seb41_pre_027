import React from 'react';
import { fetchPatchAnswer } from '../../utils/api';

import { useRef } from 'react';
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
    font-size: 11px;
    background-color: #0a95ff;
    color: white;
    border: none;
    border-radius: 3px;
    width: 120px;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
`;

export default function ToastAnswerPatch(answerId) {
  const { id } = useParams();
  const editorRef = useRef();

  const submitFormtoast = () => {
    // alert(editorRef.current?.getInstance().getMarkdown());
    alert(answerId);
    fetchPatchAnswer(
      process.env.REACT_APP_DB_HOST + `/api/answers/${answerId}`,
      id,
      {
        answerId: answerId,
        text: editorRef.current?.getInstance().getMarkdown(),
      }
    );
  };

  return (
    <>
      <Viewer />
      <Editor
        previewStyle="vertical"
        toolbarItems={[['bold', 'italic'], ['ul', 'ol'], ['link']]}
        height="400px"
        initialEditType="markdown"
        hideModeSwitch
        initialValue=""
        ref={editorRef}
        language="ru-RU"
        useCommandShortcut={true}
      ></Editor>

      <Button>
        <form>
          <button type="submit" onClick={(e) => submitFormtoast(e)}>
            수정하기
          </button>
        </form>
      </Button>
    </>
  );
}
