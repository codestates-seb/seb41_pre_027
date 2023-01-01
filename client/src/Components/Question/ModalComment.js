import { useState } from 'react';
import styled from 'styled-components';
import { fetchPatchComment } from '../../utils/api';
import useInput from '../../utils/useInput';
export const ModalBackdrop = styled.div`
  position: fixed;
  z-index: 999;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.4);
  display: grid;
  place-items: center;
`;

export const ModalContainer = styled.div`
  text-align: center;
`;

export const ModalBtn = styled.button`
  height: fit-content;
  border-radius: 3px;
`;

export const ModalView = styled.div.attrs((props) => ({
  // attrs 메소드를 이용해서 아래와 같이 div 엘리먼트에 속성을 추가할 수 있습니다.
  role: 'dialog',
}))`
  border-radius: 5px;
  background-color: #ffffff;
  width: 340px;
  padding: 16px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;

  > .close-btn {
    position: absolute;
    top: 4px;
    right: 4px;

    width: 24px;
    height: 24px;
    text-align: center;
    line-height: 20px;
    padding: 0;

    background-color: transparent;
    border: 0;
    cursor: pointer;

    :hover {
      color: #0074cc;
    }
  }

  > div.desc {
    width: 100%;
    padding: 24px 0 8px;
    color: #4000c7;
    > form {
      display: flex;
      gap: 16px;
      flex-direction: column;
      input {
        padding: 0.6em 0.8em;
        border: 1px solid #e6e9ea;
        background-color: #fff;
        color: #3b4045;
        ::placeholder {
          color: #838c95;
        }
      }
      input:focus {
        border-color: #6bbbf7;
        outline: none;
      }
      button {
        height: fit-content;
        padding: 4px 0;
        border-radius: 3px;
      }
    }
  }
`;

export const ModalComment = ({
  commentId,
  id,
  commentContent,
  bindCommentContent,
  resetCommentContent,
}) => {
  const [commentPatch, bindCommentPatch, resetCommentPatch] = useInput('');
  const [isOpen, setIsOpen] = useState(false);
  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };
  const patchCommentForm = () => {
    alert(`${commentId}${commentPatch}`);
    fetchPatchComment(
      process.env.REACT_APP_DB_HOST + `/api/comments/${commentId}`,
      id,
      {
        commentId: commentId,
        text: commentPatch,
      }
    );
    resetCommentPatch();
  };
  return (
    <>
      <ModalContainer isOpen={isOpen}>
        <ModalBtn className="btn-style2" onClick={openModalHandler}>
          {isOpen === false ? '수정' : '수정 중'}
        </ModalBtn>
        {isOpen === true ? (
          <ModalBackdrop onClick={openModalHandler}>
            <ModalView onClick={(e) => e.stopPropagation()}>
              <button onClick={openModalHandler} className="close-btn">
                &times;
              </button>
              <div className="desc">
                <form onSubmit={(e) => patchCommentForm(e)}>
                  <input
                    type="text"
                    placeholder="Modified a comment "
                    {...bindCommentPatch}
                  ></input>
                  <button className="btn-style1" type="submit">
                    수정하기
                  </button>
                </form>
              </div>
            </ModalView>
          </ModalBackdrop>
        ) : null}
      </ModalContainer>
    </>
  );
};
