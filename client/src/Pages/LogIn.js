import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { authActions } from '../Redux/auth';

const StyledSignUp = styled.div`
  padding: 40px 0;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;

  div {
    margin: 10px 0;
  }
  label {
    margin-right: 10px;
  }
`;

const LogIn = () => {
  // 로그인 버튼을 눌렀을 때, 로그인 상태로 변경하는 dispatch이다
  const dispatch = useDispatch();

  const navigate = useNavigate();

  const [inputEmail, setInputEmail] = useState('');
  const [inputPassword, setInputPassword] = useState('');

  const changeEmailHandler = (event) => {
    setInputEmail(event.target.value);
  };
  const changePasswordHandler = (event) => {
    setInputPassword(event.target.value);
  };

  const loginHandler = (event) => {
    event.preventDefault();

    //post 요청 코드
    const reqBody = {
      username: inputEmail,
      password: inputPassword,
    };
    const sendData = async () => {
      try {
        const response = await axios.post('/api/login', reqBody);
        let jwtToken = response.headers.get('Authorization');
        let memberId = response.data.memberId;
        localStorage.setItem('Authorization', jwtToken);
        localStorage.setItem('memberId', memberId);
        dispatch(authActions.login());
        setTimeout(() => {
          navigate('/');
          window.location.reload();
        }, 250);
      } catch (error) {
        console.log(error);
        alert('인증에 실패했습니다.');
      }
    };
    sendData();

    // 인풋 초기화
    setInputEmail('');
    setInputPassword('');
  };

  return (
    <StyledSignUp>
      <h2>로그인 페이지</h2>
      <form onSubmit={loginHandler}>
        <div>
          <label htmlFor="email">이메일</label>
          <input
            id="email"
            type="text"
            placeholder="email"
            value={inputEmail}
            onChange={changeEmailHandler}
            autoComplete="off"
          />
        </div>
        <div>
          <label htmlFor="pw">비밀번호</label>
          <input
            id="pw"
            type="password"
            placeholder="password"
            value={inputPassword}
            onChange={changePasswordHandler}
          />
        </div>
        <button type="submit">로그인</button>
      </form>
    </StyledSignUp>
  );
};

export default LogIn;
