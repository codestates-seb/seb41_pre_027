import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import axios from 'axios';

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

const SignUp = () => {
  const navigate = useNavigate();

  const [inputEmail, setInputEmail] = useState('');
  const [inputPassword, setInputPassword] = useState('');
  const [inputName, setInputName] = useState('');
  const [selectedAvatar, setSelectedAvatar] = useState('1');

  const changeEmailHandler = (event) => {
    setInputEmail(event.target.value);
  };
  const changePasswordHandler = (event) => {
    setInputPassword(event.target.value);
  };
  const changeNameHandler = (event) => {
    setInputName(event.target.value);
  };
  const changeAvatarHandler = (event) => {
    setSelectedAvatar(event.target.value);
  };

  const signupHandler = (event) => {
    event.preventDefault();

    //post 요청 코드
    const reqBody = {
      email: inputEmail,
      password: inputPassword,
      name: inputName,
      memberImage: Number(selectedAvatar),
    };
    const sendData = async () => {
      try {
        const response = await axios.post('/api/member', reqBody);
        if (response.status === 201) {
          alert('환영합니다.');
          navigate('/login');
          window.location.reload();
        }
      } catch (error) {
        console.log(error);
        if (error.response.status === 400) {
          alert('잘못된 요청입니다.');
        }
      }
    };
    sendData();

    // 인풋 초기화
    setInputEmail('');
    setInputPassword('');
    setInputName('');
    setSelectedAvatar('1');
  };

  return (
    <StyledSignUp>
      <h2>회원가입페이지</h2>
      <form onSubmit={signupHandler}>
        <div>
          <label htmlFor="email">이메일</label>
          <input
            id="email"
            type="text"
            placeholder="email"
            value={inputEmail}
            onChange={changeEmailHandler}
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
        <div>
          <label htmlFor="name">닉네임</label>
          <input
            id="name"
            type="text"
            placeholder="name"
            value={inputName}
            onChange={changeNameHandler}
          />
        </div>
        <div>
          <input
            id="avt1"
            type="radio"
            name="userAvatar"
            value="1"
            checked={selectedAvatar === '1'}
            onChange={changeAvatarHandler}
          />
          <label htmlFor="avt1">아바타1</label>
          <input
            id="avt2"
            type="radio"
            name="userAvatar"
            value="2"
            checked={selectedAvatar === '2'}
            onChange={changeAvatarHandler}
          />
          <label htmlFor="avt2">아바타2</label>
          <input
            id="avt3"
            type="radio"
            name="userAvatar"
            value="3"
            checked={selectedAvatar === '3'}
            onChange={changeAvatarHandler}
          />
          <label htmlFor="avt3">아바타3</label>
          <input
            id="avt4"
            type="radio"
            name="userAvatar"
            value="4"
            checked={selectedAvatar === '4'}
            onChange={changeAvatarHandler}
          />
          <label htmlFor="avt4">아바타4</label>
          <input
            id="avt5"
            type="radio"
            name="userAvatar"
            value="5"
            checked={selectedAvatar === '5'}
            onChange={changeAvatarHandler}
          />
          <label htmlFor="avt5">아바타5</label>
          <input
            id="avt6"
            type="radio"
            name="userAvatar"
            value="6"
            checked={selectedAvatar === '6'}
            onChange={changeAvatarHandler}
          />
          <label htmlFor="avt6">아바타6</label>
        </div>
        <button type="submit">회원가입</button>
      </form>
    </StyledSignUp>
  );
};

export default SignUp;
