import React, { useEffect, useState } from 'react';
import axios from 'axios';
import NewestPosts from '../Components/Users/NewestPosts';
import styled from 'styled-components';
import { avatarsM } from '../utils/avatarImage';
import { Cookies } from 'react-cookie';
import pencil from '../Img/pencil.png';
import { Link } from 'react-router-dom';

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
      flex-basis: 80%;
    }

    h3 {
      font-weight: 500;
      font-size: 2.2rem;
      margin-bottom: 12px;
      color: #232629;
    }
    > a {
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
    > a {
      font-size: 1rem;
      span {
        font-size: 1.3rem;
        margin-bottom: 4px;
      }
    }
  }

  @media screen and (max-width: 640px) {
    .user__profile {
      flex-direction: column;
    }
  }
`;
const Buttons = styled.div`
  display: flex;
  a {
    font-size: 1rem;
    border-radius: 3px;
    display: flex;
    justify-content: center;
    align-items: center;
    white-space: nowrap;
    padding: 0.6rem 0.7em;
    color: #9ba2a9;
    background-color: #fff;
    border: 1px solid #9fa6ad;
    cursor: pointer;
    &:hover {
      background-color: #f8f9f9;
      border-color: #838c95;
      color: #3b4045;
    }
    .pencil {
      margin-right: 6px;
    }
  }
`;

const MyPageView = () => {
  const cookies = new Cookies();
  const memberId = cookies.get('memberId');

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
          <Buttons>
            <Link to="/mypage/editprofile">
              <svg
                className="svg-icon pencil"
                width="18"
                height="18"
                viewBox="0 0 18 18"
              >
                <path d="m13.68 2.15 2.17 2.17c.2.2.2.51 0 .71L14.5 6.39l-2.88-2.88 1.35-1.36c.2-.2.51-.2.71 0ZM2 13.13l8.5-8.5 2.88 2.88-8.5 8.5H2v-2.88Z"></path>
              </svg>
              Edit profile
            </Link>
          </Buttons>
        </div>
      ) : (
        <p>데이터없음</p>
      )}

      <NewestPosts memberId={memberId} />
    </StyledUserView>
  );
};

export default MyPageView;
