import React, { useState } from 'react';
import TopbarSearch from './TopbarSearch';
import styled from 'styled-components';

const StyledHeaderSearch = styled.div`
  display: flex;
  align-items: center;
  flex-grow: 10000;
  height: 100%;

  .header__search {
    flex: 1 10000 0;
  }
  .heder__search__button {
    display: none;
  }
  .header__search--mobile {
    display: none;
  }
  @media screen and (max-width: 1200px) {
    .pconly {
      display: none;
    }
  }
  @media screen and (max-width: 640px) {
    .header__search {
      display: none;
    }
    .heder__search__button {
      display: flex;
      align-items: center;
      flex-direction: row-reverse;
      cursor: pointer;
      margin-left: auto;
      height: 100%;
      position: relative;

      > svg {
        padding: 0 10px;
      }
    }
    .open {
      background: #e3e6e8;
    }
    .header__search--mobile {
      display: block;
      box-sizing: border-box;
      width: 100%;
      position: absolute;
      top: 50px;
      left: 0;
      box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.05),
        0 2px 8px rgba(0, 0, 0, 0.05);
      background-color: #e3e6e8;
      padding: 8px 12px;
    }
  }
`;

const HeaderSearch = () => {
  // 모바일 검색 UI를 보여주거나 닫는 버튼
  const [isOpened, setIsOpened] = useState(false);
  const showSearchUIHandler = () => {
    setIsOpened(!isOpened);
  };

  return (
    <StyledHeaderSearch>
      {/* pc, 태블릿 검색 ui */}
      <div className="header__search">
        <TopbarSearch />
      </div>

      {/* 모바일 검색 아이콘 */}
      <div
        className={
          isOpened ? 'heder__search__button open' : 'heder__search__button'
        }
        onClick={showSearchUIHandler}
        aria-hidden="true"
      >
        <svg
          className="svg-icon iconSearch"
          width="18"
          height="18"
          viewBox="0 0 18 18"
        >
          <path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path>
        </svg>
      </div>
      {/* 모바일 검색 아이콘 클릭 시 열리는 검색 ui */}
      {isOpened && (
        <div className="header__search--mobile">
          <TopbarSearch />
        </div>
      )}
    </StyledHeaderSearch>
  );
};

export default HeaderSearch;
