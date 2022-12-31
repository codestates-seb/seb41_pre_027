import styled from 'styled-components';
import bookmark from '../Img/bookmark.png';
import message from '../Img/message.png';
import trophy from '../Img/trophy.png';
import vote from '../Img/vote.png';
import google from '../Img/google.png';
import github from '../Img/github.png';
import facebook from '../Img/facebook.png';
import React, { useState } from 'react';
import avatars from '../utils/avatarImage';
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { authActions } from '../Redux/auth';
import { useCookies } from 'react-cookie';

const Container = styled.div`
  width: 95%;
  padding: 24px;
  min-height: calc(100vh - 53px);
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;

  @media screen and (max-width: 1200px) {
    padding: 24px 16px;
    min-height: max-content;
  }
`;

const Content = styled.div`
  display: flex;
  gap: 48px;
  font-size: 1.3rem;
  line-height: 30px;
  color: #232629;
  align-items: center;

  @media screen and (max-width: 1200px) {
    flex-direction: column;
    gap: 12px;
  }
`;

const MobileHeader = styled.div`
  display: none;

  @media screen and (max-width: 1200px) {
    display: flex;
    width: 70%;
    h1 {
      font-size: 1.6rem;
      text-align: center;
      font-weight: 400;
      letter-spacing: -0.05px;
    }
  }
  @media screen and (max-width: 640px) {
    width: 100%;
  }
`;

const LeftContent = styled.div`
  box-sizing: border-box;
  padding-left: 40px;
  h1 {
    font-size: 2rem;
    font-weight: 400;
    letter-spacing: -0.05px;
    margin-bottom: 32px;
  }
  p {
    margin-bottom: 24px;
    font-size: 1.15rem;
  }
  img {
    width: 25px;
    vertical-align: middle;
    margin-right: 15px;
  }

  @media screen and (max-width: 1200px) {
    display: none;
  }
`;
const Introduce = styled.div`
  span {
    font-size: 0.8rem;
    color: #6a737c;
  }
  a {
    font-size: 0.8rem;
    color: #0074cc;
  }
`;

const RightBox = styled.div`
  width: 300px;
`;

const ButtonBox = styled.div`
  margin: 16px 0;
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const Button = styled.button`
  padding: 6px 0;
  margin: 5px 0;
  border-radius: 5px;
  width: 100%;
  cursor: pointer;
  box-sizing: border-box;
  font-size: 1rem;

  background-color: ${(props) => props.background};
  &:hover {
    background-color: ${(props) => props.hover};
  }
  border: 1px solid ${(props) => props.bordercolor};
  color: ${(props) => props.color};
  img {
    width: 20px;
    vertical-align: middle;
    margin: 0 3px;
  }
`;

const InputForm = styled.form`
  background-color: white;
  border: 1px solid #d6d6d6;
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05), 0 20px 48px rgba(0, 0, 0, 0.05),
    0 1px 4px rgba(0, 0, 0, 0.1);
  border-radius: 7px;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
`;

const InputBox = styled.div`
  padding: 5px;
  p {
    font-weight: 700;
    font-size: 1.15rem;
  }
  input[type='text'],
  input[type='email'],
  input[type='password'] {
    width: 100%;
    padding: 0.6em 0.7em;
    border: 1px solid #babfc4;
    background-color: #fff;
    color: #0c0d0e;
    border-radius: 3px;
    font-size: 1rem;
    box-sizing: border-box;
    &:focus {
      box-shadow: 0 0 0 4px rgba(0, 116, 204, 0.15);
      border-color: #6bbbf7;
      outline: none;
    }
  }
  span {
    color: #ff0000;
    font-size: 1rem;
    margin-top: 10px;
    line-height: 1.3;
    display: inline-block;
    text-align: left;
  }
`;

const SignUpButton = styled.button`
  width: 100%;
  border-radius: 3px;
  padding: 10px 20px;
  margin: 10px 0;
  cursor: pointer;
  color: #fff;
  background-color: #0a95ff;
  border: 1px solid #0a95ff;
  box-shadow: inset 0 1px 0 0 rgba(255, 255, 255, 0.4);
  :hover {
    background-color: #0074cc;
    border-color: #0074cc;
  }
`;

const Login = styled.div`
  text-align: center;
  font-size: 12px;
  margin: 40px 0;
  p {
    margin: 4px 0;
  }
  a {
    color: #0074cc;
    text-decoration: none;
    :hover {
      color: #0a95ff;
    }
  }
`;

const ConsentGuide = styled.div`
  font-size: 12px;
  margin-top: 20px;
  a {
    color: blue;
    text-decoration: none;
  }
`;

const AvatarBox = styled.div`
  text-align: center;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin: 12px 0;

  label {
    input[type='radio'] {
      margin-right: 8px;
    }
    img {
      width: 3rem;
      height: 3rem;
      background-color: #fff;
      transition: all 0.2s linear;
      border: 1px solid #d6d6d6;
      box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05),
        0 20px 48px rgba(0, 0, 0, 0.05), 0 1px 4px rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      &:hover {
        transform: scale(1.2);
      }
    }
  }
`;

function Signup() {
  const [account, setAccount] = useState({
    email: '',
    password: '',
    name: '',
    memberImage: '1',
  });

  const onChangeInput = (e) => {
    setAccount({
      ...account,
      [e.target.name]: e.target.value,
    });
  };

  const [signSta, setsignSta] = useState(false);
  const [email, setEmail] = useState(false);
  const [password, setPassword] = useState(false);

  const emailRegex =
    /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/; // at least 1 letter and 1 digit

  function emailVal() {
    if (emailRegex.test(account.email)) {
      return setEmail(true);
    }
    return setEmail(false);
  }

  function passwordVal() {
    if (passwordRegex.test(account.password)) {
      return setPassword(true);
    }
    return setPassword(false);
  }

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const reqLoginBody = {
    username: account.email,
    password: account.password,
  };
  const [tokenCookie, setTokenCookie] = useCookies(['Authorization']);
  const [refreshCookie, setRefreshCookie] = useCookies(['Refresh']);
  const [memberIdCookie, setMemberIdCookie] = useCookies(['memberId']);
  const sendLoginReq = async () => {
    try {
      const response = await axios.post(
        process.env.REACT_APP_DB_HOST + '/api/login',
        reqLoginBody
      );
      const jwtToken = response.headers.get('Authorization');
      const refreshToken = response.headers.get('Refresh');
      const memberId = response.data.memberId;
      setTokenCookie('Authorization', jwtToken, {
        maxAge: 60 * 30000,
      }); // 60초 * 30000분
      setRefreshCookie('Refresh', refreshToken, {
        maxAge: 60 * 30000,
      }); // 60초 * 30000분
      setMemberIdCookie('memberId', memberId, { maxAge: 60 * 30000 });
      if (tokenCookie && memberIdCookie && refreshCookie) {
        dispatch(authActions.login());
      }
      setTimeout(() => {
        navigate('/');
        window.location.reload();
      }, 250);
    } catch (error) {
      console.log(error);
      alert('인증에 실패했습니다.');
    }
  };

  const signupSubmitHandler = (event) => {
    event.preventDefault();
    if (email && password) {
      const reqSignupBody = {
        email: account.email,
        password: account.password,
        name: account.name,
        memberImage: account.memberImage,
      };

      const sendSignUpReq = async () => {
        try {
          const response = await axios.post(
            process.env.REACT_APP_DB_HOST + '/api/member',
            reqSignupBody
          );
          if (response.status === 201) {
            alert('환영합니다.');
            sendLoginReq();
          }
        } catch (error) {
          console.log(error);
          alert('잘못된 요청입니다.');
        }
      };
      sendSignUpReq();
    }
  };

  return (
    <Container>
      <Content>
        <MobileHeader>
          <h1>
            Create your Stack Overflow account. It’s free and only takes a
            minute.
          </h1>
        </MobileHeader>
        <LeftContent>
          <h1>Join the Stack Overflow community</h1>
          <p>
            <img src={message} alt="message" />
            Get unstuck — ask a question
          </p>
          <p>
            <img src={vote} alt="vote" />
            Unlock new privileges like voting and commenting
          </p>
          <p>
            <img src={bookmark} alt="bookmark" />
            Save your favorite tags, filters, and jobs
          </p>
          <p>
            <img src={trophy} alt="trophy" />
            Earn reputation and badges
          </p>
          {/* <Introduce>
            <span>
              Collaborate and share knowledge with a private group for FREE.
            </span>
            <br />
            <Link to={() => false}>
              Get Stack Overflow for Teams free for up to 50 users.
            </Link>
          </Introduce> */}
        </LeftContent>
        <RightBox>
          <ButtonBox>
            <Button
              background="white"
              color="black"
              bordercolor="hsl(210deg 8% 85%)"
              hover="#f1f1f1"
            >
              <img src={google} alt="google" />
              Sign up with Google
            </Button>
            <Button
              background="rgba(1,1,1)"
              color="white"
              bordercolor="rgba(0,0,0)"
              hover="black"
            >
              <img src={github} alt="github" />
              Sign up with Github
            </Button>
            <Button
              background="#385499"
              color="white"
              bordercolor="#385499"
              hover="#00108A"
            >
              <img src={facebook} alt="facebook" />
              Sign up with Facebook
            </Button>
          </ButtonBox>
          <InputForm onSubmit={signupSubmitHandler}>
            <InputBox>
              <p>Display name</p>
              <input
                name="name"
                type="text"
                value={account.name}
                onChange={onChangeInput}
              />
            </InputBox>
            <InputBox>
              <p>Email</p>
              <input
                name="email"
                type="email"
                value={account.email}
                onChange={onChangeInput}
              />
              <span>
                {email === false && signSta === true
                  ? 'The email is not a valid email address.'
                  : null}
              </span>
            </InputBox>
            <InputBox>
              <p>Password</p>
              <input
                name="password"
                type="password"
                value={account.password}
                onChange={onChangeInput}
              />
              <span>
                {password === false && signSta === true
                  ? 'Passwords must contain at least eight characters, including at least 1 letter and 1 number.'
                  : null}
              </span>
            </InputBox>
            <InputBox>
              <p>Avatar</p>
              <AvatarBox>
                <label htmlFor="avt1">
                  <input
                    id="avt1"
                    type="radio"
                    name="memberImage"
                    value="1"
                    checked={account.memberImage === '1'}
                    onChange={onChangeInput}
                  />
                  <img src={avatars[0]} alt="avatar1" className="avatar1" />
                </label>
                <label htmlFor="avt2">
                  <input
                    id="avt2"
                    type="radio"
                    name="memberImage"
                    value="2"
                    checked={account.memberImage === '2'}
                    onChange={onChangeInput}
                  />
                  <img src={avatars[1]} alt="avatar2" className="avatar2" />
                </label>
                <label htmlFor="avt3">
                  <input
                    id="avt3"
                    type="radio"
                    name="memberImage"
                    value="3"
                    checked={account.memberImage === '3'}
                    onChange={onChangeInput}
                  />
                  <img src={avatars[2]} alt="avatar3" className="avatar3" />
                </label>
                <label htmlFor="avt4">
                  <input
                    id="avt4"
                    type="radio"
                    name="memberImage"
                    value="4"
                    checked={account.memberImage === '4'}
                    onChange={onChangeInput}
                  />
                  <img src={avatars[3]} alt="avatar4" className="avatar4" />
                </label>
                <label htmlFor="avt5">
                  <input
                    id="avt5"
                    type="radio"
                    name="memberImage"
                    value="5"
                    checked={account.memberImage === '5'}
                    onChange={onChangeInput}
                  />
                  <img src={avatars[4]} alt="avatar5" className="avatar5" />
                </label>
                <label htmlFor="avt6">
                  <input
                    id="avt6"
                    type="radio"
                    name="memberImage"
                    value="6"
                    checked={account.memberImage === '6'}
                    onChange={onChangeInput}
                  />
                  <img src={avatars[5]} alt="avatar6" className="avatar6" />
                </label>
              </AvatarBox>
            </InputBox>
            <SignUpButton
              type="submit"
              value="Signup"
              onClick={() => {
                setsignSta(true);
                emailVal();
                passwordVal();
              }}
            >
              Sign up
            </SignUpButton>
            {/* <ConsentGuide>
              <p>
                By clicking “Sign up”, you agree to our
                <Link to={() => false}>
                  terms of service, privacy policy and cookie policy
                </Link>
              </p>
            </ConsentGuide> */}
          </InputForm>
          <Login>
            <p>
              Already have an account?
              <Link to="/login"> Log in</Link>
            </p>
          </Login>
        </RightBox>
      </Content>
    </Container>
  );
}

export default Signup;
