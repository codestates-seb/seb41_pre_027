import React, { useState, useCallback } from 'react';
import styled from 'styled-components';

const Main = styled.div`
  display: flex;
  justify-content: center;
`;
const Content = styled.div`
  display: flex;
  flex-direction: column;
  width: 1050px;
  padding: 15px;
`;
const Head = styled.div`
  display: flex;
  width: 100%;
  margin-bottom: 10px;
  justify-content: space-between;
`;
const Tabs = styled.div`
  display: flex;
`;
const Tab = styled.div`
  text-decoration: none;
  margin: 0.5px;
  padding: 5px 10px;
  font-size: 1.3rem;
  color: #656b71;
  &:hover {
    background-color: #f1f2f3;
    border-radius: 30px;
  }
  &.clicked {
    background-color: #f1823b;
    border-radius: 30px;
    color: white;
  }
  &.clicked::after {
    background-color: #f1823b;
    border-radius: 30px;
    color: white;
  }

  cursor: pointer;
`;
const H1 = styled.h1`
  font-size: 2rem;
  margin-left: 10px;
`;
const Detail = styled.div`
  color: #9ba2a9;
  cursor: pointer;
  white-space: nowrap;
  display: flex;
  margin: 15px 5px;
  font-size: 1.3rem;
`;
const Details = styled.div`
  margin: 5px 20px;
  justify-content: center;
  display: flex;
  flex-direction: column;
`;
const Icon = styled.div`
  margin: 0 5px 0 10px;
`;
const Icon2 = styled.div`
  margin-right: 5px;
`;
const Buttons = styled.div`
  display: flex;
  height: 35px;
`;
const Button = styled.button`
  font-size: 1.4rem;
  border-radius: 3px;
  display: flex;
  justify-content: center;
  align-items: center;
  white-space: nowrap;
  padding: 7px 10px;
  &:hover {
    background-color: #f8f9f9;
  }
  text-decoration: none;
  margin: 0 5px;
  color: #9ba2a9;
  background-color: white;
  border-color: #9ba2a9;
  border: 1px solid;
  cursor: pointer;
`;
const Left = styled.div`
  display: flex;
`;

function Mypage() {
  return (
    <Main>
      <Content>
        <Head>
          <Left>
            image
            <Details>
              <H1>name</H1>
              <Detail>
                <Icon></Icon>
                Member for 3 days
                <Icon></Icon>
                Last seen this week
                <Icon></Icon>
                Visited 3 days, 3 consecutive
              </Detail>
            </Details>
          </Left>
          <Buttons>
            <Button>
              <Icon2></Icon2>
              Edit profile
            </Button>
            <Button>
              <Icon2></Icon2>
              Network profile
            </Button>
          </Buttons>
        </Head>
        <Tabs>
          <Tab>Profile</Tab>

          <Tab>Activity</Tab>

          <Tab>Saves</Tab>

          <Tab>Settings</Tab>
        </Tabs>
      </Content>
    </Main>
  );
}

export default Mypage;
