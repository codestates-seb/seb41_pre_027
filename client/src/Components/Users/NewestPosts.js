import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const StyledNewestPosts = styled.div`
  margin-bottom: 24px;
  h4 {
    font-weight: 400;
    font-size: 1.6rem;
    color: #232629;
  }

  .questions__list {
    border: 1px solid #e3e6e8;
    border-radius: 5px;
    margin: 16px 0;

    li {
      box-sizing: border-box;
      padding: 12px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      color: #6a737c;
      gap: 24px;
      :not(:last-child) {
        border-bottom: 1px solid #e3e6e8;
      }

      .hasAnswers {
        background-color: #9fa6ad;
        color: #fff;
        padding: 4px 12px;
        border-radius: 3px;
      }
      .hasAnswers.true {
        background-color: #5eba7d;
      }

      a {
        flex-grow: 10000;
        color: #0074cc;
        :hover {
          color: #0a95ff;
        }
      }
    }
  }
`;

const NewestPosts = () => {
  const location = useLocation().pathname;
  const memberId = location.slice(7);
  const [userQuestions, setUserQuestions] = useState([]);
  const [countUserQuestions, setCountUserQuestions] = useState(0);

  const getUserQuestion = async () => {
    try {
      const response = await axios.get(`/api/questions/member/${memberId}`);
      setUserQuestions(response.data.data);
      setCountUserQuestions(response.data.count);
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
    getUserQuestion();
  }, []);

  const changeStringToDate = (stringData) => {
    const today = new Date().getTime();
    const writeDate = new Date(stringData).getTime() + 9 * 60 * 60 * 1000;
    const elapsedTime = Math.trunc((today - writeDate) / 1000);

    if (elapsedTime < 60) {
      // 1분
      return '방금 전';
    } else if (elapsedTime < 60 * 60) {
      // 1시간
      return `${Math.trunc(elapsedTime / 60)} 분 전`;
    } else if (elapsedTime < 60 * 60 * 24) {
      // 1일
      return `${Math.trunc(elapsedTime / (60 * 60))} 시간 전`;
    } else if (elapsedTime < 60 * 60 * 24 * 30) {
      // 1달
      return `${Math.trunc(elapsedTime / (60 * 60 * 24))} 일 전`;
    } else {
      return new Date(stringData).toLocaleDateString().slice(0, -1);
    }
  };

  return (
    <StyledNewestPosts>
      <h4>Newest posts</h4>
      <ul className="questions__list">
        {countUserQuestions ? (
          userQuestions.slice(0, 10).map((el) => {
            return (
              <li key={el.questionId}>
                <div
                  className={el.answerCount ? 'hasAnswers true' : 'hasAnswers'}
                >
                  A
                </div>
                <Link
                  to={'/questions/' + el.questionId}
                  className="question__title"
                >
                  {el.title}
                </Link>
                <p className="question__content">
                  {changeStringToDate(el.createdAt)}
                </p>
              </li>
            );
          })
        ) : (
          <li>작성한 게시글 없음</li>
        )}
      </ul>
    </StyledNewestPosts>
  );
};

export default NewestPosts;
