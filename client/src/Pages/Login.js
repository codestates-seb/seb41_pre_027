import footerlogo from '../Img/footerlogo.png';
import google from '../Img/google.png';
import github from '../Img/github.png';
import facebook from '../Img/facebook.png';
import styled from 'styled-components';
import React, { useState } from 'react';

const Container = styled.div`
  background-color: #f1f2f3;
  width: 100%;
  min-height: 800px;
  box-sizing: border-box;
`;

const Content = styled.div`
  padding-top: 100px;
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
    width: 50px;
  }
`;

const ButtonBox = styled.div`
  margin: 5px;
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const Button = styled.button`
  padding: 10px 0;
  margin: 5px 0;
  border-radius: 5px;
  width: 100%;
  cursor: pointer;

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

const InputForm = styled.div`
  background-color: white;
  border: 1px solid #d6d6d6;
  box-shadow: 0px 10px 10px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  margin: 5px;
  width: 100%;
  padding: 20px;
`;

const InputBox = styled.div`
  padding: 5px;
  p {
    font-weight: bold;
    font-size: 1rem;
    padding: 10px 0;
  }
  input {
    width: 100%;
    padding: 10px 0;
    &:focus {
      box-shadow: 0 0 5px 5px rgba(28, 107, 138, 0.3);
    }
  }
  span {
    color: red;
    font-size: 17px;
    margin-top: 10px;
  }
  > div {
    display: flex;
    justify-content: space-between;
    a {
      margin-top: 10px;
      color: #0074cc;
      text-decoration: none;
      font-size: 12px;
    }
  }
`;

const LoginButton = styled.button`
  background-color: #0a95ff;
  border: none;
  width: 100%;
  border-radius: 3px;
  padding: 10px 20px;
  margin: 10px 0;
  color: white;
  cursor: pointer;
`;

const Signup = styled.div`
  text-align: center;
  font-size: 12px;
  margin: 40px 0;
  p {
    margin: 20px 0;
  }
  a {
    color: #0074cc;
    text-decoration: none;
  }
`;

function Login() {
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

  function LoginAcess() {
    if (emailRegex.test(account.email)) {
      return setEmail(true);
    }
    return setEmail(false);
  }

  function PassVal() {
    if (account.password.length >= 4) {
      return setPassword(true);
    }
    return setPassword(false);
  }

  return (
    <Container>
      <Content>
        <Logo>
          <a href>
            <img src={footerlogo} alt="logo" />
          </a>
        </Logo>
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
        <InputForm>
          <InputBox>
            <div>
              <p>email</p>
            </div>
            <input
              name="email"
              type="text"
              value={account.email}
              onChange={onChangeEmail}
            />
            <span>
              <span>
                {email === false && LoginSta === true ? (
                  <span>The email is not a valid email address.</span>
                ) : (
                  <span></span>
                )}
              </span>
            </span>
          </InputBox>
          <InputBox>
            <div>
              <p>Password</p>
              <a href>Forgot password?</a>
            </div>
            <input
              name="password"
              type="password"
              value={account.password}
              onChange={onChangeEmail}
            />
            <span>
              <span>
                {password === false && LoginSta === true ? (
                  <span>
                    Please enter at least 4 characters for the password.
                  </span>
                ) : (
                  <span></span>
                )}
              </span>
            </span>
          </InputBox>
          <LoginButton
            value="Login"
            onClick={() => {
              setLoginSta(true);
              LoginAcess();
              PassVal();
            }}
          >
            Log in
          </LoginButton>
        </InputForm>
        <Signup>
          <p>
            Don’t have an account?<a href> Sign up</a>
          </p>
          <p>
            Are you an employer?<a href> Sign up on Talent</a>
          </p>
        </Signup>
      </Content>
    </Container>
  );
}

export default Login;
