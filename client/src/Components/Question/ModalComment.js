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
  height: 15rem;
  text-align: center;
  margin: 120px auto;
`;

export const ModalBtn = styled.button`
  text-decoration: none;
  border: none;
  color: white;
  border-radius: 30px;
  cursor: grab;
`;

export const ModalView = styled.div.attrs((props) => ({
  // attrs 메소드를 이용해서 아래와 같이 div 엘리먼트에 속성을 추가할 수 있습니다.
  role: 'dialog',
}))`
  border-radius: 10px;
  background-color: #ffffff;
  width: 300px;
  height: 100px;

  > span.close-btn {
    margin-top: 5px;
    cursor: pointer;
  }

  > div.desc {
    margin-top: 25px;
    color: #4000c7;
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
      <ModalContainer>
        <ModalBtn onClick={openModalHandler}>
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
                    placeholder="입력하시오"
                    {...bindCommentPatch}
                  ></input>
                  <button type="submit">수정하기</button>
                </form>
              </div>
            </ModalView>
          </ModalBackdrop>
        ) : null}
      </ModalContainer>
    </>
  );
};
