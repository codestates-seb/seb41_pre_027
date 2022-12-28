import React, { useState, useEffect } from 'react';
import Pagination from '../Components/UI/Pagination';
import SidebarWidget from '../Components/Questions/SidebarWidget';
import styled from 'styled-components';
import axios from 'axios';
import avatar1 from '../assets/images/avatar/1_@1x.png';
import avatar2 from '../assets/images/avatar/2_@1x.png';
import avatar3 from '../assets/images/avatar/3_@1x.png';
import avatar4 from '../assets/images/avatar/4_@1x.png';
import avatar5 from '../assets/images/avatar/5_@1x.png';
import avatar6 from '../assets/images/avatar/6_@1x.png';
import { useLocation } from 'react-router-dom';

const StyledQuestions = styled.section`
  padding: 24px 24px 24px 0;
  display: flex;
  flex-grow: 10000;
  justify-content: space-between;
  align-items: flex-start;
  gap: 24px;
  border-left: 1px solid #d6d9dc;

  @media screen and (max-width: 640px) {
    padding: 24px 0;
  }
`;
const QuestionsList = styled.section`
  display: flex;
  flex-grow: 10000;
  justify-content: space-between;
  align-items: flex-start;
  flex-direction: column;
  max-width: calc(100% - 300px - 24px);

  .flex-vertical-center {
    display: flex;
    align-items: center;
  }

  .questions__header {
    margin-bottom: 12px;
    justify-content: space-between;
    width: 100%;
    padding-left: 24px;
    box-sizing: border-box;

    h2 {
      font-size: 2.07rem;
      font-family: 'Spoqa Han Sans';
      font-weight: 500;
      margin-right: 12px;
      flex: 1 auto;
    }
    .questions__header--button {
      padding: 0.8em;
      border-radius: 3px;
    }
  }

  .btn-style1 {
    color: #fff;
    background-color: #0a95ff;
    border: 1px solid #0a95ff;
    box-shadow: inset 0 1px 0 0 rgba(255, 255, 255, 0.4);
    :hover {
      background-color: #0074cc;
      border-color: #0074cc;
    }
  }

  .questions__volume {
    padding-left: 24px;
    color: #232629;
    font-size: 1.3rem;
    margin-top: 20px;
  }

  .questions__list {
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
      }
      .question__title {
        font-size: 1.3rem;
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
  }

  @media screen and (max-width: 1200px) {
    max-width: 100%;
    .questions__list {
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
  }
  @media screen and (max-width: 640px) {
    max-width: 100%;
    .questions__header {
      padding: 0 16px;
      h2 {
        font-size: 2rem;
      }
    }
    .questions__volume {
      padding-left: 16px;
    }
    .questions__list {
      margin: 16px auto 24px;
      > li {
        padding: 16px;
        .question__content {
          margin: 8px 0;
        }
      }
    }
  }
`;

const QuestionsTagged = () => {
  const location = useLocation().pathname;
  const tag = location.slice(18);
  const avatars = [avatar1, avatar2, avatar3, avatar4, avatar5, avatar6];
  const [questionList, setQuestionList] = useState([]);
  const [countQuestions, setCountQuestions] = useState(0);
  const [page, setPage] = useState(1);
  const totalPage = Math.ceil(countQuestions / 10) || 1;

  const getQuestions = async () => {
    try {
      const response = await axios.get(`/api/questions?page=${page - 1}`);
      setQuestionList(response.data.data);
      setCountQuestions(response.data.count);
    } catch (error) {
      if (error.response) {
        // 요청이 전송되었고, 서버에서 20x 외의 코드로 응답 됨
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
      } else if (error.request) {
        // 요청이 전송되었지만, 응답이 수신되지 않음
        console.log(error.request);
      } else {
        // 오류가 발생한 요청을 설정하는 데 문제가 생김
        console.log('Error', error.message);
      }
      console.log(error.config);
    }
  };

  useEffect(() => {
    getQuestions();
  }, [page]);

  return (
    <StyledQuestions>
      <QuestionsList>
        <div className="questions__header flex-vertical-center">
          <h2>Questions tagged [{tag}]</h2>
          <div className="questions__header--button btn-style1">
            <a href="/questions/ask">Ask Question</a>
          </div>
        </div>
        <p className="questions__volume">{countQuestions} results</p>
        <ul className="questions__list">
          {questionList.length ? (
            questionList.map((el) => {
              return (
                <li key={el.questionId}>
                  <ul className="question__response">
                    <li>{el.rating} votes</li>
                    <li>10 answers</li>
                    <li>{el.views} views</li>
                  </ul>
                  <div className="question__preview">
                    <a
                      href={'/questions/' + el.questionId}
                      className="question__title"
                    >
                      {el.title}
                    </a>
                    <p className="question__content">{el.text}</p>
                    <div className="question__author flex-vertical-center">
                      <img src={avatars[el.memberImage - 1]} alt="유저아바타" />
                      <a href={'/users/' + el.memberID}>{el.name}</a>
                    </div>
                  </div>
                </li>
              );
            })
          ) : (
            <li>데이터가 없음</li>
          )}
        </ul>
        {/* 페이지네이션 */}
        <Pagination
          totalPage={totalPage}
          limit={5}
          page={page}
          setPage={setPage}
        />
      </QuestionsList>
      <SidebarWidget />
    </StyledQuestions>
  );
};

export default QuestionsTagged;