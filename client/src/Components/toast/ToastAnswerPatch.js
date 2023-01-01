import React, { useRef } from 'react';
import { fetchPatchAnswer } from '../../utils/api';

import styled from 'styled-components';
// Toast 에디터
import { Editor, Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'tui-color-picker/dist/tui-color-picker.css';
import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
import { useNavigate, useParams } from 'react-router';
import { useSelector } from 'react-redux';
import { Cookies } from 'react-cookie';
import axios from 'axios';

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

export default function ToastAnswerPatch() {
  const answerId = useSelector((state) => state.modify.answerId);
  console.log(answerId);
  const questionId = useSelector((state) => state.modify.questionId);
  console.log(questionId);

  const navigate = useNavigate();
  const cookies = new Cookies();

  const { id } = useParams();
  const editorRef = useRef();

  const submitFormtoast = (e) => {
    // alert(editorRef.current?.getInstance().getMarkdown());
    // alert(answerId);
    // fetchPatchAnswer(
    //   process.env.REACT_APP_DB_HOST + `/api/answers/${answerId}`,
    //   questionId,
    //   {
    //     answerId: answerId,
    //     text: editorRef.current?.getInstance().getMarkdown(),
    //   }
    // );

    e.preventDefault();
    const sendPatch = async () => {
      try {
        await axios.patch(
          process.env.REACT_APP_DB_HOST + `/api/answers/${answerId}`,
          {
            answerId: answerId,
            text: editorRef.current?.getInstance().getHTML(),
          },
          {
            headers: {
              Authorization: cookies.get('Authorization'),
            },
          }
        );
        setTimeout(() => {
          navigate(`/questions/${questionId}`);
          window.location.reload();
        }, 150);
      } catch (error) {
        console.log(error);
        alert('요청실패');
      }
    };
    sendPatch();
  };

  return (
    <>
      <Viewer />
      <Editor
        previewStyle="tab"
        toolbarItems={[
          ['heading', 'bold', 'italic'],
          ['code', 'codeblock'],
        ]}
        height="200px"
        initialEditType="markdown"
        hideModeSwitch
        initialValue=""
        ref={editorRef}
        language="ko-KR"
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
