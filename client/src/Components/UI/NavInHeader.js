import React, { useState } from 'react';
import GNB from './GNB';
import styled from 'styled-components';

const StyledNavInHeader = styled.div`
  display: flex;
  align-items: center;
  position: relative;
  height: 100%;
  :hover {
    background-color: #e3e6e8;
  }

  .header__menu--button {
    padding: 0 16px;
    flex-shrink: 0;
    justify-content: center;
    flex-direction: column;
    background: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border: none;
    height: 100%;
    cursor: pointer;

    span {
      display: block;
      width: 16px;
      height: 2px;
      background-color: #525960;
      margin: 1.5px 0;
      -webkit-transition: all 0.25s ease-in-out;
      -moz-transition: all 0.25s ease-in-out;
      -o-transition: all 0.25s ease-in-out;
      transition: all 0.25s ease-in-out;
      position: relative;

      ::before {
        position: absolute;
        content: '';
        top: -5px;
        left: 0;
        width: 100%;
        height: 2px;
        background-color: #525960;
      }
      ::after {
        position: absolute;
        content: '';
        top: 5px;
        left: 0;
        width: 100%;
        height: 2px;
        background-color: #525960;
      }
    }
  }
  .open {
    span {
      background-color: transparent;
      ::before {
        top: 0;
        transform: rotate(-45deg);
      }
      ::after {
        top: 0;
        transform: rotate(45deg);
      }
    }
  }

  .header__floatmenu {
    position: absolute;
    background-color: #fff;
    top: 50px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.05),
      0 2px 8px rgba(0, 0, 0, 0.05);
  }
`;

const NavInHeader = () => {
  // 메뉴 아이콘을 누르면 GNB 컴포넌트를 보여주고, 아이콘을 변경한다.
  const [isOpened, setIsOpened] = useState(false);
  const floatMenuHandler = () => {
    setIsOpened(!isOpened);
  };
  return (
    <StyledNavInHeader>
      <button
        className={
          isOpened
            ? 'header__menu--button flex-vertical-center open'
            : 'header__menu--button flex-vertical-center'
        }
        onClick={floatMenuHandler}
      >
        <span />
      </button>
      {/* 버튼 눌러서 열리고 닫히는 GNB UI */}
      {isOpened && (
        <div className="header__floatmenu">
          <GNB />
        </div>
      )}
    </StyledNavInHeader>
  );
};

export default NavInHeader;
