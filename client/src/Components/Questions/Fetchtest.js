import React, { useState } from 'react';
import axios from 'axios';

const Fetchtest = () => {
  const dummydata = [
    {
      memberId: 100,
      email: 'dummydata@gmail.com',
      password:
        '{bcrypt}$2a$10$/KtYQAgG8z0YzW12sTf8F.zpOwEIs/CAFzTQmP5pOWFyJhKGzKazG',
      name: 'dummy',
    },
    {
      memberId: 101,
      email: 'dummydata2@gmail.com',
      password:
        '{bcrypt}$2a$10$/KtYQAgG8z0YzW12sTf8F.zpOwEIs/CAFzTQmP5pOWFyJhKGzKazG',
      name: 'dummy2',
    },
  ];
  const [members, setMembers] = useState(dummydata || []);
  const getMembersRequestHandler = async () => {
    try {
      await axios({
        method: 'get',
        url: '/api/member?page=0',
      }).then((response) => {
        console.log(response.data);
        setMembers(response.data);
        // setMembers(dummydata);
      });
    } catch (error) {
      console.error(error);
    }
  };

  // 회원가입테스트
  const [inputEmail, setInputEmail] = useState('');
  const [inputPW, setInputPW] = useState('');
  const [inputUsername, setInputUsername] = useState('');

  const emailChangeHandler = (event) => {
    setInputEmail(event.target.value);
  };
  const pwChangeHandler = (event) => {
    setInputPW(event.target.value);
  };
  const nameChangeHandler = (event) => {
    setInputUsername(event.target.value);
  };
  const signInHandler = (event) => {
    event.preventDefault();

    //post 요청 코드
    const reqBody = {
      email: inputEmail,
      password: inputPW,
      name: inputUsername,
    };
    console.log(JSON.stringify(reqBody));
    const sendData = async () => {
      try {
        await axios.post('/api/member', reqBody).then((response) => {
          console.log(response.data);
        });
      } catch (error) {
        console.error(error);
      }
    };
    sendData();
    // 인풋 초기화
    setInputEmail('');
    setInputPW('');
    setInputUsername('');
  };

  return (
    <div className="container">
      <h2 style={{ margin: '40px 0 20px' }}>회원가입 테스트</h2>
      <form onSubmit={signInHandler} style={{ margin: '20px 0 40px' }}>
        <input
          type="text"
          placeholder="email"
          value={inputEmail}
          onChange={emailChangeHandler}
        />
        <input
          type="text"
          placeholder="password"
          value={inputPW}
          onChange={pwChangeHandler}
        />
        <input
          type="text"
          placeholder="username"
          value={inputUsername}
          onChange={nameChangeHandler}
        />
        <button>sign in</button>
      </form>

      <hr />

      <h2 style={{ margin: '40px 0 20px' }}>전체회원 목록 불러오기</h2>
      <button
        type="submit"
        onClick={getMembersRequestHandler}
        style={{ margin: '20px 0' }}
      >
        getMembers
      </button>
      <ul>
        {members.map((el, idx) => {
          return (
            <li key={idx} style={{ margin: '20px 0' }}>
              <p>
                <b>ID </b>
                {el.memberId}
              </p>
              <p>
                <b>NAME </b>
                {el.name}
              </p>
              <p>
                <b>EMAIL </b>
                {el.email}
              </p>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default Fetchtest;
