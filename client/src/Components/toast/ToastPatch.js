import { useRef, useState } from 'react';
import styled from 'styled-components';
// Toast 에디터
import { Editor, Viewer } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
//
import useInput from '../../utils/useInput';
import { useNavigate } from 'react-router';

import '@toast-ui/editor/dist/i18n/ko-kr';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { Cookies } from 'react-cookie';

const BigContainer = styled.div`
  padding-bottom: 80px;
  flex-basis: 0;
  box-sizing: border-box;
  .content {
    background-color: white;
    border: 1px solid #edeff1;
    padding: 24px;
    border-radius: 5px;
    margin: 20px 0px;
    box-sizing: border-box;
  }
  .content__container {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  .content__title {
    color: #0c0d0e;
    font-size: 1.15rem;
    line-height: 1.3;
  }
  .content__title__description {
    color: #3b4045;
    font-size: 0.92rem;
    line-height: 1.3;
  }

  input {
    width: 100%;
    border: 1px solid #babfc4;
    background-color: #fff;
    color: #3b4045;
    border-radius: 3px;
    padding: 0.6em 0.7em;
    box-sizing: border-box;
    ::placeholder {
      color: #babfc4;
    }
  }
  input:focus {
    border-color: #6bbbf7;
    box-shadow: 0 0 0 4px rgba(122, 167, 199, 0.15);
    outline: none;
  }

  @media screen and (max-width: 640px) {
    .content__container {
      gap: 4px;
    }
  }
`;
const Container = styled.div`
  border: 1px solid #edeff1;
  background-color: white;
  padding: 24px;
  border-radius: 5px;
  box-sizing: border-box;
  width: 100%;
  .ask__body {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 12px;
    h3 {
      color: #0c0d0e;
      font-size: 1.15rem;
      line-height: 1.3;
    }
    > span {
      color: #3b4045;
      font-size: 0.92rem;
      line-height: 1.3;
    }
  }

  @media screen and (max-width: 640px) {
    .ask__body {
      gap: 4px;
    }
  }
`;

const Button = styled.div`
  margin-top: 24px;
  button {
    border-radius: 3px;
    font-size: 1.05em;
    padding: 12px;
  }
`;
//
const ToastPatch = () => {
  const cookies = new Cookies();
  const navigate = useNavigate();
  const questionId = useSelector((state) => state.modify.questionId);

  // Editor DOM 선택용
  const editorRef = useRef();
  const [patchTitle, bindPatchTitle] = useInput('');
  const [inputTags, setInputTags] = useState('');

  const tagsChangeHandler = (event) => {
    setInputTags(event.target.value);
  };

  const postTags = async () => {
    try {
      const response = await axios.post(
        process.env.REACT_APP_DB_HOST + `/api/questions/${questionId}/tags`,
        {
          tagString: inputTags,
        },
        {
          headers: {
            Authorization: cookies.get('Authorization'),
          },
        }
      );
      console.log(response.data);
    } catch (error) {
      console.log(error);
      alert('태그 등록 실패');
    }
  };

  // 등록 버튼 핸들러
  const submitQuestionForm = (e) => {
    e.preventDefault();

    const sendPatch = async () => {
      try {
        const response = await axios.patch(
          process.env.REACT_APP_DB_HOST + `/api/questions/${questionId}`,
          {
            questionId: questionId,
            title: patchTitle,
            text: editorRef.current?.getInstance().getHTML(),
          },
          {
            headers: {
              Authorization: cookies.get('Authorization'),
            },
          }
        );
        postTags();
        setTimeout(() => {
          navigate(`/questions/${response.data.questionId}`);
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
    <BigContainer>
      <form onSubmit={submitQuestionForm} name="asksubmit">
        <div className="content">
          <div className="content__container">
            <h3 className="content__title">Title</h3>
            <span className="content__title__description">
              Be specific and imagine you’re asking a question to another
              person.
            </span>
            <input
              type="text"
              placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
              maxLength="300"
              {...bindPatchTitle}
            ></input>
          </div>
        </div>
        <div className="content">
          <div className="content__container">
            <h3 className="content__title">Tags</h3>
            <span className="content__title__description">
              Please enter separated by &#39;,&#39; without spaces.
            </span>
            <input
              type="text"
              placeholder="e.g. java,oracle,spring,react,vscode,javascript,redux,..."
              value={inputTags}
              onChange={tagsChangeHandler}
            ></input>
          </div>
        </div>
        <Container>
          <div className="ask__body">
            <h3>What are the details of your problem?</h3>
            <span className="ask__body--detail">
              Introduce the problem and expand on what you put in the title.
              Minimum 20 characters.
            </span>
          </div>
          <Viewer />
          <Editor
            ref={editorRef} // DOM 선택용 useRef
            placeholder="내용을 입력해주세요."
            previewStyle="tab" // 미리보기 스타일 지정
            height="300px" // 에디터 창 높이
            initialEditType="markdown" //
            toolbarItems={[
              // 툴바 옵션 설정
              ['heading', 'bold', 'italic', 'strike'],
              ['hr', 'quote'],
              ['ul', 'ol', 'task'],
              ['code', 'codeblock'],
            ]}
            useCommandShortcut={false} // 키보드 입력 컨트롤 방지
            language="ko-KR"
            initialValue=""
          ></Editor>
        </Container>
        <Button>
          <button className="btn-style1">Review your question</button>
        </Button>
      </form>
    </BigContainer>
  );
};

export default ToastPatch;
