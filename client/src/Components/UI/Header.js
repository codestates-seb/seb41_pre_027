import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useLocation } from 'react-router-dom';
import styled from 'styled-components';
import NavInHeader from './NavInHeader';
import HeaderSearch from './HeaderSearch';
import NotLoggedInHeader from './NotLoggedInHeader';
import LoggedInHeader from './LoggedInHeader';

import sprites from '../../assets/images/sprites.svg';

const StyledHeader = styled.header`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: #f8f9f9;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top: 3px solid #f48225;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.05),
    0 2px 8px rgba(0, 0, 0, 0.05);
  z-index: 999;

  .contents {
    width: 97.2307692rem;
    height: 100%;
  }

  .flex-vertical-center {
    display: flex;
    align-items: center;
  }

  .topbar {
    flex-direction: row;
  }

  .header__logo {
    height: 100%;
    padding: 0 8px;
    :hover {
      background-color: #e3e6e8;
    }
    > span {
      background-image: url(${sprites});
      background-position: 0 -500px;
      width: 150px;
      height: 30px;
      margin-top: -4px;
      display: inline-block;
      text-indent: -9999em;
    }
  }

  .header__nav {
    flex-direction: row;
    li {
      padding: 6px 12px;
      color: #525960;
      cursor: pointer;
    }
    li:hover {
      background-color: #e3e6e8;
      border-radius: 100px;
      padding: 6px 12px;
      color: #0c0d0e;
    }
  }

  .header__member {
    padding-right: 12px;
  }

  @media screen and (max-width: 1200px) {
    .contents {
      min-width: auto;
    }
    .pconly {
      display: none;
    }
  }
  @media screen and (max-width: 640px) {
    .header__logo {
      > span {
        background-image: url(${sprites});
        background-position: 0 -500px;
        width: 25px;
        margin-top: 0;
        display: inline-block;
        text-indent: -9999em;
      }
    }

    .header__member {
      max-width: 150px;
      overflow-x: auto;
      -ms-overflow-style: none; /* IE and Edge */
      scrollbar-width: none; /* Firefox */
      ::-webkit-scrollbar {
        /* Chrome, Safari, Opera */
        height: 0;
        background: transparent;
      }
    }
  }
`;
const Header = () => {
  // windowWidth 상태를 store에서 받아온다.
  const windowWidth = useSelector((state) => state.windowWidth.width);

  // 로그인 인증 상태를 확인한다.
  const isAuth = useSelector((state) => state.auth.isAuthenticated);

  // location의 path가 /login, /signup, /questions/ask이 아닐 경우에만 GNB를 화면 좌측에 보여주기 위한 상태 변경 함수
  // windowWidth가 모바일 사이즈일 때도 보여준다.
  const location = useLocation().pathname;
  const [isShow, setIsShow] = useState(false);
  useEffect(() => {
    if (
      location === '/login' ||
      location === '/signup' ||
      location === '/questions/ask' ||
      windowWidth <= 640
    ) {
      setIsShow(true);
    } else {
      setIsShow(false);
    }
  }, [location, windowWidth]);

  return (
    <StyledHeader>
      <div className="contents topbar flex-vertical-center">
        {/* 로그인, 회원가입, 질문작성, 모바일에서 보이는 메뉴 아이콘 */}
        {isShow && <NavInHeader />}

        {/* 스택오버플로우 로고, 메인페이지 바로가기 버튼 */}
        <a href="/" className="header__logo flex-vertical-center">
          <span>Stack Overflow</span>
        </a>

        {/* 상단 바로가기 링크 - 모양만 나오는 빈 링크이다 */}
        <ul className="header__nav flex-vertical-center">
          <li className="pconly">About</li>
          <li>Products</li>
          <li className="pconly">For Teams</li>
        </ul>

        {/* 검색 UI */}
        <HeaderSearch />

        {/* 로그인 상태에 따라 다르게 보이는 회원 관련 버튼 */}
        <div className="header__member flex-vertical-center">
          {/* 로그인 한 상태일 때 <LoggedInHeader /> || 로그인 안한 상태일 때 <NotLoggedInHeader />*/}
          {isAuth ? <LoggedInHeader /> : <NotLoggedInHeader />}
        </div>
      </div>
    </StyledHeader>
  );
};

export default Header;
