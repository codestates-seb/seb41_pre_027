import React from 'react';
import styled from 'styled-components';

const StyledTopbarSearch = styled.div`
  .topbar__search {
    padding: 0 8px;
    position: relative;
    input {
      border: 1px solid #babfc4;
      background-color: #fff;
      color: #3b4045;
      border-radius: 3px;
      padding: 0.6em 0.7em;
      padding-left: 32px;
      flex-grow: 100;
    }
    input:focus {
      border-color: #6bbbf7;
      box-shadow: 0 0 0 4px rgba(122, 167, 199, 0.15);
      outline: none;
    }
    .iconSearch {
      color: #838c95;
      position: absolute;
      left: 1.2rem;
      right: 0;
    }
  }
`;

const TopbarSearch = () => {
  return (
    <StyledTopbarSearch>
      <form className="topbar__search flex-vertical-center">
        <input type="text" placeholder="Search..." />
        <svg
          className="svg-icon iconSearch"
          width="18"
          height="18"
          viewBox="0 0 18 18"
        >
          <path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path>
        </svg>
      </form>
    </StyledTopbarSearch>
  );
};

export default TopbarSearch;
