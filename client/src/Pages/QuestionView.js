import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const QuestionView = () => {
  const location = useLocation().pathname;
  const questionId = location.slice(11);

  const [data, setData] = useState({});

  const getQuestion = async () => {
    try {
      const response = await axios.get(`/api/questions/${questionId}`);
      setData(response.data);
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
    getQuestion();
  }, []);

  return (
    <div>
      {Object.keys(data).length ? (
        <div>
          <h3>{data.title}</h3>
          <p>{data.text}</p>
          <p>{data.memberImage}</p>
          <p>{data.name}</p>
        </div>
      ) : (
        <p>데이터없음</p>
      )}
    </div>
  );
};

export default QuestionView;
