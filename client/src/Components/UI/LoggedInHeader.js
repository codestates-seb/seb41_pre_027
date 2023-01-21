import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import axios from 'axios';
import { authActions } from '../../Redux/auth';
import { avatars } from '../../utils/avatarImage';
import { Cookies } from 'react-cookie';

const StyledLoggedInHeader = styled.ul`
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 50px;

  li {
    height: 100%;
    display: flex;
    align-items: center;
    padding: 0 8px;
    overflow-y: hidden;
    :hover {
      background-color: #e3e6e8;
    }

    button {
      background: none;
      -webkit-appearance: none;
      -moz-appearance: none;
      appearance: none;
      border: none;
      display: inherit;
      padding: 0;
    }

    svg {
      color: #525960;
      cursor: pointer;
    }

    img {
      width: 38px;
      height: 38px;
      display: inline-block;
      margin-top: 4px;
    }
  }
`;

const LoggedInHeader = () => {
  const cookies = new Cookies();
  // 로그인 된 헤더. 마지막 아이콘 버튼을 눌러 로그아웃 할 수 있다.
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const postLogout = async () => {
    try {
      const response = await axios.post(
        process.env.REACT_APP_DB_HOST + '/api/member/logout',
        {
          payload: {},
        },
        {
          headers: {
            Authorization: cookies.get('Authorization'),
          },
        }
      );
      console.log(response);
      navigate('/');
      window.location.reload();
    } catch (error) {
      console.log(error);
    }
  };

  const logoutHandler = () => {
    postLogout();
    dispatch(authActions.logout());
    cookies.remove('Authorization');
    cookies.remove('memberId');
    cookies.remove('Refresh');
    // navigate('/');
    // window.location.reload();
  };

  const [userInfo, setUserInfo] = useState({});
  const memberId = cookies.get('memberId');

  const getUserInfo = async () => {
    try {
      const response = await axios.get(
        process.env.REACT_APP_DB_HOST + `/api/member/${memberId}`
      );
      setUserInfo(response.data);
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
    getUserInfo();
  }, []);

  return (
    <StyledLoggedInHeader>
      <li title={userInfo.name}>
        <Link to="/mypage">
          <img src={avatars[userInfo.memberImage - 1]} alt="아바타이미지" />
        </Link>
      </li>
      <li>
        <svg className="svg-icon" width="20" height="18" viewBox="0 0 20 18">
          <path d="M4.63 1h10.56a2 2 0 0 1 1.94 1.35L20 10.79V15a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-4.21l2.78-8.44c.25-.8 1-1.36 1.85-1.35Zm8.28 12 2-2h2.95l-2.44-7.32a1 1 0 0 0-.95-.68H5.35a1 1 0 0 0-.95.68L1.96 11h2.95l2 2h6Z"></path>
        </svg>
      </li>
      <li>
        <svg className="svg-icon" width="18" height="18" viewBox="0 0 18 18">
          <path d="M15 2V1H3v1H0v4c0 1.6 1.4 3 3 3v1c.4 1.5 3 2.6 5 3v2H5s-1 1.5-1 2h10c0-.4-1-2-1-2h-3v-2c2-.4 4.6-1.5 5-3V9c1.6-.2 3-1.4 3-3V2h-3ZM3 7c-.5 0-1-.5-1-1V4h1v3Zm8.4 2.5L9 8 6.6 9.4l1-2.7L5 5h3l1-2.7L10 5h2.8l-2.3 1.8 1 2.7h-.1ZM16 6c0 .5-.5 1-1 1V4h1v2Z"></path>
        </svg>
      </li>
      <li>
        <svg className="svg-icon" width="18" height="18" viewBox="0 0 18 18">
          <path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8Zm.81 12.13c-.02.71-.55 1.15-1.24 1.13-.66-.02-1.17-.49-1.15-1.2.02-.72.56-1.18 1.22-1.16.7.03 1.2.51 1.17 1.23ZM11.77 8c-.59.66-1.78 1.09-2.05 1.97a4 4 0 0 0-.09.75c0 .05-.03.16-.18.16H7.88c-.16 0-.18-.1-.18-.15.06-1.35.66-2.2 1.83-2.88.39-.29.7-.75.7-1.24.01-1.24-1.64-1.82-2.35-.72-.21.33-.18.73-.18 1.1H5.75c0-1.97 1.03-3.26 3.03-3.26 1.75 0 3.47.87 3.47 2.83 0 .57-.2 1.05-.48 1.44Z"></path>
        </svg>
      </li>
      <li>
        <svg className="svg-icon" width="18" height="18" viewBox="0 0 18 18">
          <path d="m8.9844-0.013672a1 1 0 0 0 -0.98438 1.0137v0.38281l-0.55273-0.27734a1 1 0 0 0 -0.48242 -0.11133 1 1 0 0 0 -0.41211 1.9004l1.4473 0.72266v3.6523l-3.1621-1.8262 0.097656-1.6152a1 1 0 0 0 -0.95117 -1.0742 1 1 0 0 0 -1.0449 0.95508l-0.037109 0.61719-0.33008-0.19141a1 1 0 0 0 -0.57422 -0.14062 1 1 0 0 0 -0.42578 1.8711l0.33203 0.19141-0.51758 0.3418a1 1 0 1 0 1.1016 1.668l1.3516-0.89258 3.1621 1.8262-3.1621 1.8262-1.3516-0.89258a1 1 0 0 0 -0.56445 -0.17383 1 1 0 0 0 -0.53711 1.8418l0.51758 0.3418-0.33203 0.19141a1 1 0 1 0 1 1.7305l0.33008-0.19141 0.037109 0.61719a1 1 0 1 0 1.9961 -0.11914l-0.097656-1.6152 3.1621-1.8262v3.6523l-1.4473 0.72266a1 1 0 0 0 0.89453 1.7891l0.55273-0.27734v0.38281a1 1 0 1 0 2 0v-0.38281l0.55273 0.27734a1 1 0 1 0 0.89453 -1.7891l-1.4473-0.72266v-3.6523l3.1621 1.8262-0.097656 1.6152a1 1 0 1 0 1.9961 0.11914l0.037109-0.61719 0.33008 0.19141a1 1 0 1 0 1 -1.7305l-0.33203-0.19141 0.51758-0.3418a1 1 0 0 0 -0.56641 -1.8418 1 1 0 0 0 -0.53516 0.17383l-1.3516 0.89258-3.1621-1.8262 3.1621-1.8262 1.3516 0.89258a1 1 0 1 0 1.1016 -1.668l-0.51758-0.3418 0.33203-0.19141a1 1 0 0 0 -0.45508 -1.8711 1 1 0 0 0 -0.54492 0.14062l-0.33008 0.19141-0.037109-0.61719a1 1 0 0 0 -0.97461 -0.95508 1 1 0 0 0 -1.0215 1.0742l0.097656 1.6152-3.1621 1.8262v-3.6523l1.4473-0.72266a1 1 0 1 0 -0.89453 -1.7891l-0.55273 0.27734v-0.38281a1 1 0 0 0 -1.0156 -1.0137z"></path>
        </svg>
      </li>
      <li title="Log Out">
        <button onClick={logoutHandler}>
          <svg className="svg-icon" width="18" height="18" viewBox="0 0 18 18">
            <path d="M15 1H3a2 2 0 0 0-2 2v2h16V3a2 2 0 0 0-2-2ZM1 13c0 1.1.9 2 2 2h8v3l3-3h1a2 2 0 0 0 2-2v-2H1v2Zm16-7H1v4h16V6Z"></path>
          </svg>
        </button>
      </li>
    </StyledLoggedInHeader>
  );
};

export default LoggedInHeader;
