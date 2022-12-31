import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { avatars } from '../../utils/avatarImage';

const StyledQuestionsList = styled.div`
  width: 100%;
  margin: 20px auto 40px;
  border-bottom: 1px solid #e3e3e3;
  > li {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    border-top: 1px solid #e3e6e8;
    padding: 16px;
    gap: 16px;

    .question__response {
      width: 15%;
      color: #6a737c;
      text-align: right;
      display: flex;
      flex-direction: column;
      gap: 12px;
    }
    .question__preview {
      width: calc(100% - 15% - 16px);
    }
    a {
      display: block;
      color: #0074cc;
      :hover {
        color: #0a95ff;
      }
    }
    .question__title {
      font-size: 1.3rem;
      text-overflow: ellipsis;
      overflow: hidden;
      display: -webkit-box;
      -webkit-line-clamp: 1; /* 몇 줄까지 보여줄 지 */
      -webkit-box-orient: vertical;
      height: 1.3rem;
      line-height: 1.3rem;
    }
    .question__content {
      width: 100%;
      margin: 12px 0;
      text-overflow: ellipsis;
      overflow: hidden;
      word-break: break-word;
      line-height: 1.5;
      color: #3b4045;

      display: -webkit-box;
      -webkit-line-clamp: 2; /* 몇 줄까지 보여줄 지 */
      -webkit-box-orient: vertical;
    }
    .question__author {
      width: calc(100% - 8px);
      flex-direction: row;
      justify-content: flex-end;
      gap: 4px;
      img {
        width: 28px;
        height: 28px;
        margin-top: 2px;
      }
    }
  }

  @media screen and (max-width: 1200px) {
    > li {
      flex-direction: column;
      gap: 12px;
      .question__response {
        width: 100%;
        text-align: left;
        flex-direction: row;
      }
      .question__preview {
        width: 100%;
      }
    }
  }

  @media screen and (max-width: 640px) {
    margin: 16px auto 24px;
    > li {
      padding: 16px;
      .question__content {
        margin: 8px 0;
      }
    }
  }
`;

const QuestionsList = ({ questionList }) => {
  return (
    <StyledQuestionsList>
      {questionList.length ? (
        questionList.map((el) => {
          return (
            <li key={el.questionId}>
              <ul className="question__response">
                <li>{el.ratingScore} votes</li>
                <li>{el.answerCount} answers</li>
                <li>{el.views} views</li>
              </ul>
              <div className="question__preview">
                <Link
                  to={'/questions/' + el.questionId}
                  className="question__title"
                >
                  {el.title}
                </Link>
                <p className="question__content">{el.text}</p>
                <div className="question__author flex-vertical-center">
                  <img src={avatars[el.memberImage - 1]} alt="유저아바타" />
                  <Link to={'/users/' + el.memberId}>{el.name}</Link>
                </div>
              </div>
            </li>
          );
        })
      ) : (
        <li>작성된 게시글이 없습니다.</li>
      )}
    </StyledQuestionsList>
  );
};

export default QuestionsList;
