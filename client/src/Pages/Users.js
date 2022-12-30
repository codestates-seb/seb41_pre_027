import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Pagination from '../Components/UI/Pagination';
import styled from 'styled-components';
import axios from 'axios';
import avatar1 from '../assets/images/avatar/1_@1x.png';
import avatar2 from '../assets/images/avatar/2_@1x.png';
import avatar3 from '../assets/images/avatar/3_@1x.png';
import avatar4 from '../assets/images/avatar/4_@1x.png';
import avatar5 from '../assets/images/avatar/5_@1x.png';
import avatar6 from '../assets/images/avatar/6_@1x.png';

const StyledUsers = styled.section`
  padding: 24px;
  display: flex;
  flex-direction: column;
  flex-grow: 10000;
  justify-content: space-between;
  align-items: flex-start;
  gap: 24px;
  border-left: 1px solid #d6d9dc;

  .flex-vertical-center {
    display: flex;
    align-items: center;
  }

  .users__header {
    margin-bottom: 12px;
    justify-content: space-between;
    flex-grow: 10000;
    width: calc(100% - 300px - 24px);

    h2 {
      font-size: 2.07rem;
      font-family: 'Spoqa Han Sans';
      font-weight: 500;
      margin-right: 12px;
      flex: 1 auto;
    }
  }

  .users__search {
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

  .users__list {
    margin: 20px auto 40px;
    width: 100%;
    display: grid;
    grid-template-columns: repeat(4, minmax(0, 1fr));
    gap: 36px 12px;
    li {
      display: flex;
      flex-direction: row;
    }
  }
  .users__list--avatar {
    width: 48px;
    height: 48px;
    overflow: hidden;

    img {
      max-width: 100%;
      margin-top: 4px;
    }
  }
  .users__list--info {
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
    .users__search {
      input {
        max-width: 50%;
      }
    }
    .users__list {
      margin: 12px auto 24px;
      grid-template-columns: repeat(2, minmax(0, 1fr));
      gap: 18px 12px;
    }
  }

  @media screen and (max-width: 640px) {
    .users__header {
      h2 {
        font-size: 2rem;
      }
    }
    .users__search {
      input {
        max-width: 65%;
      }
    }
    .users__list {
      margin: 8px auto 24px;
      grid-template-columns: repeat(1, minmax(0, 1fr));
      gap: 12px 0;
    }
  }
`;

const Users = () => {
  const [inputValue, setInputValue] = useState('');
  const [userList, setUserList] = useState([]);
  const avatars = [avatar1, avatar2, avatar3, avatar4, avatar5, avatar6];
  const [countUsers, setCountUsers] = useState(0);
  const [page, setPage] = useState(1);
  const totalPage = Math.ceil(countUsers / 20) || 1;

  const getUsers = async () => {
    try {
      const response = await axios.get(`/api/member?page=${page - 1}`);
      setUserList(response.data.data);
      setCountUsers(response.data.count);
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
    getUsers();
  }, [page]);

  const getSearchUsers = async (keyword) => {
    try {
      const response = await axios.get(
        `/api/member/search/?search=${keyword}&page=${page - 1}`
      );
      setUserList(response.data.data);
      setCountUsers(response.data.count);
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
      getSearchUsers(inputValue);
      setInputValue('');
    }
  };

  const handleUserSearch = (event) => {
    event.preventDefault();
  };

  return (
    <StyledUsers>
      <div className="users__header flex-vertical-center">
        <h2>Users</h2>
      </div>
      <div className="users__search">
        <form onSubmit={handleUserSearch} className="flex-vertical-center">
          <input
            type="text"
            placeholder="Filter by user"
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
      <ul className="users__list">
        {userList.length ? (
          userList.map((el) => {
            return (
              <li key={el.memberId}>
                <div className="users__list--avatar">
                  <img src={avatars[el.memberImage - 1]} alt="유저네임아바타" />
                </div>
                <div className="users__list--info">
                  <Link to={'/users/' + el.memberId}>{el.name}</Link>
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
    </StyledUsers>
  );
};

export default Users;
