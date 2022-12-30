import styled from 'styled-components';
import bookmark from '../Img/bookmark.png';
import message from '../Img/message.png';
import trophy from '../Img/trophy.png';
import vote from '../Img/vote.png';
import google from '../Img/google.png';
import github from '../Img/github.png';
import facebook from '../Img/facebook.png';
import React, { useState } from 'react';
import avatar1 from '../Img/avatar1.png';
import avatar2 from '../Img/avatar2.png';
import avatar3 from '../Img/avatar3.png';
import avatar4 from '../Img/avatar4.png';
import avatar5 from '../Img/avatar5.png';
import avatar6 from '../Img/avatar6.png';
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Container = styled.div`
  width: 100%;
  background-color: #f1f2f3;
  min-height: 1000px;
`;

const Content = styled.div`
  width: 80%;
  margin: 0 10%;
  display: flex;
`;

const LeftBox = styled.div`
  min-width: 600px;
  width: 50%;
  height: 500px;
  position: relative;
`;

const LeftContent = styled.div`
  position: absolute;
  top: 50%;
  right: 0;
  width: 60%;
  height: 300px;
  h1 {
    font-size: 2.5rem;
  }
  p {
    margin: 30px 0;
  }
  img {
    width: 25px;
    vertical-align: middle;
    margin-right: 15px;
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
  min-width: 600px;
  width: 45%;
  height: 500px;
  box-sizing: border-box;
`;

const ButtonBox = styled.div`
  margin: 20px 0 5px 5px;
  width: 50%;
  display: flex;
  flex-direction: column;
`;

const Button = styled.button`
  padding: 10px 0;
  margin: 5px 0;
  border-radius: 5px;
  width: 100%;
  cursor: pointer;
  border: 1px solid ${(props) => props.borderColor};
  color: ${(props) => props.color};
  background-color: ${(props) => props.bgColor};
  &:hover {
    background-color: ${(props) => props.hoverColor};
  }
  img {
    width: 20px;
    vertical-align: middle;
    margin: 0 3px;
  }
`;

const InputForm = styled.form`
  background-color: white;
  border: 1px solid #d6d6d6;
  box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  margin: 5px;
  width: 50%;
  padding: 20px;
`;

const InputBox = styled.div`
  padding: 5px;
  p {
    font-weight: bold;
    font-size: 1rem;
    padding: 10px 0;
  }
  input[type='text'],
  input[type='email'],
  input[type='password'] {
    width: 100%;
    padding: 10px 0;
    &:focus {
      box-shadow: 0 0 5px 5px rgba(28, 107, 138, 0.3);
    }
  }
  span {
    color: red;
    font-size: 14px;
    margin-top: 10px;
  }
`;

const SignUpButton = styled.button`
  background-color: #0a95ff;
  border: none;
  width: 100%;
  border-radius: 3px;
  padding: 10px 20px;
  margin: 10px 0;
  color: white;
  cursor: pointer;
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

  .avatar1 {
    width: 3rem;
    height: 3rem;
    margin: 2px;
    margin-bottom: 10px;
    background-color: white;
    transition: all 0.2s linear;
    border: 1px solid #d6d6d6;
    box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    &:hover {
      transform: scale(1.2);
    }
  }

  .avatar2 {
    width: 3rem;
    height: 3rem;
    margin: 2px;
    margin-bottom: 10px;
    border: 1px solid #d6d6d6;
    background-color: white;
    box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    transition: all 0.2s linear;
    &:hover {
      transform: scale(1.2);
    }
  }

  .avatar3 {
    width: 3rem;
    height: 3rem;
    margin: 2px;
    margin-bottom: 10px;
    border: 1px solid #d6d6d6;
    background-color: white;
    box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    transition: all 0.2s linear;
    &:hover {
      transform: scale(1.2);
    }
  }

  .avatar4 {
    width: 3rem;
    height: 3rem;
    margin: 2px;
    margin-bottom: 10px;
    border: 1px solid #d6d6d6;
    background-color: white;
    box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    transition: all 0.2s linear;
    &:hover {
      transform: scale(1.2);
    }
  }

  .avatar5 {
    width: 3rem;
    height: 3rem;
    margin: 2px;
    margin-bottom: 10px;
    border: 1px solid #d6d6d6;
    background-color: white;
    box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    transition: all 0.2s linear;
    &:hover {
      transform: scale(1.2);
    }
  }

  .avatar6 {
    width: 3rem;
    height: 3rem;
    margin: 2px;
    margin-bottom: 10px;
    border: 1px solid #d6d6d6;
    background-color: white;
    box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    transition: all 0.2s linear;
    &:hover {
      transform: scale(1.2);
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
          const response = await axios.post('/api/member', reqSignupBody);
          if (response.status === 201) {
            alert('환영합니다.');
            navigate('/login');
            window.location.reload();
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
        <LeftBox>
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
            <Introduce>
              <span>
                Collaborate and share knowledge with a private group for FREE.
              </span>
              <br />
              <Link to="">
                Get Stack Overflow for Teams free for up to 50 users.
              </Link>
            </Introduce>
          </LeftContent>
        </LeftBox>
        <RightBox>
          <ButtonBox>
            <Button
              bgColor="white"
              color="black"
              borderColor="gray"
              hoverColor="#f1f1f1"
            >
              <img src={google} alt="google" />
              Sign up with Google
            </Button>
            <Button
              color="white"
              bgColor="rgba(1,1,1)"
              borderColor="rgba(0,0,0)"
              hoverColor="black"
            >
              <img src={github} alt="github" />
              Sign up with Github
            </Button>
            <Button
              bgColor="#385499"
              color="white"
              borderColor="#385499"
              hoverColor="#00108A"
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
              <p>email</p>
              <input
                name="email"
                type="email"
                value={account.email}
                onChange={onChangeInput}
              />
              <span>
                <span>
                  {email === false && signSta === true ? (
                    <span>The email is not a valid email address.</span>
                  ) : (
                    <span></span>
                  )}
                </span>
              </span>
            </InputBox>
            <InputBox>
              <p>password</p>
              <input
                name="password"
                type="password"
                value={account.password}
                onChange={onChangeInput}
              />
              <span>
                <span>
                  {password === false && signSta === true ? (
                    <span>
                      Passwords must contain at least eight characters,
                      including at least 1 letter and 1 number.
                    </span>
                  ) : (
                    <span></span>
                  )}
                </span>
              </span>
            </InputBox>
            <InputBox>
              <p>avatar</p>
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
                  <img src={avatar1} alt="avatar1" className="avatar1" />
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
                  <img src={avatar2} alt="avatar2" className="avatar2" />
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
                  <img src={avatar3} alt="avatar3" className="avatar3" />
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
                  <img src={avatar4} alt="avatar4" className="avatar4" />
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
                  <img src={avatar5} alt="avatar5" className="avatar5" />
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
                  <img src={avatar6} alt="avatar6" className="avatar6" />
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
            <ConsentGuide>
              <p>
                By clicking “Sign up”, you agree to our
                <Link to="">
                  terms of service, privacy policy and cookie policy
                </Link>
              </p>
            </ConsentGuide>
          </InputForm>
        </RightBox>
      </Content>
    </Container>
  );
}

export default Signup;
