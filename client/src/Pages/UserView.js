import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import NewestPosts from '../Components/Users/NewestPosts';
import styled from 'styled-components';
import { avatars, avatarsM } from '../utils/avatarImage';

const StyledUserView = styled.div`
  width: 100%;
  padding: 24px;
  border-left: 1px solid #d6d9dc;

  .user__profile {
    display: flex;
    align-items: center;
    gap: 12px;
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #d6d9dc;

    img {
      width: 128px;
    }

    .user__profile--info {
      display: flex;
      flex-direction: column;
    }

    h3 {
      font-weight: 500;
      font-size: 2.2rem;
      margin-bottom: 12px;
      color: #232629;
    }
    a {
      font-size: 1.15rem;
      color: #6a737c;
      :hover {
        color: #232629;
      }
      span {
        font-size: 1.5rem;
        display: inline-block;
        vertical-align: middle;
        margin-right: 4px;
      }
    }
  }

  @media screen and (max-width: 1200px) {
    .user__profile {
      img {
        width: 96px;
      }
    }
    h3 {
      font-size: 2rem;
    }
    a {
      font-size: 1rem;
      span {
        font-size: 1.3rem;
        margin-bottom: 4px;
      }
    }
  }
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
      {Object.keys(userData).length ? (
        <div className="user__profile">
          <img
            src={avatarsM[userData.memberImage - 1]}
            alt={`${userData.name}아바타이미지`}
          />
          <div className="user__profile--info">
            <h3>{userData.name}</h3>
            <a href={`mailto:${userData.email}`}>
              <span>📧</span>
              {userData.email}
            </a>
          </div>
        </div>
      ) : (
        <p>데이터없음</p>
      )}

      <NewestPosts />
    </StyledUserView>
  );
};

export default userView;
