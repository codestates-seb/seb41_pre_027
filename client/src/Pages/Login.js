import footerlogo from '../Img/footerlogo.png';
import google from '../Img/google.png';
import github from '../Img/github.png';
import facebook from '../Img/facebook.png';
import styled from 'styled-components';
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { authActions } from '../Redux/auth';
import { useCookies } from 'react-cookie';

const Container = styled.div`
  width: 100%;
  padding: 24px;
  min-height: calc(100vh - 53px);
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Content = styled.div`
  width: 300px;
  font-size: 1.3rem;
  line-height: 30px;
  color: #232629;
  text-align: center;
`;

const Logo = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  img {
    width: 64px;
  }
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
    margin-right: 3px;
  }
`;

const InputForm = styled.form`
  background-color: white;
  border: 1px solid #d6d6d6;
  border-radius: 7px;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05), 0 20px 48px rgba(0, 0, 0, 0.05),
    0 1px 4px rgba(0, 0, 0, 0.1);
`;

const InputBox = styled.div`
  padding: 5px;
  p {
    font-weight: 700;
    font-size: 1.15rem;
  }
  input {
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
  > div {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
    a {
      color: #0074cc;
      text-decoration: none;
      font-size: 12px;
    }
  }
`;

const LoginButton = styled.button`
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

const Signup = styled.div`
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

function Login() {
  const [tokenCookie, setTokenCookie] = useCookies(['Authorization']);
  const [refreshCookie, setRefreshCookie] = useCookies(['Refresh']);
  const [memberIdCookie, setMemberIdCookie] = useCookies(['memberId']);

  const [account, setAccount] = useState({
    email: '',
    password: '',
  });

  const onChangeEmail = (e) => {
    setAccount({
      ...account,
      [e.target.name]: e.target.value,
    });
  };

  const [LoginSta, setLoginSta] = useState(false);
  const [email, setEmail] = useState(false);
  const [password, setPassword] = useState(false);

  const emailRegex =
    /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

  function LoginAcess() {
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

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const loginSubmitHandler = (event) => {
    event.preventDefault();
    if (email && password) {
      const reqBody = {
        username: account.email,
        password: account.password,
      };
      const sendLoginReq = async () => {
        try {
          const response = await axios.post(
            process.env.REACT_APP_DB_HOST + '/api/login',
            reqBody
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
      sendLoginReq();
    }
  };

  return (
    <Container>
      <Content>
        <Logo>
          <Link to="/">
            <img src={footerlogo} alt="logo" />
          </Link>
        </Logo>
        <ButtonBox>
          <Button
            background="white"
            color="black"
            bordercolor="hsl(210deg 8% 85%)"
            hover="#f1f1f1"
          >
            <img src={google} alt="google" />
            Log in with Google
          </Button>
          <Button
            background="rgba(1,1,1)"
            color="white"
            bordercolor="rgba(0,0,0)"
            hover="black"
          >
            <img src={github} alt="github" />
            Log in with Github
          </Button>
          <Button
            background="#385499"
            color="white"
            bordercolor="#385499"
            hover="#00108A"
          >
            <img src={facebook} alt="facebook" />
            Log in with Facebook
          </Button>
        </ButtonBox>
        <InputForm onSubmit={loginSubmitHandler}>
          <InputBox>
            <div>
              <p>Email</p>
            </div>
            <input
              name="email"
              type="text"
              value={account.email}
              onChange={onChangeEmail}
            />
            <span>
              {email === false && LoginSta === true
                ? 'The email is not a valid email address.'
                : null}
            </span>
          </InputBox>
          <InputBox>
            <div>
              <p>Password</p>
              {/* <Link to={() => false}>Forgot password?</Link> */}
            </div>
            <input
              name="password"
              type="password"
              value={account.password}
              onChange={onChangeEmail}
            />
            <span>
              {password === false && LoginSta === true
                ? `Passwords must contain at least eight characters, including
                at least 1 letter and 1 number.`
                : null}
            </span>
          </InputBox>
          <LoginButton
            type="submit"
            value="Login"
            onClick={() => {
              setLoginSta(true);
              LoginAcess();
              passwordVal();
            }}
          >
            Log in
          </LoginButton>
        </InputForm>
        <Signup>
          <p>
            Don’t have an account?<Link to="/signup"> Sign up</Link>
          </p>
          {/* <p>
            Are you an employer?<Link to={() => false}> Sign up on Talent</Link>
          </p> */}
        </Signup>
      </Content>
    </Container>
  );
}

export default Login;
