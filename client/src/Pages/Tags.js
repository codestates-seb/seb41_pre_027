import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Pagination from '../Components/UI/Pagination';
import styled from 'styled-components';
import axios from 'axios';
import { avatars } from '../utils/avatarImage';

const StyledTags = styled.section`
  padding: 24px;
  display: flex;
  flex-direction: column;
  flex-grow: 10000;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
  border-left: 1px solid #d6d9dc;

  .flex-vertical-center {
    display: flex;
    align-items: center;
  }

  .tags__header {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    width: 100%;

    h2 {
      font-size: 2.07rem;
      font-family: 'Spoqa Han Sans';
      font-weight: 500;
      margin: 0 0 16px;
      flex: 1 auto;
    }

    p {
      font-size: 1.15rem;
      margin: 0 0 16px;
      color: #242629;
      line-height: 1.45;
    }
  }

  .tags__search {
    position: relative;
    width: 100%;
    input {
      border: 1px solid #babfc4;
      background-color: #fff;
      color: #3b4045;
      border-radius: 3px;
      padding: 0.6em 0.7em;
      padding-left: 32px;
      flex-grow: 100;
      max-width: 20%;
      ::placeholder {
        /* Chrome, Firefox, Opera, Safari */
        color: #babfc4;
        opacity: 1; /* Firefox */
      }
      ::-ms-input-placeholder {
        /* Edge */
        color: #babfc4;
      }
    }
    input:focus {
      border-color: #6bbbf7;
      box-shadow: 0 0 0 4px rgba(122, 167, 199, 0.15);
      outline: none;
    }
    .iconSearch {
      color: #838c95;
      position: absolute;
      left: 0.8rem;
      right: 0;
    }
  }

  .tags__list {
    margin: 20px auto 40px;
    width: 100%;
    display: grid;
    grid-template-columns: repeat(4, minmax(0, 1fr));
    gap: 36px 12px;
    li {
      display: flex;
      flex-direction: row;
      border: 1px solid #d6d9dc;
      border-radius: 3px;
      padding: 12px;
    }
  }
  .tags__list--avatar {
    width: 48px;
    height: 48px;
    overflow: hidden;

    img {
      max-width: 100%;
      margin-top: 4px;
    }
  }
  .tags__list--info {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 8px 0 8px 8px;
    text-align: left;
    a {
      color: #0074cc;
      :hover {
        color: #0a95ff;
      }
    }
    span {
      font-size: 11px;
    }
  }

  @media screen and (max-width: 1200px) {
    .tags__search {
      input {
        max-width: 50%;
      }
    }
    .tags__list {
      margin: 12px auto 24px;
      grid-template-columns: repeat(2, minmax(0, 1fr));
      gap: 18px 12px;
    }
  }

  @media screen and (max-width: 800px) {
    .tags__header {
      br {
        display: none;
      }
    }
  }

  @media screen and (max-width: 640px) {
    .tags__header {
      h2 {
        font-size: 2rem;
      }
    }
    .tags__search {
      input {
        max-width: 65%;
      }
    }
    .tags__list {
      margin: 8px auto 24px;
      grid-template-columns: repeat(1, minmax(0, 1fr));
      gap: 12px 0;
    }
  }
`;

const Tags = () => {
  const [inputValue, setInputValue] = useState('');
  const [tagList, setTagList] = useState([]);
  const [countTags, setCountTags] = useState(0);
  const [page, setPage] = useState(1);
  const totalPage = Math.ceil(countTags / 20) || 1;

  const getTags = async () => {
    try {
      const response = await axios.get(
        process.env.REACT_APP_DB_HOST + `/api/member?page=${page - 1}`
      );
      setTagList(response.data.data);
      setCountTags(response.data.count);
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
    getTags();
  }, [page]);

  const getSearchTags = async (keyword) => {
    try {
      const response = await axios.get(
        process.env.REACT_APP_DB_HOST +
          `/api/member/search/?search=${keyword}&page=${page - 1}`
      );
      setTagList(response.data.data);
      setCountTags(response.data.count);
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

  const handleChangeSearchKeyword = (event) => {
    setInputValue(event.target.value);
  };

  const handleKeyUp = (event) => {
    if (event.key === 'Enter') {
      getSearchTags(inputValue);
      setInputValue('');
    }
  };

  const handleTagSearch = (event) => {
    event.preventDefault();
  };

  return (
    <StyledTags>
      <div className="tags__header">
        <h2>Tags</h2>
        <p>
          A tag is a keyword or label that categorizes your question with other,
          similar questions.
          <br /> Using the right tags makes it easier for others to find and
          answer your question.
        </p>
      </div>
      <div className="tags__search">
        <form onSubmit={handleTagSearch} className="flex-vertical-center">
          <input
            type="text"
            placeholder="Filter by tag name"
            value={inputValue}
            onKeyUp={handleKeyUp}
            onChange={handleChangeSearchKeyword}
          />
          <svg
            className="svg-icon iconSearch"
            width="18"
            height="18"
            viewBox="0 0 18 18"
          >
            <path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path>
          </svg>
        </form>
      </div>
      <ul className="tags__list">
        {tagList.length ? (
          tagList.map((el) => {
            return (
              <li key={el.memberId}>
                <div className="tags__list--avatar">
                  <img src={avatars[el.memberImage - 1]} alt="유저네임아바타" />
                </div>
                <div className="tags__list--info">
                  <Link to={'/questions/tagged/' + el.name}>{el.name}</Link>
                  <span>{el.memberId}</span>
                </div>
              </li>
            );
          })
        ) : (
          <p>데이터가없음</p>
        )}
      </ul>
      {/* 페이지네이션 */}
      <Pagination
        totalPage={totalPage}
        limit={5}
        page={page}
        setPage={setPage}
      />
    </StyledTags>
  );
};

export default Tags;
