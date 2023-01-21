import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

const StyledPagination = styled.div`
  display: flex;
  align-items: flex-end;
  gap: 4px;
  button {
    padding: 0 8px;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    background-color: transparent;
    border: 1px solid #d6d9dc;
    border-radius: 3px;
    line-height: 2;
    color: #3b4045;
    cursor: pointer;
    :hover {
      background-color: #d6d9dc;
      border: 1px solid #babfc4;
      color: #0c0d0e;
    }
  }
  button[aria-current='page'] {
    color: #fff;
    background-color: #f48225;
    cursor: default;
    :hover {
      border-color: #f48225;
    }
  }
  button:disabled {
    display: none;
  }
  span {
    display: inline-flex;
    margin: 0 8px;
    color: #232629;
  }
  .hidden {
    display: none;
  }
`;

const Pagination = ({ totalPage, limit, page, setPage }) => {
  // 총 페이지 갯수에 따라 Pagination 갯수 정하기, limit 단위로 페이지 리스트 넘기기
  const [currentPageArray, setCurrentPageArray] = useState([]);
  const [totalPageArray, setTotalPageArray] = useState([]);

  const isFirstPage = page === 1 ? true : false;
  const isLastPage = page === totalPage ? true : false;

  // 특정 숫자까지의 배열을 만들고 limit 기준으로 자른 배열 만들기
  const sliceArrayByLimit = (totalPage, limit) => {
    const totalPageArray = Array(totalPage)
      .fill()
      .map((_, i) => i);
    return Array(Math.ceil(totalPage / limit))
      .fill()
      .map(() => totalPageArray.splice(0, limit));
  };

  useEffect(() => {
    if (page % limit === 1) {
      setCurrentPageArray(totalPageArray[Math.floor(page / limit)]);
    } else if (page % limit === 0) {
      setCurrentPageArray(totalPageArray[Math.floor(page / limit) - 1]);
    }
  }, [page]);

  useEffect(() => {
    const slicedPageArray = sliceArrayByLimit(totalPage, limit);
    setTotalPageArray(slicedPageArray);
    setCurrentPageArray(slicedPageArray[0]);
  }, [totalPage]);

  return (
    <StyledPagination>
      <button onClick={() => setPage(page - 1)} disabled={page === 1}>
        Prev
      </button>
      <button onClick={() => setPage(1)} disabled={page === 1}>
        1
      </button>
      <span className={isFirstPage ? 'hidden' : null}>...</span>
      {currentPageArray?.map((i) => (
        <button
          key={i + 1}
          onClick={() => setPage(i + 1)}
          aria-current={page === i + 1 ? 'page' : null}
        >
          {i + 1}
        </button>
      ))}
      <span className={isLastPage ? 'hidden' : null}>...</span>
      <button onClick={() => setPage(totalPage)} disabled={page === totalPage}>
        {totalPage}
      </button>
      <button onClick={() => setPage(page + 1)} disabled={page === totalPage}>
        Next
      </button>
    </StyledPagination>
  );
};

export default Pagination;
