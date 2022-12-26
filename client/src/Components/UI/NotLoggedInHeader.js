import React from 'react';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import { authActions } from '../../Redux/auth';

const StyledNotLoggedInHeader = styled.ul`
  display: flex;
  flex-direction: row;
  align-items: center;

  li {
    display: inline-flex;
    padding: 8px 10px;
    border-radius: 3px;
    margin: 0 2px;
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
  .btn-style2 {
    color: #39739d;
    background-color: #e1ecf4;
    border: 1px solid #7aa7c7;
    box-shadow: inset 0 1px 0 0 rgba(255, 255, 255, 0.7);
    :hover {
      background-color: #b3d3ea;
      color: #2c5877;
    }
  }
`;

const NotLoggedInHeader = () => {
  // 로그인 버튼을 눌렀을 때, 로그인 상태로 변경하는 dispatch이다
  // 나중에 로그인 페이지로 라우팅해서 정상적인 로직을 통해 로그인이 작동하도록 수정해야 하는 부분
  const dispatch = useDispatch();

  const loginHandler = (event) => {
    event.preventDefault();

    dispatch(authActions.login());
  };
  return (
    <StyledNotLoggedInHeader>
      <li className="btn-style2">
        <Link to="/login" onClick={loginHandler}>
          Log in
        </Link>
      </li>
      <li className="btn-style1">
        <Link to="/signup">Sign up</Link>
      </li>
    </StyledNotLoggedInHeader>
  );
};

export default NotLoggedInHeader;
