import React, { useEffect, useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';

const userView = () => {
  const location = useLocation().pathname;
  const memberId = location.slice(7);

  const [userData, setUserData] = useState({});

  const getUser = async () => {
    try {
      const response = await axios.get(`/api/member/${memberId}`);
      setUserData(response.data);
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
    getUser();
    getUserQuestion();
  }, []);

  return (
    <div>
      <h2>유저정보</h2>
      {Object.keys(userData).length ? (
        <div>
          <h3>{userData.name}</h3>
          <p>{userData.memberId}</p>
          <p>{userData.email}</p>
          <p>{userData.memberImage}</p>
        </div>
      ) : (
        <p>데이터없음</p>
      )}
      <hr />
      <h2>유저작성글</h2>
      {countUserQuestions ? (
        <ul className="questions__list">
          {userQuestions.map((el) => {
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
                </div>
              </li>
            );
          })}
        </ul>
      ) : (
        <p>작성한 게시글 없음</p>
      )}
    </div>
  );
};

export default userView;
