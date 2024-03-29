import styled from 'styled-components';
import axios from 'axios';
import { Cookies } from 'react-cookie';
import React, { useState, useEffect } from 'react';
import { Link, useLoaderData, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { avatarsM } from '../utils/avatarImage';
import { authActions } from '../Redux/auth';

const Container = styled.div``;
const Span = styled.span`
  color: #7e868e;
  font-size: 1.3rem;
  margin-left: 10px;
`;
const Title = styled.h1`
  font-weight: 400;
  font-size: 2.6rem;
  margin-top: 20px;
`;
const Hr = styled.hr`
  width: 1000px;
  height: 1px;
  margin: 20px 0;
  border: 1px solid rgb(216, 217, 220);
`;
const Inform = styled.h4`
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 2rem;
`;
const PubBox = styled.div`
  border: 1px solid rgb(216, 217, 220);
  padding: 20px;
  .password {
    font-size: 1.3rem;
    margin-top: 15px;
    color: red;
  }
`;
const SubTitle = styled.h6`
  font-weight: 500;
  margin: 10px 0;
  font-size: 1.4rem;
`;

const Buttons = styled.div`
  display: flex;
  margin: 10px;
`;
const Button1 = styled.button`
  padding: 10px;
  color: white;
  text-decoration: none;
  background-color: #379fef;
  border-radius: 3px;
  border: none;
  margin-right: 10px;
  cursor: pointer;
  &:hover {
    background-color: hsl(206, 100%, 40%);
  }
`;
const Button2 = styled.button`
  padding: 10px;
  color: white;
  text-decoration: none;
  background-color: violet;
  border-radius: 3px;
  border: none;
  margin-right: 10px;
  cursor: pointer;
  &:hover {
    background-color: hsl(206, 100%, 40%);
  }
`;
const Button3 = styled.button`
  color: #379fef;
  padding: 10px 15px;
  border: none;
  background-color: white;
  border-radius: 3px;
  cursor: pointer;
  &:hover {
    background-color: #efefef;
  }
  font-size: 1.5rem;
  margin-top: 2px;
`;

const Avatarimg = styled.div`
  .avatarimg {
    width: 8rem;
    height: 8rem;
    border: 1px solid #d6d6d6;
    box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05), 0 20px 48px rgba(0, 0, 0, 0.05),
      0 1px 4px rgba(0, 0, 0, 0.1);
    border-radius: 7px;
  }
`;

function Editprofile() {
  // 탈퇴버튼시 탈퇴
  //로그인 확인시 프로필 이미지 출력
  //display name 입력후 에딧버튼시 닉네임 에딧
  // private name 입력후 에딧버튼시 아이디 변경

  const [account, setAccount] = useState({
    displayname: '',
    newpassword: '',
  });

  const [editSta, setEditSta] = useState(false);
  const [newpassword, setNewpassword] = useState(false);
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

  const dispatch = useDispatch();

  function passwordVal() {
    if (passwordRegex.test(account.newpassword)) {
      return setNewpassword(true);
    }
    return setNewpassword(false);
  }

  const onChangeprofile = (e) => {
    setAccount({
      ...account,
      [e.target.name]: e.target.value,
    });
  };
  const navigate = useNavigate();

  const cookies = new Cookies();
  const accessToken = cookies.get('Authorization');
  const memberId = cookies.get('memberId');

  const [userData, setUserData] = useState({});

  const getUser = async () => {
    try {
      const response = await axios.get(
        process.env.REACT_APP_DB_HOST + `/api/member/${memberId}`
      );
      setUserData(response.data);
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
    getUser();
  }, []);

  const Patchname = async () => {
    if (
      !(account.displayname === userData.name || account.displayname === '')
    ) {
      try {
        await axios
          .patch(
            process.env.REACT_APP_DB_HOST + `/api/member/${memberId}`,
            {
              name: account.displayname,
            },
            { headers: { Authorization: accessToken } }
          )
          .then(() => {
            alert('Your nickname change completed');
          });
      } catch (error) {
        if (error.response) {
          // 요청이 전송되었고, 서버에서 20x 외의 코드로 응답 됨
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
          return alert('Error');
        } else if (error.request) {
          // 요청이 전송되었지만, 응답이 수신되지 않음
          console.log(error.request);
          return alert('Error');
        } else {
          // 오류가 발생한 요청을 설정하는 데 문제가 생김
          console.log('Error', error.message);
        }
        console.log(error.config);
      }
    }
  };

  useEffect(() => {
    getUser();
  }, []);

  const Patchpassword = async () => {
    if (!(newpassword === false || account.password === '')) {
      try {
        await axios
          .patch(
            process.env.REACT_APP_DB_HOST + `/api/member/${memberId}`,
            {
              password: account.newpassword,
            },
            { headers: { Authorization: accessToken } }
          )
          .then(() => {
            alert('Your password change completed');
          });
      } catch (error) {
        if (error.response) {
          // 요청이 전송되었고, 서버에서 20x 외의 코드로 응답 됨
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
          return alert('Error');
        } else if (error.request) {
          // 요청이 전송되었지만, 응답이 수신되지 않음
          console.log(error.request);
          return alert('Error');
        } else {
          // 오류가 발생한 요청을 설정하는 데 문제가 생김
          console.log('Error', error.message);
        }
        console.log(error.config);
      }
    }
  };

  useEffect(() => {
    getUser();
  }, []);

  const Deleteuser = (e) => {
    e.preventDefault();
    if (window.confirm('If you click "ok", your account will be deleted')) {
      axios
        .delete(process.env.REACT_APP_DB_HOST + `/api/member/${memberId}`, {
          headers: { Authorization: accessToken },
        })
        .then(() => {
          dispatch(authActions.logout());
          cookies.remove('Authorization');
          cookies.remove('memberId');
          cookies.remove('Refresh');
          window.location.reload();
          alert('Thank you for using it.');
          navigate('/');
        })
        .catch((err) => alert(err.response.data.message));
    } else {
      return;
    }
  };

  return (
    <Container>
      <Title>Edit your profile</Title>
      <Hr />
      <Inform>Public information</Inform>
      <PubBox>
        <Avatarimg>
          {Object.keys(userData).length ? (
            <img
              src={avatarsM[userData.memberImage - 1]}
              alt={`${userData.name}아바타이미지`}
              className="avatarimg"
            />
          ) : (
            <div>error</div>
          )}
        </Avatarimg>
        <SubTitle>Display name</SubTitle>
        <input
          type="text"
          name="displayname"
          value={account.displayname}
          onChange={onChangeprofile}
        />
      </PubBox>
      <Inform>
        Private information<Span>Not shown publicly</Span>
      </Inform>
      <PubBox>
        <SubTitle>New password</SubTitle>
        <input
          type="password"
          name="newpassword"
          value={account.newpassword}
          onChange={onChangeprofile}
        />
        <div className="password">
          <span>
            {newpassword === false && editSta === true
              ? `Passwords must contain at least eight characters, including
                at least 1 letter and 1 number.`
              : null}
          </span>
        </div>
      </PubBox>
      <Buttons>
        <Button1
          onClick={() => {
            passwordVal();
            setEditSta(true);
            Patchname();
            Patchpassword();
          }}
        >
          Save Edits
        </Button1>
        <Button2 onClick={Deleteuser}>Withdraw</Button2>
        <Button3 as="a" href="/mypage">
          Cancel
        </Button3>
      </Buttons>
    </Container>
  );
}

export default Editprofile;
