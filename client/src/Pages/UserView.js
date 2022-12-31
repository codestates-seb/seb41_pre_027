import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import NewestPosts from '../Components/Users/NewestPosts';
import styled from 'styled-components';
import avatars from '../utils/avatarImage';

const StyledUserView = styled.div`
  width: 100%;
  padding: 24px;
  border-left: 1px solid #d6d9dc;
`;

const userView = () => {
  const location = useLocation().pathname;
  const memberId = location.slice(7);

  const [userData, setUserData] = useState({});

  const getUser = async () => {
    try {
      const response = await axios.get(
        process.env.REACT_APP_DB_HOST + `/api/member/${memberId}`
      );
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

  useEffect(() => {
    getUser();
  }, []);

  return (
    <StyledUserView>
      <h2>유저정보</h2>
      {Object.keys(userData).length ? (
        <div>
          <img
            src={avatars[userData.memberImage - 1]}
            alt={`${userData.name}아바타이미지`}
          />
          <h3>{userData.name}</h3>
          <a href={`mailto:${userData.email}`}>{userData.email}</a>
        </div>
      ) : (
        <p>데이터없음</p>
      )}
      <hr />
      <NewestPosts />
    </StyledUserView>
  );
};

export default userView;
